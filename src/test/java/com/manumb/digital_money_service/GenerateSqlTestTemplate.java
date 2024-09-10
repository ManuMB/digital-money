package com.manumb.digital_money_service;

import com.sun.tools.javac.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

public class GenerateSqlTestTemplate {
    public static void executeSQLScript(String scriptFilePath,String scriptName) {
        try {
            // Carga la configuración de conexión desde el archivo properties en el directorio resources
            Properties properties = loadProperties(scriptFilePath);
            String url = properties.getProperty("spring.datasource.url");
            String username = properties.getProperty("spring.datasource.username");
            String password = properties.getProperty("spring.datasource.password");

            // Establece la conexión a la base de datos
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();

            // Lee el script SQL desde el archivo
            String script = loadSQLScript(scriptName);
            String[] statements = script.split(";");


            // Ejecutar cada sentencia SQL individualmente
            for (String sql : statements) {
                // Ignorar las sentencias en blanco
                if (!sql.trim().isEmpty()) {
                    statement.executeUpdate(sql);
                }
            }
            System.out.println("Script SQL ejecutado exitosamente.");

            // Cierra la conexión
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Properties loadProperties(String filePath) throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(filePath);
        properties.load(inputStream);
        return properties;
    }

    public static String loadSQLScript(String scriptFilePath) throws IOException {
        StringBuilder script = new StringBuilder();
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(scriptFilePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                script.append(line).append("\n");
            }
        }
        return script.toString();
    }
}

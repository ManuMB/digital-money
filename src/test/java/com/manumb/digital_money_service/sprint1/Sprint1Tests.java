package com.manumb.digital_money_service.sprint1;

import com.google.gson.JsonObject;
import com.manumb.digital_money_service.GenerateSqlTestTemplate;
import com.manumb.digital_money_service.business.jwt.services.JwtServiceHandler;
import com.manumb.digital_money_service.business.users.services.UserServiceHandler;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Sprint1Tests {

    private final String userUrl = "http://localhost:8080/api/users";
    private final String authUrl = "http://localhost:8080/api/auth";
    private final String registerPath = "/register";
    private final String loginPath = "/login";
    private final String logoutPath = "/logout";
    private String bearerToken;

    @Autowired
    private JwtServiceHandler jwtServiceHandler;
    @Autowired
    private UserServiceHandler userServiceHandler;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeAll
    public static void setUp() {
        GenerateSqlTestTemplate.executeSQLScript("application.properties", "src/test/java/com/manumb/digital_money_service/sqlTemplates/CreateUserTemplateSqlTest.sql");
        // Generate a new token
    }

    @AfterAll
    public static void cleanUp() {
        GenerateSqlTestTemplate.executeSQLScript("application.properties", "src/test/java/com/manumb/digital_money_service/sqlTemplates/DeleteAllTemplateSqlTest.sql");
    }

    // REGISTRAR EXITOSO DE USUARIO
    @Test
    @Order(1)
    public void register() {

        JsonObject request = new JsonObject();
        request.addProperty("fullName", "Esteban Quito");
        request.addProperty("dni", "12345678901");
        request.addProperty("email", "e.quito@gmail.com");
        request.addProperty("phoneNumber", "1112345678");
        request.addProperty("password", "Password123");

        given()
                .contentType("application/json")
                .body(request.toString())
                .post(userUrl + registerPath)
                .then()
                .statusCode(200)
                .log().body();
    }

    //REGISTRO CON CAMPO FALTANTE (DNI)
    @Test
    @Order(2)
    public void badRegister_1() {

        JsonObject request = new JsonObject();
        request.addProperty("fullName", "Esteban Quito");
        request.addProperty("email", "e.quito@gmail.com");
        request.addProperty("phoneNumber", "1112345678");
        request.addProperty("password", "Password123");
        given()
                .contentType("application/json")
                .body(request.toString())
                .post(userUrl + registerPath)
                .then()
                .statusCode(500)
                .log().body();
    }

    //REGISTRAR USUARIO PREVIAMENTE REGISTRADO
    @Test
    @Order(3)
    public void badRegister_2() {

        JsonObject request = new JsonObject();
        request.addProperty("fullName", "Esteban Quito");
        request.addProperty("dni", "12345678901");
        request.addProperty("email", "e.quito@gmail.com");
        request.addProperty("phoneNumber", "1112345678");
        request.addProperty("password", "Password123");

        given()
                .contentType("application/json")
                .body(request.toString())
                .post(userUrl + registerPath)
                .then()
                .statusCode(400)
                .log().body();
    }

    //REGISTRA USUARIO CON DNI USADO
    @Test
    @Order(4)
    public void badRegister_3() {

        JsonObject request = new JsonObject();
        request.addProperty("fullName", "Lucas Prueba");
        request.addProperty("dni", "12345678901");
        request.addProperty("email", "aaaa@gmail.com");
        request.addProperty("phoneNumber", "1472583691");
        request.addProperty("password", "Password123");

        given()
                .contentType("application/json")
                .body(request.toString())
                .post(userUrl + registerPath)
                .then()
                .statusCode(400)
                .log().body();
    }

    //LOGIN EXITOSO
    @Test
    @Order(5)
    public void login() {

        JsonObject request = new JsonObject();
        request.addProperty("email", "e.quito@gmail.com");
        request.addProperty("password", "Password123");

        given()
                .contentType("application/json")
                .body(request.toString())
                .post(authUrl + loginPath)
                .then()
                .statusCode(200)
                .log().body();
    }

    //LOGIN FALLIDO - EMAIL INEXISTENTE
    @Test
    @Order(6)
    public void badLogin() {

        JsonObject request = new JsonObject();
        request.addProperty("email", "pablonicolasm@hotmail.com");
        request.addProperty("password", "admin12");

        given()
                .contentType("application/json")
                .body(request.toString())
                .post(authUrl + loginPath)
                .then()
                .statusCode(404)
                .log().body();
    }

    //LOGOUT EXITOSO
    @Test
    @Order(7)
    public void logout() {
        var user = userServiceHandler.findByEmail("e.quito@gmail.com");
        bearerToken = jwtServiceHandler.generateToken(new HashMap<>(), user).getJwt();

        String logoutPath = "/logout";

        given()
                .header("Authorization", "DM-" + bearerToken)
                .post(authUrl + logoutPath)
                .then()
                .statusCode(200)
                .log().body();
    }
/*
    @Test
    public void aa(){
        System.out.println(passwordEncoder.encode("Password123"));
    }

 */
}

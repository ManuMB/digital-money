package com.manumb.digital_money_service.tests;

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

    @Autowired
    private JwtServiceHandler jwtServiceHandler;
    @Autowired
    private UserServiceHandler userServiceHandler;

    @BeforeAll
    public static void sqlSetUp() {
        GenerateSqlTestTemplate.executeSQLScript("application.properties", "sqlTemplates/Sprint1TemplateSqlTest.sql");
    }

    @AfterAll
    public static void cleanUp() {
        GenerateSqlTestTemplate.executeSQLScript("application.properties", "sqlTemplates/DeleteAllTemplateSqlTest.sql");
    }

    //Ok, se registra un nuevo usuario.
    @Test
    @Order(1)
    public void register() {

        JsonObject request = new JsonObject();
        request.addProperty("fullName", "Esteban Quito");
        request.addProperty("dni", "12345678");
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

    //Bad Request, Se intenta registrar un usuario con campo faltante dni.
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
                .statusCode(400)
                .log().body();
    }

    //Bad request, se intenta registrar un usuario ya existente.
    @Test
    @Order(3)
    public void badRegister_2() {

        JsonObject request = new JsonObject();
        request.addProperty("fullName", "Esteban Quito");
        request.addProperty("dni", "12345678");
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

    //Bad request, se intenta registrar un usuario con dni ya existente.
    @Test
    @Order(4)
    public void badRegister_3() {

        JsonObject request = new JsonObject();
        request.addProperty("fullName", "Juan Perez");
        request.addProperty("dni", "12345678");
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

    //OK, se loguea un usuario existente.
    @Test
    @Order(5)
    public void login() {

        JsonObject request = new JsonObject();
        request.addProperty("email", "test.user@email.com");
        request.addProperty("password", "Password123");

        given()
                .contentType("application/json")
                .body(request.toString())
                .post(authUrl + loginPath)
                .then()
                .statusCode(200)
                .log().body();
    }

    //Not Found, se intenta realizar login con email inexistente.
    @Test
    @Order(6)
    public void badLogin() {

        JsonObject request = new JsonObject();
        request.addProperty("email", "bbbb@hotmail.com");
        request.addProperty("password", "Password123");

        given()
                .contentType("application/json")
                .body(request.toString())
                .post(authUrl + loginPath)
                .then()
                .statusCode(404)
                .log().body();
    }

    //OK, se desloguea un usuario existente.
    @Test
    @Order(7)
    public void logout() {
        var user = userServiceHandler.findByEmail("test.user@email.com");
        String bearerToken = jwtServiceHandler.generateToken(new HashMap<>(), user).getJwt();

        given()
                .header("Authorization", "DM-" + bearerToken)
                .post(authUrl + logoutPath)
                .then()
                .statusCode(200)
                .log().body();
    }
}

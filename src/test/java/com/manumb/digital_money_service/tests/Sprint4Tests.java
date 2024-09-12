package com.manumb.digital_money_service.tests;

import com.google.gson.JsonObject;
import com.manumb.digital_money_service.GenerateSqlTestTemplate;
import com.manumb.digital_money_service.business.jwt.services.JwtServiceHandler;
import com.manumb.digital_money_service.business.users.services.UserServiceHandler;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Sprint4Tests {
    private final String accountUrl = "http://localhost:8080/api/accounts";
    String accountId = "/1";
    private String bearerToken;

    @Autowired
    private UserServiceHandler userServiceHandler;
    @Autowired
    private JwtServiceHandler jwtServiceHandler;

    @BeforeEach
    public void setUp() {
        var user = userServiceHandler.findByEmail("test.user@email.com");
        bearerToken = jwtServiceHandler.generateToken(new HashMap<>(), user).getJwt();
    }

    @BeforeAll
    public static void sqlSetUp() {
        GenerateSqlTestTemplate.executeSQLScript("application.properties", "sqlTemplates/Sprint4TemplateSqlTest.sql");
    }

    @AfterAll
    public static void cleanUp() {
        GenerateSqlTestTemplate.executeSQLScript("application.properties", "sqlTemplates/DeleteAllTemplateSqlTest.sql");
    }


    //Ok, se crea una transferencia exitosa con alias.
    @Test
    public void successfulCreateAccountTransfer_1(){
        JsonObject request = new JsonObject();
        request.addProperty("destinationAccountIdentifier", "JUAN.PEREZ.ALIAS");
        request.addProperty("amount", "100.0");

        given()
                .header("Authorization", "DM-" + bearerToken)
                .contentType("application/json")
                .body(request.toString())
                .post(accountUrl + accountId + "/transfer")
                .then()
                .statusCode(200)
                .log().body();
    }

    //Ok, se crea una transferencia exitosa con cvu.
    @Test
    public void successfulCreateAccountTransfer_2(){
        JsonObject request = new JsonObject();
        request.addProperty("destinationAccountIdentifier", "33333333333");
        request.addProperty("amount", "100.0");

        given()
                .header("Authorization", "DM-" + bearerToken)
                .contentType("application/json")
                .body(request.toString())
                .post(accountUrl + accountId + "/transfer")
                .then()
                .statusCode(200)
                .log().body();
    }

    //Bad Request, se intenta realizar una transferencia a una cuenta inexistente.
    @Test
    public void badCreateAccountTransfer_1(){
        JsonObject request = new JsonObject();
        request.addProperty("destinationAccountIdentifier", "CUENTA.NO.EXISTENTE");
        request.addProperty("amount", "100.0");

        given()
                .header("Authorization", "DM-" + bearerToken)
                .contentType("application/json")
                .body(request.toString())
                .post(accountUrl + accountId + "/transfer")
                .then()
                .statusCode(400)
                .log().body();
    }

    //Gone, se intenta realizar una transferencia con fondos insuficientes.
    @Test
    public void badCreateAccountTransfer_2(){
        JsonObject request = new JsonObject();
        request.addProperty("destinationAccountIdentifier", "JUAN.PEREZ.ALIAS");
        request.addProperty("amount", "100000.0");

        given()
                .header("Authorization", "DM-" + bearerToken)
                .contentType("application/json")
                .body(request.toString())
                .post(accountUrl + accountId + "/transfer")
                .then()
                .statusCode(410)
                .log().body();
    }

    //Forbidden, se intenta realizar una transferencia sin estar loguado.
    @Test
    public void badCreateAccountTransfer_3(){
        JsonObject request = new JsonObject();
        request.addProperty("destinationAccountIdentifier", "JUAN.PEREZ.ALIAS");
        request.addProperty("amount", "100.0");

        given()
                .contentType("application/json")
                .body(request.toString())
                .post(accountUrl + accountId + "/transfer")
                .then()
                .statusCode(403)
                .log().body();
    }
}

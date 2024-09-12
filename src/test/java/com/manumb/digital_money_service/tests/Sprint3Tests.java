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
public class Sprint3Tests {
    private final String accountUrl = "http://localhost:8080/api/accounts";
    String accountId = "/1";
    String transactionId = "/1";
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
        GenerateSqlTestTemplate.executeSQLScript("application.properties", "sqlTemplates/Sprint3TemplateSqlTest.sql");
    }

    @AfterAll
    public static void cleanUp() {
        GenerateSqlTestTemplate.executeSQLScript("application.properties", "sqlTemplates/DeleteAllTemplateSqlTest.sql");
    }

    //Ok, se obtiene una lista de todas las transacciones existentes.
    @Test
    @Order(4)
    public void getAllTransactions(){
        given()
                .header("Authorization", "DM-" + bearerToken)
                .get(accountUrl + accountId + "/activity")
                .then()
                .statusCode(200)
                .log().body();
    }

    //Bad request, se envia la propiedad de accountId en null, debe ser positivo.
    @Test
    @Order(5)
    public void badGetAllTransactions_1(){
        given()
                .header("Authorization", "DM-" + bearerToken)
                //TODO
                .get(accountUrl + "/" + "/activity")
                .then()
                .statusCode(400)
                .log().body();
    }

    //Forbidden, se intenta obtener las transacciones sin estar logueado.
    @Test
    @Order(6)
    public void badGetAllTransactions_2(){
        given()
                .get(accountUrl + accountId + "/activity")
                .then()
                .statusCode(403)
                .log().body();
    }

    //OK, se obtiene una transaccion existente.
    @Test
    @Order(7)
    public void getTransaction(){
        given()
                .header("Authorization", "DM-" + bearerToken)
                .get(accountUrl + accountId + "/activity" + transactionId)
                .then()
                .statusCode(200)
                .log().body();
    }

    //Bad Request, se envia la propiedad accountId en null.
    @Test
    @Order(8)
    public void badGetTransaction_1(){
        given()
                .header("Authorization", "DM-" + bearerToken)
                .get(accountUrl + "/" + "/activity" + "/1")
                .then()
                .statusCode(400)
                .log().body();
    }

    //Forbidden, se intenta obtener una transaccion sin estar logueado.
    @Test
    @Order(9)
    public void badGetTransaction_2(){
        given()
                .get(accountUrl + accountId + "/activity" + transactionId)
                .then()
                .statusCode(403)
                .log().body();
    }

    //Not Found, se intenta obtener una transaccion con transactionId inexistente.
    @Test
    @Order(10)
    public void badGetTransaction_3(){
        given()
                .header("Authorization", "DM-" + bearerToken)
                .get(accountUrl + accountId + "/activity" + "/100")
                .then()
                .statusCode(404)
                .log().body();
    }

    //OK, se crea un deposito a la cuenta con una tarjeta existente.
    @Test
    @Order(1)
    public void createCardDeposit(){
        JsonObject request = new JsonObject();
        request.addProperty("cardNumber", "1111111111111111");
        request.addProperty("amount", "500.0");

        given()
                .header("Authorization", "DM-" + bearerToken)
                .contentType("application/json")
                .body(request.toString())
                .post(accountUrl + accountId + "/deposit")
                .then()
                .statusCode(201)
                .log().body();
    }

    //Forbidden, se intenta crear deposito sin estar logueado.
    @Test
    @Order(2)
    public void badCreateCardDeposit_1(){
        JsonObject request = new JsonObject();
        request.addProperty("cardNumber", "111222333444555");
        request.addProperty("amount", "500.0");

        given()
                .contentType("application/json")
                .body(request.toString())
                .post(accountUrl + accountId + "/deposit")
                .then()
                .statusCode(403)
                .log().body();
    }

    //Bad Request, se envia la request con la propiedad cardnumber con 15 caracteres (debe tener 16).
    @Test
    @Order(3)
    public void badCreateCardDeposit_2(){
        JsonObject request = new JsonObject();
        request.addProperty("cardNumber", "11122233344455");

        given()
                .header("Authorization", "DM-" + bearerToken)
                .contentType("application/json")
                .body(request.toString())
                .post(accountUrl + accountId + "/deposit")
                .then()
                .statusCode(400)
                .log().body();
    }
}

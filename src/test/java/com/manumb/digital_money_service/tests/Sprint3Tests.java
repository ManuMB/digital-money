package com.manumb.digital_money_service.tests;

import com.google.gson.JsonObject;
import com.manumb.digital_money_service.business.jwt.services.JwtServiceHandler;
import com.manumb.digital_money_service.business.users.services.UserServiceHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Sprint3Tests {
    private final String accountUrl = "http://localhost:8080/api/accounts";
    String accountId = "/8";
    String transactionId = "/1";
    private String bearerToken;

    @Autowired
    private UserServiceHandler userServiceHandler;
    @Autowired
    private JwtServiceHandler jwtServiceHandler;
    @BeforeEach
    public void setUp() {
        var user = userServiceHandler.findByEmail("e.quito@gmail.com");
        bearerToken = jwtServiceHandler.generateToken(new HashMap<>(), user).getJwt();
    }

    @Test
    public void getLastFiveTransactions(){
        given()
                .header("Authorization", "DM-" + bearerToken)
                .get(accountUrl + accountId + "/transactions")
                .then()
                .statusCode(200)
                .log().body();
    }

    //Bad request
    @Test
    public void badGetLastFiveTransactions_1(){
        given()
                .header("Authorization", "DM-" + bearerToken)
                //TODO
                .get(accountUrl + accountId + "/transactions")
                .then()
                .statusCode(400)
                .log().body();
    }

    //Forbidden
    @Test
    public void badGetLastFiveTransactions_2(){
        given()
                //TODO
                .header("Authorization", "DM-" + bearerToken)
                .get(accountUrl + accountId + "/transactions")
                .then()
                .statusCode(403)
                .log().body();
    }

    @Test
    public void getAllTransactions(){
        given()
                .header("Authorization", "DM-" + bearerToken)
                .get(accountUrl + accountId + "/activity")
                .then()
                .statusCode(200)
                .log().body();
    }

    //Bad request
    @Test
    public void badGetAllTransactions_1(){
        given()
                .header("Authorization", "DM-" + bearerToken)
                //TODO
                .get(accountUrl + accountId + "/activity")
                .then()
                .statusCode(400)
                .log().body();
    }

    //Forbidden
    @Test
    public void badGetAllTransactions_2(){
        given()
                //TODO
                .header("Authorization", "DM-" + bearerToken)
                .get(accountUrl + accountId + "/activity")
                .then()
                .statusCode(403)
                .log().body();
    }

    @Test
    public void getTransaction(){
        given()
                .header("Authorization", "DM-" + bearerToken)
                .get(accountUrl + accountId + "/activity" + transactionId)
                .then()
                .statusCode(200)
                .log().body();
    }

    //Bad Request
    @Test
    public void badGetTransaction_1(){
        given()
                .header("Authorization", "DM-" + bearerToken)
                //TODO
                .get(accountUrl + accountId + "/activity" + transactionId)
                .then()
                .statusCode(400)
                .log().body();
    }

    //Forbidden
    @Test
    public void badGetTransaction_2(){
        given()
                //TODO
                .header("Authorization", "DM-" + bearerToken)
                .get(accountUrl + accountId + "/activity" + transactionId)
                .then()
                .statusCode(403)
                .log().body();
    }

    //ID de transaccion inexistente
    @Test
    public void badGetTransaction_3(){
        given()
                .header("Authorization", "DM-" + bearerToken)
                .get(accountUrl + accountId + "/activity" + "/100")
                .then()
                .statusCode(404)
                .log().body();
    }

    @Test
    public void createCardDeposit(){
        JsonObject request = new JsonObject();
        request.addProperty("cardNumber", "111222333444555");
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

    //Forbidden
    @Test
    public void badCreateCardDeposit_1(){
        JsonObject request = new JsonObject();
        request.addProperty("cardNumber", "111222333444555");
        request.addProperty("amount", "500.0");

        given()
                //TODO
                .header("Authorization", "DM-" + bearerToken)
                .contentType("application/json")
                .body(request.toString())
                .post(accountUrl + accountId + "/deposit")
                .then()
                .statusCode(403)
                .log().body();
    }

    //Se envia la request sin la propiedad amount
    @Test
    public void badCreateCardDeposit_2(){
        JsonObject request = new JsonObject();
        request.addProperty("cardNumber", "111222333444555");

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

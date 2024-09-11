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
public class Sprint4Tests {
    private final String accountUrl = "http://localhost:8080/api/accounts";
    String accountId = "/8";
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


    //Transferencia exitosa con alias
    @Test
    public void successfulCreateAccountTransfer_1(){
        JsonObject request = new JsonObject();
        request.addProperty("destinationAccountIdentifier", "HOLA.QUE.TAL");
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

    //Transferencia exitosa con cvu
    @Test
    public void successfulCreateAccountTransfer_2(){
        JsonObject request = new JsonObject();
        request.addProperty("destinationAccountIdentifier", "HOLA.QUE.TAL");
        //TODO
        request.addProperty("amount", "112233");

        given()
                .header("Authorization", "DM-" + bearerToken)
                .contentType("application/json")
                .body(request.toString())
                .post(accountUrl + accountId + "/transfer")
                .then()
                .statusCode(200)
                .log().body();
    }

    //Se intenta realizar transferencia a una cuenta inexistente
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

    //Se intenta realizar transferencia con fondos insuficientes
    @Test
    public void badCreateAccountTransfer_2(){
        JsonObject request = new JsonObject();
        request.addProperty("destinationAccountIdentifier", "HOLA.QUE.TAL");
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

    //Forbidden
    @Test
    public void badCreateAccountTransfer_3(){
        JsonObject request = new JsonObject();
        request.addProperty("destinationAccountIdentifier", "HOLA.QUE.TAL");
        request.addProperty("amount", "100.0");

        given()
                //TODO
                .header("Authorization", "DM-" + bearerToken)
                .contentType("application/json")
                .body(request.toString())
                .post(accountUrl + accountId + "/transfer")
                .then()
                .statusCode(403)
                .log().body();
    }
}

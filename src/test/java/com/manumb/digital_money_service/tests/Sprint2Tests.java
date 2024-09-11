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
public class Sprint2Tests {
    private final String accountUrl = "http://localhost:8080/api/accounts";
    private final String userUrl = "http://localhost:8080/api/users";
    private final String authUrl = "http://localhost:8080/api/auth/login";
    String accountId = "/1";
    String userId = "/1";
    String cardId = "/1";
    private String bearerToken;

    @Autowired
    private UserServiceHandler userServiceHandler;
    @Autowired
    private JwtServiceHandler jwtServiceHandler;

    @BeforeEach
    public void sqlSetUp() {
        var user = userServiceHandler.findByEmail("e.quito@gmail.com");
        bearerToken = jwtServiceHandler.generateToken(new HashMap<>(), user).getJwt();
    }

    @BeforeAll
    public static void setUp() {
        GenerateSqlTestTemplate.executeSQLScript("application.properties", "src/test/java/com/manumb/digital_money_service/sqlTemplates/CreateUserTemplateSqlTest.sql");
    }

    @AfterAll
    public static void cleanUp() {
        GenerateSqlTestTemplate.executeSQLScript("application.properties", "src/test/java/com/manumb/digital_money_service/sqlTemplates/DeleteAllTemplateSqlTest.sql");
    }

    @Test
    public void getAccountBalance() {
        given()
                .header("Authorization", "DM-" + bearerToken)
                .get(accountUrl + accountId + "/balance")
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    public void getAccountLastTransactions() {
        given()
                .header("Authorization", "DM-" + bearerToken)
                .get(accountUrl + accountId + "/transactions")
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    public void getUserInfo() {
        given()
                .header("Authorization", "DM-" + bearerToken)
                .get(userUrl + userId)
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    public void patchUserInfo() {
        JsonObject request = new JsonObject();
        request.addProperty("fullName", "Esteban Quito");
        request.addProperty("dni", "99999999999");
        request.addProperty("email", "e.quito@gmail.com");
        request.addProperty("phoneNumber", "8888888888");

        given()
                .header("Authorization", "DM-" + bearerToken)
                .contentType("application/json")
                .body(request.toString())
                .patch(userUrl + userId)
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    public void getAccountInfo(){
        given()
                .header("Authorization", "DM-" + bearerToken)
                .get(userUrl + userId)
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    public void patchAccountInfo(){
        JsonObject request = new JsonObject();
        request.addProperty("alias", "HOLA.QUE.TAL");

        given()
                .header("Authorization", "DM-" + bearerToken)
                .contentType("application/json")
                .body(request.toString())
                .patch(accountUrl + accountId)
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    public void getAllCards(){
        given()
                .header("Authorization", "DM-" + bearerToken)
                .get(accountUrl + accountId + "/cards")
                .then()
                .statusCode(200)
                .log().body();
    }

    //La cuenta no tiene tarjetas asociadas
    @Test
    public void getAllCardsEmpty(){
        given()
                .header("Authorization", "DM-" + bearerToken)
                //TODO
                .get(accountUrl + accountId + "/cards")
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    public void getCardById(){
        given()
                .header("Authorization", "DM-" + bearerToken)
                .get(accountUrl + accountId + "/cards" + cardId)
                .then()
                .statusCode(200)
                .log().body();
    }

    //El id de tarjeta no existe
    @Test
    public void badGetCardById(){
        given()
                .header("Authorization", "DM-" + bearerToken)
                //TODO
                .get(accountUrl + accountId + "/cards" + cardId)
                .then()
                .statusCode(500)
                .log().body();
    }

    @Test
    public void createCard(){
        JsonObject request = new JsonObject();
        request.addProperty("cardHolder", "ESTEBAN QUITO");
        request.addProperty("cardNumber", "111222333444555");
        request.addProperty("cvv", "123");
        request.addProperty("expirationDate", "2030-01-01");

        given()
                .header("Authorization", "DM-" + bearerToken)
                .contentType("application/json")
                .body(request.toString())
                .post(accountUrl + accountId + "/cards")
                .then()
                .statusCode(201)
                .log().body();
    }

    //El numero de tarjeta ya se encuentra asociado en otra cuenta
    @Test
    public void badCreateCard_1(){
        JsonObject request = new JsonObject();
        request.addProperty("cardHolder", "ESTEBAN QUITO");
        request.addProperty("cardNumber", "111222333444555");
        request.addProperty("cvv", "123");
        request.addProperty("expirationDate", "2030-01-01");

        given()
                .header("Authorization", "DM-" + bearerToken)
                .contentType("application/json")
                .body(request.toString())
                //TODO
                .post(accountUrl + accountId + "/cards")
                .then()
                .statusCode(409)
                .log().body();
    }

    //Se envia la request sin la propiedad cardNumber
    @Test
    public void badCreateCard_2(){
        JsonObject request = new JsonObject();
        request.addProperty("cardHolder", "ESTEBAN QUITO");
        request.addProperty("cvv", "123");
        request.addProperty("expirationDate", "2030-01-01");

        given()
                .header("Authorization", "DM-" + bearerToken)
                .contentType("application/json")
                .body(request.toString())
                //TODO
                .post(accountUrl + accountId + "/cards")
                .then()
                .statusCode(400)
                .log().body();
    }

    @Test
    public void deleteCard(){
        given()
                .header("Authorization", "DM-" + bearerToken)
                .contentType("application/json")
                .delete(accountUrl + accountId + "/cards" + cardId)
                .then()
                .statusCode(200)
                .log().body();
    }

    //El id de tarjeta no existe
    @Test
    public void badDeleteCard(){
        given()
                .header("Authorization", "DM-" + bearerToken)
                .contentType("application/json")
                //TODO
                .delete(accountUrl + accountId + "/cards" + cardId)
                .then()
                .statusCode(404)
                .log().body();
    }
}

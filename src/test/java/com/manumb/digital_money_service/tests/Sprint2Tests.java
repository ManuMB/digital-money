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
    String accountId = "/1";
    String userId = "/1";
    String cardId = "/1";
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
        GenerateSqlTestTemplate.executeSQLScript("application.properties", "sqlTemplates/Sprint2TemplateSqlTest.sql");
    }

    @AfterAll
    public static void cleanUp() {
        GenerateSqlTestTemplate.executeSQLScript("application.properties", "sqlTemplates/DeleteAllTemplateSqlTest.sql");
    }

    //Ok, se obtiene el balance de una cuenta existente.
    @Test
    @Order(1)
    public void getAccountBalance() {
        given()
                .header("Authorization", "DM-" + bearerToken)
                .get(accountUrl + accountId + "/balance")
                .then()
                .statusCode(200)
                .log().body();
    }

    //Ok, se obtienen las ultimas 5 transacciones de una cuenta existente.
    @Test
    @Order(2)
    public void getLastFiveTransactions() {
        given()
                .header("Authorization", "DM-" + bearerToken)
                .get(accountUrl + accountId + "/transactions")
                .then()
                .statusCode(200)
                .log().body();
    }

    //OK, se obtiene la informacion de un usuario existente.
    @Test
    @Order(3)
    public void getUserInfo() {
        given()
                .header("Authorization", "DM-" + bearerToken)
                .get(userUrl + userId)
                .then()
                .statusCode(200)
                .log().body();
    }

    //OK, se obtiene la informacion de una cuenta existente.
    @Test
    @Order(4)
    public void getAccountInfo(){
        given()
                .header("Authorization", "DM-" + bearerToken)
                .get(userUrl + userId)
                .then()
                .statusCode(200)
                .log().body();
    }

    //Bad Request, se envia la propiedad accountId en null.
    @Test
    @Order(5)
    public void badGetLastFiveTransactions_1(){
        given()
                .header("Authorization", "DM-" + bearerToken)
                .get(accountUrl + "/" + "/transactions")
                .then()
                .statusCode(400)
                .log().body();
    }

    //Forbidden, se intenta obtener las ultimas 5 transacciones sin estar logueado.
    @Test
    @Order(6)
    public void badGetLastFiveTransactions_2(){
        given()
                .get(accountUrl + accountId + "/transactions")
                .then()
                .statusCode(403)
                .log().body();
    }

    //OK, se modifica la informacion de una cuenta existente.
    @Test
    @Order(7)
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

    //OK, se obtiene una lista de tarjetas vacia de una cuenta que no tiene tarjetas registradas.
    @Test
    @Order(8)
    public void getAllCardsEmpty(){
        given()
                .header("Authorization", "DM-" + bearerToken)
                .get(accountUrl + accountId + "/cards")
                .then()
                .statusCode(200)
                .log().body();
    }

    //OK, se obtiene una tarjeta existente.
    @Test
    @Order(9)
    public void getCardById(){
        given()
                .header("Authorization", "DM-" + bearerToken)
                .get(accountUrl + accountId + "/cards" + cardId)
                .then()
                .statusCode(200)
                .log().body();
    }

    //Internal Server Error, se intenta obtener una tarjeta inexistente.
    @Test
    @Order(10)
    public void badGetCardById(){
        given()
                .header("Authorization", "DM-" + bearerToken)
                .get(accountUrl + accountId + "/cards" + "/100")
                .then()
                .statusCode(500)
                .log().body();
    }

    //OK, se crea una nueva tarjeta.
    @Test
    @Order(11)
    public void createCard(){
        JsonObject request = new JsonObject();
        request.addProperty("cardHolder", "ESTEBAN QUITO");
        request.addProperty("cardNumber", "1112223334445556");
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

    //Conflict, el numero de tarjeta ya se encuentra asociado en otra cuenta.
    @Test
    @Order(12)
    public void badCreateCard_1(){
        JsonObject request = new JsonObject();
        request.addProperty("cardHolder", "ESTEBAN QUITO");
        request.addProperty("cardNumber", "1112223334445556");
        request.addProperty("cvv", "123");
        request.addProperty("expirationDate", "2030-01-01");

        given()
                .header("Authorization", "DM-" + bearerToken)
                .contentType("application/json")
                .body(request.toString())
                .post(accountUrl + accountId + "/cards")
                .then()
                .statusCode(409)
                .log().body();
    }

    //Bad Request, se envia la request sin la propiedad cardNumber.
    @Test
    @Order(13)
    public void badCreateCard_2(){
        JsonObject request = new JsonObject();
        request.addProperty("cardHolder", "ESTEBAN QUITO");
        request.addProperty("cvv", "123");
        request.addProperty("expirationDate", "2030-01-01");

        given()
                .header("Authorization", "DM-" + bearerToken)
                .contentType("application/json")
                .body(request.toString())
                .post(accountUrl + accountId + "/cards")
                .then()
                .statusCode(400)
                .log().body();
    }

    //OK, se obtiene la lista de tarjetas de una cuenta que tiene tarjetas registradas.
    @Test
    @Order(14)
    public void getAllCards(){
        given()
                .header("Authorization", "DM-" + bearerToken)
                .get(accountUrl + accountId + "/cards")
                .then()
                .statusCode(200)
                .log().body();
    }

    //OK, se elimina una tarjeta existente.
    @Test
    @Order(15)
    public void deleteCard(){
        given()
                .header("Authorization", "DM-" + bearerToken)
                .contentType("application/json")
                .delete(accountUrl + accountId + "/cards" + cardId)
                .then()
                .statusCode(200)
                .log().body();
    }

    //Bad Request, se intenta eliminar una tarjeta inexistente.
    @Test
    @Order(16)
    public void badDeleteCard(){
        given()
                .header("Authorization", "DM-" + bearerToken)
                .contentType("application/json")
                .delete(accountUrl + accountId + "/cards" + cardId)
                .then()
                .statusCode(404)
                .log().body();
    }

    //OK, se modifica la informacion de un usuario existente.
    @Test
    @Order(17)
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
}

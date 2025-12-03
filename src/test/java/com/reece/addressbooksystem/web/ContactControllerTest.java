package com.reece.addressbooksystem.web;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.util.Files;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import io.restassured.path.json.JsonPath;
import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContactControllerTest {
    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }


    @Test
    public void testGetContact() {
        when().get("api/v1/contacts/1")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body("id", equalTo(1));
    }
    @Test
    public void testGetAllAddressBookContacts() {
        JsonPath expectedJson = new JsonPath(new File("src/test/resources/addressbookContacts.json"));
        when().get("api/v1/address-books/1/contacts")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(
                        "", equalTo(expectedJson.getList("")));
    }
    @Test
    public void testGetAllContacts() {
        JsonPath expectedJson = new JsonPath(new File("src/test/resources/allContacts.json"));
        when().get("api/v1/contacts")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(
                        "", equalTo(expectedJson.getList("")));
    }
    @Order(1)
    @Test
    public void testSaveContact() {
        JsonPath expectedJson = new JsonPath(new File("src/test/resources/newContact.json"));
        given()
                .contentType(ContentType.JSON)
        .body("{\n" +
                "  \n" +
                "  \"firstName\": \"Mona\",\n" +
                "  \"lastName\": \"Lisa\",\n" +
                "  \"phoneNumbers\": [\n" +
                "    \"44444\"\n" +
                "  ]\n" +
                "}")
                .when().post("api/v1/address-books/1/contacts")
                .then()
                .assertThat()
                .statusCode(201)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(
                        "", equalTo(expectedJson.get()));
    }

    @Order(2)
    @Test
    public void testDeleteContact() {
        JsonPath expectedJson = new JsonPath(new File("src/test/resources/newContact.json"));
        given()

                .when().delete("api/v1/contacts/4")
                .then()
                .assertThat()
                .statusCode(204)
                ;
    }

}

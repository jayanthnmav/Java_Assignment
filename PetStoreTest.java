package com.example.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.example.tests.ExcelReader;

import java.io.IOException;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

public class PetStoreTest {
    private Map<Integer, String> petData;

    @BeforeClass
    public void setUp() throws IOException {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
        ExcelReader reader = new ExcelReader();
        petData = reader.readExcel("C:\\Users\\jayanthn\\Downloads\\PetData.xlsx");
    }

    @Test
    public void createPets() {
        for (Map.Entry<Integer, String> entry : petData.entrySet()) {
            int id = entry.getKey();
            String name = entry.getValue();
            String requestBody = "{\n" +
                    "  \"id\": " + id + ",\n" +
                    "  \"category\": {\n" +
                    "    \"id\": 0,\n" +
                    "    \"name\": \"string\"\n" +
                    "  },\n" +
                    "  \"name\": \"" + name + "\",\n" +
                    "  \"photoUrls\": [\n" +
                    "    \"string\"\n" +
                    "  ],\n" +
                    "  \"tags\": [\n" +
                    "    {\n" +
                    "      \"id\": 0,\n" +
                    "      \"name\": \"string\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"status\": \"available\"\n" +
                    "}";

            Response response = given()
                    .header("Content-Type", "application/json")
                    .body(requestBody)
                    .post("/pet")
                    .then()
                    .statusCode(200)
                    .body("status", equalTo("available"))
                    .extract()
                    .response();

            assertEquals(response.statusCode(), 200);
            assertEquals(response.jsonPath().getString("status"), "available");
        }
    }


    @Test(dependsOnMethods = "createPets")
    public void getPets()
    {
        for (Map.Entry<Integer, String> entry : petData.entrySet()) {
            int id = entry.getKey();
            String name = entry.getValue();

            Response response = given()
                    .get("/pet/" + id)
                    .then()
                    .statusCode(200)
                    .body("id", equalTo(id))
                    .body("name", equalTo(name))
                    .extract()
                    .response();

            assertEquals(response.statusCode(), 200);
            assertEquals(response.jsonPath().getInt("id"), id);
            assertEquals(response.jsonPath().getString("name"), name);
        }
    }


    @Test(dependsOnMethods = "getPets")
    public void deletePets() {
        for (Map.Entry<Integer, String> entry : petData.entrySet()) {
            int id = entry.getKey();

            Response response = given()
                    .delete("/pet/" + id)
                    .then()
                    .statusCode(200)
                    .extract()
                    .response();

            assertEquals(response.statusCode(), 200);
        }

        }

    }

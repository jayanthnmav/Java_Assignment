package stepDef;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.config.JsonPathConfig.jsonPathConfig;

public class prob2StepDef {

    public String request_body;

    public String json_path_obj;

    RestAssured restAssured;

    Response response;

    String gadName;

    @Given("user name for restful {string}")
    public void user_name_for_restful(String name) {
        gadName = name;
    }
    @Then("user creates request body for restful postcall")
    public void user_creates_request_body_for_restful_postcall() {
        request_body = "{\n" +
                "    \"name\": \""+gadName+"\",\n" +
                "    \"data\": {\n" +
                "        \"year\": 2019,\n" +
                "        \"price\": 1849.99,\n" +
                "        \"CPU model\": \"Intel Core i9\",\n" +
                "        \"Hard disk size\": \"1 TB\"\n" +
                "    }\n" +
                "}";
    }
    @When("user send request body post call with {string}")
    public void user_send_request_body_post_call_with(String url) {
//        response = given().contentType(ContentType.JSON).body(request_body).when().post(url);
        response = given().contentType(ContentType.JSON).when().get(url);
        System.out.println("Response: " + response.getBody().asString());
    }
    @Then("user see status code for restful post {string}")
    public void user_see_status_code_for_restful_post(String status_code) {
        int res_status_code = response.statusCode();
        Assert.assertEquals(String.valueOf(res_status_code),status_code);
    }
    @Then("user validates the restful response year as {string}")
    public void user_validates_the_restful_response_year_as(String year) {
        String res_year = response.getBody().jsonPath().getString("data.year");
        System.out.println(res_year);
        Assert.assertEquals(String.valueOf(res_year),year);
    }
    @Then("user validates the restful response price as {string}")
    public void user_validates_the_restful_response_price_as(String price) {
        String res_price = response.getBody().jsonPath().getString("data.price");
        System.out.println(res_price);
        Assert.assertEquals(String.valueOf(res_price),price);
    }

}

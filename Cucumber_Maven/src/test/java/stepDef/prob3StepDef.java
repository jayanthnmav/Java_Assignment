package stepDef;

import io.cucumber.java.en.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class prob3StepDef {

    public String request_body;

    Response response;

    @Given("user does get call with {string}")
    public void user_does_get_call_with(String url) {
        response = given().contentType(ContentType.JSON).when().get(url);
        System.out.println("Response: " + response.getBody().asString());
    }
    @When("user see status code for restful get {string}")
    public void user_see_status_code_for_restful_get(String status_code) {
        int res_status_code = response.statusCode();
        Assert.assertEquals(String.valueOf(res_status_code),status_code);
    }
    @When("user validates the restful response {string}")
    public void user_validates_the_restful_response(String id) {
//        String json = response.getBody().jsonPath().getString("id");
//        System.out.println(json.toCharArray());
//        char[] ar = json.toCharArray();
//        System.out.println(ar[0]);

        List<Map<String,String>> lm = response.getBody().jsonPath().get("$");
        for(Map<String, String> i:lm){
            if(i.get("id")==id){
                Assert.assertEquals(i.get("id"),id);
            }
        }
        System.out.println(lm);


    }
    @Then("user validates response name {string}")
    public void user_validates_response_name(String responseName) {
        List<Map<String,String>> lm = response.getBody().jsonPath().get("$");
        for(Map<String, String> i:lm){
            if(i.get("name")==responseName){
                Assert.assertEquals(i.get("name"),responseName);
            }
        }
    }
}

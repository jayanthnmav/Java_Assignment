package assignment;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import static io.restassured.RestAssured.given;

public class Assignment02 {
    Reusable rs;

    @BeforeClass
    public void setUp() {
        rs = new Reusable();

    }

    @Parameters({"posturl", "name", "year", "DOB", "Address", "Salary"})
    @Test(priority = 0)
    public void postCall_BankEmployee(String url, String name, String year, String dob, String address, String salary) {
        System.out.println("***************POST CALL****************");
        Response response = given()
                .header("Content-Type", "application/json; charset=utf-8")
                .body(rs.createXMLBody(name, year, dob, address, salary))
                .when()
                .post(url);

        int statuscode = response.getStatusCode();
        String responseBody = response.getBody().asPrettyString();

        System.out.println(statuscode);
        System.out.println(responseBody);

        System.out.println("Validating id Key and get its value:\n");
        if(responseBody.contains("id")) {
            JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
            String idValue = jsonObject.get("id").getAsString();
            System.out.println("ID: " + idValue);
        }

    }

    @Parameters({"getCall_url","name", "year", "DOB", "Address", "Salary"})
    @Test(priority = 1)
    public void getCall_BankEmployee(String getUrl,String expectedName,String expectedYear,
                                     String expectedDob,String expectedAddress,String expectedSalary) {

        System.out.println("***************GET CALL****************");
        Response response = RestAssured.get(getUrl);

        int statuscode = response.getStatusCode();
        String responseBody = response.getBody().asPrettyString();
        System.out.println(statuscode);
        System.out.println(responseBody);

        assertResponseValueEquals(response, "name", expectedName);
        assertResponseValueEquals(response, "data.year", expectedYear);
        assertResponseValueEquals(response, "data.DOB", expectedDob);
        assertResponseValueEquals(response, "data.Address", expectedAddress);
        assertResponseValueEquals(response, "data.Salary", expectedSalary);
    }

    private void assertResponseValueEquals(Response response, String jsonPath, String expectedValue) {
        String actualValue = response.getBody().jsonPath().getString(jsonPath);
        actualValue = actualValue.replaceAll("\\[|\\]", "");
        Assert.assertEquals(actualValue, expectedValue);
    }

    @Parameters({"putCall_url","name","year","DOB","newAddress","Salary"})
    @Test(priority = 2)
    public void putCall_BankEmployee(String puturl,String name, String year, String dob, String newAddress, String salary){
        System.out.println("***************PUT CALL****************");
        Response response = given()
                .header("Content-Type", "application/json; charset=utf-8")
                .body(rs.createXMLBody(name, year, dob, newAddress, salary))
                .when()
                .put(puturl);

        int statuscode = response.getStatusCode();
        String responseBody = response.getBody().asPrettyString();
        System.out.println(statuscode);
        System.out.println(responseBody);

        String actualAddress = response.getBody().jsonPath().getString("data.Address");
        System.out.println(actualAddress);
        Assert.assertEquals(actualAddress,newAddress);

    }

    @Parameters({"getCall_After","newAddress"})
    @Test(priority = 3)
    public void getCall_AfterModifiedData(String getUrlAfterModified,String expectedAddress){

        System.out.println("***************GET CALL AFTER MODIFIED THE DATA***************");
        Response response = RestAssured.get(getUrlAfterModified);

        int statuscode = response.getStatusCode();
        String responseBody = response.getBody().asPrettyString();
        System.out.println(statuscode);
        System.out.println(responseBody);

        String newAddress = response.getBody().jsonPath().getString("data.Address");
        newAddress = newAddress.replaceAll("\\[|\\]", "");
        Assert.assertEquals(newAddress,expectedAddress);
        System.out.println("Employee new address is:"+  newAddress);

    }

    @Parameters({"deleteUrl"})
    @Test(priority = 4)

    public void deleteUrl_BankEmployee(String deleteUrl){

        System.out.println("*********** DELETE URL ************");
        Response response = RestAssured.delete(deleteUrl);

        int statuscode = response.getStatusCode();
        String responseBody = response.getBody().asPrettyString();
        System.out.println( statuscode);
        System.out.println(responseBody);

    }

    @Parameters({"getCall_After"})
    @Test(priority = 5)
    public void getCallValidationAfterDeletion(String url){

        Response response = RestAssured.get(url);

        int statuscode = response.getStatusCode();
        System.out.println(statuscode);
        String responseBody = response.getBody().asPrettyString();
        System.out.println(responseBody);

    }
}

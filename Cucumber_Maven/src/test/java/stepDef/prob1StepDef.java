package stepDef;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.Assert;


import static io.restassured.RestAssured.given;
import static io.restassured.path.xml.config.XmlPathConfig.xmlPathConfig;

public class prob1StepDef {

    RestAssured restAssured;

    public String request_body;

    public XmlPath xml_path_obj;

    Response response;

    public String CelsiusToConvert;

    @Given("the value of Celsius {string}")
    public void the_value_of_celsius(String degree) {
        CelsiusToConvert = degree;
    }

    @Then("user create conversion request body")
    public void user_create_conversion_request_body() {
        request_body = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap12:Envelope xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
                "  <soap12:Body>\n" +
                "    <CelsiusToFahrenheit xmlns=\"https://www.w3schools.com/xml/\">\n" +
                "      <Celsius> "+CelsiusToConvert+" </Celsius>\n" +
                "    </CelsiusToFahrenheit>\n" +
                "  </soap12:Body>\n" +
                "</soap12:Envelope>";
    }

    @When("user send a postcall with {string}")
    public void user_send_a_postcall_with(String url) {
        response = given().contentType(ContentType.XML).header("Content-Type","text/xml; charset=utf-8")
                .body(request_body).when().post(url);
        System.out.println("Response body: " +response.getBody().asString());
    }

    @Then("user see status code as {string}")
    public void user_see_status_code_as(String status_code) {
        int statusCode = response.statusCode();
        Assert.assertEquals(String.valueOf(statusCode), status_code);
    }



    @Then("user validates the response Farenheit as {string}")
    public void user_validates_the_response_farenheit_as(String responseFaren) {
        xml_path_obj = new XmlPath(response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        String FarenH =xml_path_obj.getString("soap:Envelope.soap:Body.CelsiusToFahrenheitResponse.CelsiusToFahrenheitResult").trim();
        Assert.assertEquals(FarenH, responseFaren);
    }

    @Then("user compares Celsius with Farenheit")
    public void user_compares_Celsius_with_Farenheit() {
        xml_path_obj = new XmlPath(response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        String FarenH =xml_path_obj.getString("soap:Envelope.soap:Body.CelsiusToFahrenheitResponse.CelsiusToFahrenheitResult").trim();
        System.out.println("Entered Input Celsius: "+CelsiusToConvert+ "Converted Celsius to Farenheit: "+FarenH);

    }
}

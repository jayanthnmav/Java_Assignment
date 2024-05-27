package trainingRA;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class CreateNewUser {

    RecJsonBody RJB;

    @BeforeClass
    public void setup()
    {
        RJB = new RecJsonBody();
    }

    @Parameters({"url", "name", "job"})
    @Test(groups = {"smoke","regression"})
    public void Create_A_New_User(String url, String name, String job)
    {
        setup();
        Response response = given()
                .contentType(ContentType.JSON)
                .body(RJB.CreateUserJsonBody(name,job))
                .when().post(url);

        int status_code = response.statusCode();
        String response_body = response.getBody().asString();
        System.out.println(status_code);
        System.out.println(response_body);

        String id = response.getBody().jsonPath().getString("id");
        System.out.println(id);
    }

    @Parameters({"url"})
    @Test(groups = {"smoke"})
    public void Test_Another_One(String url){
        System.out.println(url);
    }

    @Test(groups = {"regression"})
    public void Test_Other_One(){
        System.out.println("This is test 100");
    }


    @AfterClass
    public void tearDown(){

    }
}

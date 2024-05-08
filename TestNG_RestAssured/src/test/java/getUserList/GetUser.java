package getUserList;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;

public class GetUser {
    @BeforeClass
    public void setup()
    {
        System.out.println("Before class");
    }
    @Test
    public void do_reqres_get_userList_validation()
    {
        System.out.println("Inside class");
        Response response = get("https://reqres.in/api/users?page=2");
        String statuscode = String.valueOf(response.statusCode());
        Assert.assertEquals(statuscode,"200");
        System.out.println(response.getBody().asString());

    }

    @Test
    public void do_booklibrary_scope_call_validation()
    {
        System.out.println("book library test");
    }

    @AfterClass
    public void close_connection()
    {
        System.out.println("close connection");
    }
}

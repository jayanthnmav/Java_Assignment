package assignment;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Assignment01 {

    @BeforeClass
    public void setUp(){

    }

    @Parameters({"geturl"})
    @Test
    public void getCall(String url){

        Response response = RestAssured.get(url);

        int statuscode = response.getStatusCode();
        String responseBody = response.getBody().asPrettyString();
        System.out.println(statuscode);
        System.out.println(responseBody);


        String title = response.getBody().jsonPath().getString("title");
        System.out.println("Title contains iphone 9: "+title.contains("iPhone 9"));

        String value = response.getBody().jsonPath().getString("price");
        System.out.println("Price contains 549: "+value.contains("549"));

        String brand = response.getBody().jsonPath().getString("brand");
        System.out.println("Brand contains Apple: "+brand.contains("Apple"));

        int count = response.getBody().jsonPath().getList("images").size();
        System.out.println("Total image count is:"+count);

        for (int a = 0; a < count; a++) {
            String imageType = response.getBody().jsonPath().getString("images[" + a + "]");
            imageType = imageType.replaceAll("\\[|\\]", "");
            int index = imageType.lastIndexOf('.');
            if(index > 0) {
                String extension = imageType.substring(index + 1);
                System.out.print(extension +"\t");
            }

        }

    }

    @AfterClass
    public void tearDown(){

    }
}


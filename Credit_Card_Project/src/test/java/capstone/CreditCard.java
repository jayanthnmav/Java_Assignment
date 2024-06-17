package capstone;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.*;
import java.util.Collection;
import java.util.Map;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class CreditCard {
    private Map<Long, String> CCData;
    Connection dbconnection;

    String db_name,db_ccExpiry,db_ccType,db_ccNumber,db_ccLimit,db_pan;

    int db_year;



    JsonBody jbdy;

    @BeforeClass
    public void setUp() throws IOException {
        RestAssured.baseURI = "https://api.restful-api.dev/objects";
        ExcelReader reader = new ExcelReader();
        CCData = reader.readExcel("C:\\Users\\jayanthn\\Downloads\\CrediCardData.xlsx");
        jbdy = new JsonBody();
    }



    @Parameters({"posturl"})
    @Test
    public void postData(String posturl)
    {
        try
        {
            dbconnection= DriverManager.getConnection("jdbc:mysql://localhost:3306/capstonedb", "root", "root");
            if (dbconnection != null)
            {
                System.out.println("Database server is connected");
            }
            Statement statmnt = dbconnection.createStatement();
            statmnt.execute("use capstonedb");
            // Use Database where we need to create a new Table
            for(Long key :CCData.keySet()) {

                ResultSet result = statmnt.executeQuery("select * from cctable where card_number = '"+key+"';");
                while (result.next()) {
                    db_name = result.getString("customer_name");
                    db_year = Integer.parseInt(result.getString("card_year"));
                    db_ccNumber = result.getString("card_number");
                    db_ccLimit = result.getString("card_limit");
                    db_ccExpiry = result.getString("card_expiry");
                    db_ccType = result.getString("card_type");
                }
                System.out.println("name: " + db_name + " year: " + db_year + " credit number:   " + db_ccNumber + " Limit: "
                        + db_ccLimit
                        + "expiry:" + db_ccExpiry
                        + " card type : " + db_ccType);

                Response response = given()
                        .contentType(ContentType.JSON)
                        .body(jbdy.createJSONBody(db_name, db_year, db_ccNumber, db_ccLimit, db_ccExpiry, db_ccType))
                        .when()
                        .post(posturl);

                int statuscode = response.getStatusCode();
                String responseBody = response.getBody().asPrettyString();

                System.out.println(statuscode);
                System.out.println(responseBody);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    @Test
    public void compareData() {
        try{
            dbconnection= DriverManager.getConnection("jdbc:mysql://localhost:3306/creditdb", "root", "root");
            Statement statmnt = dbconnection.createStatement();
            statmnt.execute("use creditdb");
            for(Long key :CCData.keySet()) {
                ResultSet result = statmnt.executeQuery("select * from maptable where card_number = '" + key + "';");
                while (result.next()) {
                    db_ccNumber = result.getString("card_number");
                    db_pan = result.getString("PAN_number");
                }
                System.out.println( "Credit Card No."+db_ccNumber+ " PAN Number: "+db_pan);
            }

//            assertResponseValueEquals(response, "data.year", expectedYear);

        } catch (Exception e)
        {
            e.printStackTrace();
        }


    }
}

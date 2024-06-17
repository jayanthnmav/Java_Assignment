package capstone;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;



import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class aadharCard {


    Connection dbconnection;
    String aadharfromprop;

    static String fname,lname,adharnumber,runaddress,Phone,accountnumber;

    String requrl ="https://reqres.in/api/users";

    int id;
    private ExtentSparkReporter spark;
    private ExtentReports extent;
    private ExtentTest logger;

    @BeforeClass
    public void setup(){

    }
    public void Opeconnection()
    {
        extent =new ExtentReports();
        spark =new ExtentSparkReporter(System.getProperty("user.dir") + "/reports/Capstone.html");
        extent.attachReporter(spark);
        extent.setSystemInfo("QA" , "Jayanth");
        spark.config().setDocumentTitle("Capstone Prpject 1");
        spark.config().setReportName("Capstor_Prob_01");
        logger=extent.createTest("Validate the Reqres calss");
    }

    @Test(priority = 1)
    void readadharfrompropertiesfile()
    {


        try {

            String ppath = "C:\\Users\\jayanthn\\Downloads\\Credit_Card_Project\\aadhar.properties";

            Properties prt;
            prt = new Properties();
            FileInputStream fip = new FileInputStream(ppath);
            prt.load(fip);
            aadharfromprop = prt.getProperty("adhar");

            System.out.println("adhar ::>" + aadharfromprop);
            logger.info("Adhar from Properties :"+aadharfromprop);
            // logger.info("Adhar Card Number from Properies is"+ adharfromprop);
        }
        catch(Exception e){
            System.out.println("error" + e);
        }

    }

    @Test(priority = 2)
    void select_datafromtable()
    {
        try
        {

            //URL, DB User Name , DB Password
            dbconnection=DriverManager.getConnection("jdbc:mysql://localhost:3306/aadhardb", "root", "root");
            if (dbconnection != null)
            {
                System.out.println("Database server is connected");
            }
            Statement statmnt = dbconnection.createStatement();
            // Use Database where we need to create a new Table
            statmnt.execute("use aadhardb");
            ResultSet result= statmnt.executeQuery("SELECT * FROM pooftable where aadhar_no="+aadharfromprop+";");


            while (result.next())
            {
                adharnumber =result.getString("aadhar_no");
                runaddress =result.getString("address");
                fname =result.getString("first_name");
                lname =result.getString("last_name");
                Phone =result.getString("phone");
            }

            System.out.println( "for adhar "+adharnumber+"> :Firstname is : "+ fname+ "   " +  lname+ " from  "
                    + runaddress+ " City  "
                    +"Adhar number is :"+ adharnumber
                    +" GetPhone number is : "+ Phone  );
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }




    @Test(priority = 3)
    public void createUserinAPIusingproperiesfile() {
        Response res = given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"Fname\": \""+fname+"\",\n" +
                        "    \"LastName\": \""+lname+"\",\n" +
                        "    \"Adhar\": \""+adharnumber+"\",\n" +
                        "    \"Address\": \""+runaddress+"\",\n" +
                        "    \"Phone\": \""+Phone+"\"\n" +

                        "    }")
                .log().all()
                .when()
                .post(requrl);

        String resopnsebody = res.getBody().asString();
        int statuscode = res.statusCode();
        System.out.println(resopnsebody + statuscode);


        System.out.printf("Data from DB to copmare with API is: >" + fname +
                adharnumber + runaddress + lname + Phone +accountnumber);
        JsonPath jsonPath = res.jsonPath();

        // Iterate over the keys in the response JSON object
        Map<String, Object> responseMap = jsonPath.getMap("");

        for (Map.Entry<String, Object> entry : responseMap.entrySet()) {
            //      System.out.println("Get API :: "+entry.getKey() + ":" + entry.getValue());

            if (entry.getKey().equals("Fname"))
            {
                if (entry.getValue().equals(fname))
                {
                    System.out.println("First name Value of DB and API is MATCHED : "+fname);

                }
                else

                {
                    System.out.println("Value is Not matched");
                }

            }

            if (entry.getKey().equals("Adhar"))
            {
                if (entry.getValue().equals(adharnumber))
                {
                    System.out.println("First name Value of DB and API is MATCHED : "+adharnumber);

                }
                else

                {
                    System.out.println("Value is Not matched");
                }

            }

            if (entry.getKey().equals("Address"))
            {
                if (entry.getValue().equals(runaddress))
                {
                    System.out.println("First name Value of DB and API is MATCHED : "+runaddress);

                }
                else

                {
                    System.out.println("Value is Not matched");
                }

            }
            if (entry.getKey().equals("Phone"))
            {
                if (entry.getValue().equals(Phone))
                {
                    System.out.println("First name Value of DB and API is MATCHED : "+Phone);

                }
                else

                {
                    System.out.println("Value is Not matched");
                }

            }

            if (entry.getKey().equals("LastName"))
            {
                if (entry.getValue().equals(lname))
                {
                    System.out.println("Last name Value of DB and API is MATCHED: "+lname);

                }
                else

                {
                    System.out.println("Value is Not matched");
                }

            }

            if (entry.getKey().equals("id"))
            {
                if (StringUtils.isNumeric((CharSequence) entry.getValue()))
                {
                    System.out.println("ID is Numeric");
                }
                else
                {
                    System.out.println("ID is Not Number");
                }
            }








        }

    }

}

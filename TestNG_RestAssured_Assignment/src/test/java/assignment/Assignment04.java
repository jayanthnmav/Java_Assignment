package assignment;


import io.restassured.response.Response;
import java.sql.*;
import static io.restassured.RestAssured.get;
public class Assignment04
{
    Response response;
    Connection connection;
    public static void main(String[] args)
    {
        Assignment04 a_004 = new Assignment04();
        a_004.get_company_details();
        //a_004.create_a_new_table();
        //a_004.insert_new_records();
        a_004.update_records(5,"America","Gadgets");
    }
    public void get_company_details()
    {
        response = get("https://fake-json-api.mock.beeceptor.com/companies");
        System.out.println(response.getBody().asString());
    }
    public void create_a_new_table()
    {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", "root", "tiger");
            Statement stmt = connection.createStatement();
            stmt.execute("use db_created_through_ij");
            String query = "create table companies_data(id int,name varchar(255),address varchar(255),zip varchar(255),country varchar(255),employeeCount bigint,industry varchar(255),marketCap varchar(255),domain varchar(255),logo varchar(255),ceoName varchar(255));";
            stmt.execute(query);
            System.out.println("Table Created successfully!!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void insert_new_records()
    {
        try
        {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", "root", "tiger");
            Statement stmt = connection.createStatement();
            stmt.execute("use db_created_through_ij");
            int record_count = response.getBody().jsonPath().getList("id").size();
            System.out.println("Record Count is: " +record_count);
            for(int a = 0;a < record_count; a++)
            {
                String id = response.getBody().jsonPath().getString("id["+a+"]");
                String name = response.getBody().jsonPath().getString("name["+a+"]");
                String address = response.getBody().jsonPath().getString("address["+a+"]");
                String zip = response.getBody().jsonPath().getString("zip["+a+"]");
                String country = response.getBody().jsonPath().getString("country["+a+"]");
                String employeeCount = response.getBody().jsonPath().getString("employeeCount["+a+"]");
                String industry = response.getBody().jsonPath().getString("industry["+a+"]");
                String marketCap = response.getBody().jsonPath().getString("marketCap["+a+"]");
                String domain = response.getBody().jsonPath().getString("domain["+a+"]");
                String logo = response.getBody().jsonPath().getString("logo["+a+"]");
                String ceoName = response.getBody().jsonPath().getString("ceoName["+a+"]");
                //Inserting Records one at a time
                String query = "insert into employee_2 values ("+id+",\""+name+"\",\""+address+"\","+zip+",\""+country+"\","+employeeCount+",\""+industry+"\","+marketCap+",\""+domain+"\",\""+logo+"\",\""+ceoName+"\")";
                System.out.println(query);
                stmt.execute(query);
                System.out.println("Record Inserted successfully!!");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void update_records(int id,String new_country,String new_industry)
    {
        try
        {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", "root", "tiger");
            Statement stmt = connection.createStatement();
            Statement stmt2 = connection.createStatement();
            //Before Update
            String before_update_query = "select * from db_created_through_ij.employee_2 where id = '"+id+"';";
            ResultSet result = stmt.executeQuery(before_update_query);
            System.out.println("Before Update, the values are");
            while(result.next())
            {
                String id_old = result.getString("id");
                String country_old = result.getString("country");
                String industry_old = result.getString("industry");
                System.out.println("id:" +id_old+ " country:" +country_old+ " industry:" +industry_old);
            }
            //Updating Values
            String update_query = "update db_created_through_ij.employee_2 set country= \""+new_country+"\" ,industry= \""+new_industry+"\" where id= "+id+" ";
            stmt.executeUpdate(update_query);
            System.out.println("Update Query Executed Successfully");

            //After Update Query is executed, new values
            String after_update_query = "select * from db_created_through_ij.employee_2 where id = '"+id+"';";
            ResultSet updated_result = stmt2.executeQuery(after_update_query);
            while(updated_result.next())
            {
                String id_new = result.getString("id");
                String country_new = result.getString("country");
                String industry_new = result.getString("industry");
                System.out.println("After Update, the values are");
                System.out.println("id:" +id_new+ " country:" +country_new+ " industry:" +industry_new);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

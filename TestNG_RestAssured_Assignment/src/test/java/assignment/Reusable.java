package assignment;

public class Reusable {

    public String createXMLBody(String name,String year,String dob,String address,String salary){

        String body ="{\n" +
                "    \"name\":\""+name+"\",\n" +
                "    \"data\":{\n" +
                "        \"year\":"+year+",\n" +
                "        \"DOB\":\""+dob+"\",\n" +
                "        \"Address\":\""+address+"\",\n" +
                "        \"Salary\":\""+salary+"\"\n" +
                "    }\n" +
                "}";
        return body;
    }

}
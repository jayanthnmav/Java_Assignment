package capstone;

public class JsonBody {
    public String createJSONBody(String db_name,int db_year,String db_ccNumber,String db_ccLimit,String db_ccExpiry,String db_ccType){
        String body = "{\n" +
                "    \"name\": \"" + db_name + "\",\n" +
                "    \"data\": {\n" +
                "        \"year\": " + db_year + ",\n" +
                "        \"Credit Card Number\": \"" + db_ccNumber + "\",\n" +
                "        \"Limit\": \"" + db_ccLimit + "\",\n" +
                "        \"EXP Date\": \"" +db_ccExpiry+ "\",\n" +
                "        \"Card Type\": \"" + db_ccType + "\"\n" +
                "    }\n" +
                "}";
        return body;
    }

}

package trainingRA;

public class RecJsonBody {
    public String CreateUserJsonBody(String name, String job){

        String body = "{\n" +
                " \"name\": \""+name+"\",\n" +
                " \"job\": \""+job+"\"\n" +
                "}";
        return body;
    }
}

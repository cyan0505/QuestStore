import java.util.HashMap;

public class Parser {


    public static HashMap<String,String> parseUri(String uri){
        int methodName = 2;
        int studentId = 3;
        HashMap<String,String> map = new HashMap<>();
        String splitedUri[] = uri.split("/");
        if(splitedUri.length > 3){
            map.put("methodName", splitedUri[methodName]);
            map.put("studentId", splitedUri[studentId]);
        }
        else if(splitedUri.length == 3){
            map.put("methodName",splitedUri[methodName]);
            map.put("studentId", "empty");
        }
        else {
            map.put("methodName", "empty");
            map.put("studentId", "empty");
        }
        return map;
    }
}

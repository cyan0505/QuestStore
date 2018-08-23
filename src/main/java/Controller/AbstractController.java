package Controller;

import DAO.CodecoolerDAO;
import DAO.CreepyDAO;
import DAO.MentorDAO;
import Model.Session;
import Model.User;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import helpers.MimeTypeResolver;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractController {

    protected void sendFile(HttpExchange httpExchange, URL fileURL) throws IOException {
        // get the file
        File file = new File(fileURL.getFile());
        // we need to find out the mime type of the file, see: https://en.wikipedia.org/wiki/Media_type
        MimeTypeResolver resolver = new MimeTypeResolver(file);
        String mime = resolver.getMimeType();

        httpExchange.getResponseHeaders().set("Content-Type", mime);
        httpExchange.sendResponseHeaders(200, 0);

        OutputStream os = httpExchange.getResponseBody();

        // send the file
        FileInputStream fs = new FileInputStream(file);
        final byte[] buffer = new byte[0x10000];
        int count = 0;
        while ((count = fs.read(buffer)) >= 0) {
            os.write(buffer, 0, count);
        }
        os.close();
    }

    protected void send404(HttpExchange httpExchange) throws IOException {
        String response = "404 (Not Found)\n";
        httpExchange.sendResponseHeaders(404, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.toString().getBytes());
        os.close();
    }

    protected void sendReq(HttpExchange httpExchange, String response) throws IOException {

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public void redirectToLocation(HttpExchange exchange, String location) {

        final int NOW = -1;
        exchange.getResponseHeaders().set("Location", location);

        try {
            exchange.sendResponseHeaders(302, -1);
        } catch (IOException e) {
            System.out.println("problem?");
            e.printStackTrace();
        }
    }

    public Map<String, String> parseUserInfoFromData(String fromData) throws UnsupportedEncodingException {

        Map<String, String> map = new HashMap<>();
        String[] pairs = fromData.split("&");

        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }

        return map;
    }

    public String getCookieValueBetweenQuotes(String cookieStr){

        String[] data = cookieStr.split("=");
        String cookieValue = "";
        String pattern1 = "\"";

        Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern1));
        Matcher m = p.matcher(data[1]);
        while (m.find()) {
            cookieValue = m.group(1);
        }
        return cookieValue;

    }

    protected User getUserByLoginAndPassword(Map<String,String> inputs){
        List<User> users = getUsers();
        String password = inputs.get("psw");
        String login = inputs.get("uname");

        for (User user : users){
            if(user.getLogin().equals(password) && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }

    private List<User> getUsers() {
        List<User> users = new ArrayList<>();
        MentorDAO mentorDAO = new MentorDAO();
        CreepyDAO creepyDAO = new CreepyDAO();
        CodecoolerDAO codecoolerDAO = new CodecoolerDAO();

        try {
            users.addAll(mentorDAO.getMentorList());
            users.addAll(creepyDAO.getListOfAdmins());
            users.addAll(codecoolerDAO.getListOfCodecoolers());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    protected User getUserByLogin(String login){

        List<User> users = getUsers();

        for(User user: users){
            if(user.getLogin().equals(login)){
                return user;
            }
        }
        return null;
    }

    protected String renderPage(User user, String path){
        JtwigTemplate template = JtwigTemplate.classpathTemplate(path);
        JtwigModel model = JtwigModel.newModel();
        model.with(user.getRole(), user);

        return template.render(model);
    }

    protected String getLoginByCookie(HttpExchange httpExchange){
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        String cookieValue = getCookieValueBetweenQuotes(cookieStr);
        return Session.getUSER_LOGIN(cookieValue);
    }
}

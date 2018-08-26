package Controller;

import DAO.CodecoolerDAO;
import DAO.CreepyDAO;
import DAO.MentorDAO;
import Model.Session;
import Model.User;
import com.sun.net.httpserver.HttpExchange;
import helpers.MimeTypeResolver;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class AbstractController {

    void sendFile(HttpExchange httpExchange, URL fileURL) throws IOException {
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

    void send404(HttpExchange httpExchange) throws IOException {
        String response = "404 (Not Found)\n";
        httpExchange.sendResponseHeaders(404, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    void sendReq(HttpExchange httpExchange, String response) throws IOException {

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    void redirectToLocation(HttpExchange exchange, String location) {

        exchange.getResponseHeaders().set("Location", location);
        
        try {
            exchange.sendResponseHeaders(302, -1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    Map<String, String> parseUserInfoFromData(String fromData) throws UnsupportedEncodingException {

        Map<String, String> map = new HashMap<>();
        String[] pairs = fromData.split("&");

        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }

        return map;
    }

    User getUserByLoginAndPassword(Map<String,String> inputs){
        List<User> users = getUsers();
        String password = inputs.get("psw");
        String login = inputs.get("uname");

        for (User user : users){
            if(user.getLogin().equals(login) && user.getPassword().equals(password)){
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

    User getUserByLogin(String login){

        List<User> users = getUsers();

        for(User user: users){
            if(user.getLogin().equals(login)){
                return user;
            }
        }
        return null;
    }

    String renderPage(User user, String path){
        JtwigTemplate template = JtwigTemplate.classpathTemplate(path);
        JtwigModel model = JtwigModel.newModel();
        model.with(user.getRole(), user);

        return template.render(model);
    }

//    String renderPage(HashMap<String,Object> data, String path){
//        JtwigTemplate template = JtwigTemplate.classpathTemplate(path);
//        JtwigModel model = JtwigModel.newModel();
//
//        for(HashMap.Entry<String,Object> entry : data.entrySet()){
//            model.with(entry.getKey(), entry.getValue());
//        }
//
//        return template.render(model);
//    }

    String getLoginByCookie(HttpExchange httpExchange){
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie = new HttpCookie("Session-id", cookieStr);
        return Session.getUSER_LOGIN(cookie.getValue());
    }

    boolean isSessionCookie(HttpCookie cookie){
        return Session.connectionHaveThisCookie(cookie);
    }

    String[] parseUri(String uri){
        return uri.split("/");
    }
}

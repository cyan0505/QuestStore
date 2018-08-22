package Controller;

import DAO.CodecoolerDAO;
import DAO.CreepyDAO;
import DAO.MentorDAO;
import Model.Session;
import Model.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoginControler extends AbstractController implements HttpHandler {

    //private final String path = "static/index.html";

    @Override
    public void handle(HttpExchange httpExchange) throws IOException{
        String method = httpExchange.getRequestMethod();
        URI uri = httpExchange.getRequestURI();
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        System.out.println(cookieStr + " cookie from login");
        if(method.equals("POST")){
            handlePost(httpExchange);
        } else{
            handleSession(httpExchange);
        }
    }

    private void handlePost(HttpExchange httpExchange) throws IOException{

        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();

        Map<String,String> inputs = parseUserInfoFromData(formData);
        User user = getUserObject(inputs);

        if (user != null){
            Session session = new Session(user.getLogin());
            HttpCookie cookie = session.getCookie();
            httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
            redirectToLocation(httpExchange, "/" + user.getRole());

        } else {
            System.out.println("Wrong password or login!!!!");
            redirectToLocation(httpExchange, "index.html");
        }
    }

    private void handleSession(HttpExchange httpExchange) throws IOException{

        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie = new HttpCookie("Session-id", cookieStr);

        if(cookieStr == null){
            showMainPage(httpExchange);
        } else{
            redirectToLocation(httpExchange, "");
        }
    }


    private void showMainPage(HttpExchange httpExchange) throws IOException {

        String path = "." + httpExchange.getRequestURI().getPath();

        URL fileURL = getClass().getClassLoader().getResource(path);

        OutputStream os = httpExchange.getResponseBody();

        if (fileURL == null) {
            // Object does not exist or is not a file: reject with 404 error.
            send404(httpExchange);
        } else {
            // Object exists and is a file: accept with response code 200.
            sendFile(httpExchange, fileURL);
        }

        os.close();
    }

    private User getUserObject(Map<String,String> inputs){
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
}

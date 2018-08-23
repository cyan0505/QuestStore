package Controller;

import Model.Session;
import Model.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.net.URL;
import java.util.Map;

public class LoginControler extends AbstractController implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException{
        String method = httpExchange.getRequestMethod();

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
        User user = getUserByLoginAndPassword(inputs);

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

        if(cookieStr == null){
            showMainPage(httpExchange);
        } else{
            String cookieValue = getCookieValueBetweenQuotes(cookieStr);
            String login = Session.getUSER_LOGIN(cookieValue);
            User user = getUserByLogin(login);
            System.out.println("cookie exist");
            redirectToLocation(httpExchange,"/" + user.getRole());
        }
    }


    private void showMainPage(HttpExchange httpExchange) throws IOException {

        String path = "./static" + httpExchange.getRequestURI().getPath();
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


}

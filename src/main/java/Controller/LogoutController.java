package Controller;

import Model.Session;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.net.HttpCookie;

public class LogoutController extends AbstractController implements HttpHandler {

    @Override
    public void handle (HttpExchange httpExchange) {

        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie = new HttpCookie("Session-id", cookieStr);
        Session.removeSessionByCookie(cookie);
        redirectToLocation(httpExchange,"/index.html");
    }
}

package Controller;


import java.io.*;
import java.net.URI;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DAO.CodecoolerDAO;
import DAO.UserDAO;
import Model.Codecooler;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import static Controller.Controller.parseUri;


public class CodecoolerController implements HttpHandler{
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String method = httpExchange.getRequestMethod();
        String response = "";
        URI uri = httpExchange.getRequestURI();
        String[] parsedUri = parseUri(uri.toString());

        if(method.equals("GET")) {
            if(parsedUri[1].equals("codecooler")){
                showCodecoolerMainPage();
            }

        }

        if(method.equals("POST")) {


        }

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

    private String showCodecoolerMainPage(){
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/codecoolerMainPage.twig");
        JtwigModel model = JtwigModel.newModel();

        //model.with("studentList", studentsList);

        return template.render(model);
    }
}

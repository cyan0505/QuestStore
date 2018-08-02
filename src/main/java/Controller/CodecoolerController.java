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
        System.out.println(uri);

        if(method.equals("GET")) {

            response = chooseProperPage(parsedUri);
        }

        if(method.equals("POST")) {


        }

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }


    private String chooseProperPage(String[] parsedUri){
        String response="";
        if(parsedUri.length == 3 && parsedUri[1].equals("codecooler")) {
            response = getCodecoolerMainPage(parsedUri[2]);
        }
        else if(parsedUri.length == 4 && parsedUri[3].equals("profile")) {
            response = getCodecoolerProfile(parsedUri[2]);
        }

        return response;
    }


    private String getCodecoolerMainPage(String id){
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/codecoolerMainPage.twig");
        JtwigModel model = JtwigModel.newModel();

        //paste proper codecooler with index id
        //model.with("codecooler", codecooler);

        return template.render(model);
    }

    private String getCodecoolerProfile(String id){
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/codecoolerProfile.twig");
        JtwigModel model = JtwigModel.newModel();

        //paste proper codecooler with index id
        //model.with("codecooler", codecooler);

        return template.render(model);
    }
}

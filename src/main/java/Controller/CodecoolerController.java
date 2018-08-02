package Controller;


import java.io.*;
import java.net.URI;
import java.sql.SQLException;
import DAO.CodecoolerDAO;
import Model.Codecooler;
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
        int mainPageIndex = 1;
        int idIndex = 2;
        int subPageIndex = 3;
        try {
            if (parsedUri.length == 3 && parsedUri[mainPageIndex].equals("codecooler")) {
                response = getCodecoolerMainPage(parsedUri[idIndex]);
            }
            else if (parsedUri.length == 4 && parsedUri[subPageIndex].equals("profile")) {
                response = getCodecoolerProfile(parsedUri[idIndex]);
            }
            else if (parsedUri.length == 4 && parsedUri[subPageIndex].equals("contact")){
                response = getCodecoolerContact(parsedUri[idIndex]);
            }
            else if (parsedUri.length == 4 && parsedUri[subPageIndex].equals("store")){
                response = getCodecoolerStore(parsedUri[idIndex]);
            }
            else if (parsedUri.length == 4 && parsedUri[subPageIndex].equals("inventory")){
                response = getCodecoolerInventory(parsedUri[idIndex]);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return response;
    }


    private String getCodecoolerMainPage(String id) throws SQLException{
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/codecoolerMainPage.twig");
        JtwigModel model = JtwigModel.newModel();

        CodecoolerDAO codecoolerDAO = new CodecoolerDAO();
        Codecooler codecooler = codecoolerDAO.getCodecooler(id);
        model.with("codecooler", codecooler);

        return template.render(model);
    }

    private String getCodecoolerProfile(String id) throws SQLException{
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/codecoolerProfile.twig");
        JtwigModel model = JtwigModel.newModel();

        CodecoolerDAO codecoolerDAO = new CodecoolerDAO();
        Codecooler codecooler = codecoolerDAO.getCodecooler(id);
        model.with("codecooler", codecooler);

        return template.render(model);
    }

    private String getCodecoolerContact(String id) throws SQLException{
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/codecoolerContact.twig");
        JtwigModel model = JtwigModel.newModel();

        System.out.println("sss");

        CodecoolerDAO codecoolerDAO = new CodecoolerDAO();
        Codecooler codecooler = codecoolerDAO.getCodecooler(id);
        model.with("codecooler", codecooler);

        return template.render(model);
    }

    private String getCodecoolerStore(String id) throws SQLException{
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/codecoolerStore.twig");
        JtwigModel model = JtwigModel.newModel();

        CodecoolerDAO codecoolerDAO = new CodecoolerDAO();
        Codecooler codecooler = codecoolerDAO.getCodecooler(id);
        model.with("codecooler", codecooler);

        return template.render(model);
    }

    private String getCodecoolerQuest(String id) throws SQLException{
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/codecoolerQuest.twig");
        JtwigModel model = JtwigModel.newModel();

        CodecoolerDAO codecoolerDAO = new CodecoolerDAO();
        Codecooler codecooler = codecoolerDAO.getCodecooler(id);
        model.with("codecooler", codecooler);

        return template.render(model);
    }

    private String getCodecoolerInventory(String id) throws SQLException{
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/codecoolerInventory.twig");
        JtwigModel model = JtwigModel.newModel();

        CodecoolerDAO codecoolerDAO = new CodecoolerDAO();
        Codecooler codecooler = codecoolerDAO.getCodecooler(id);
        model.with("codecooler", codecooler);

        return template.render(model);
    }
}

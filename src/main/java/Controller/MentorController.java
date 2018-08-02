package Controller;

import java.io.*;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import BuisnessLogic.Artifact;
import BuisnessLogic.Quest;
import DAO.ArtefactDAO;
import DAO.CodecoolerDAO;
import DAO.QuestDAO;
import DAO.UserDAO;
import Model.Codecooler;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class MentorController implements HttpHandler {

    private UserDAO userDao = new UserDAO();
    private ArtefactDAO artefactDao = new ArtefactDAO();
    private QuestDAO questDao = new QuestDAO();


    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String method = httpExchange.getRequestMethod();
        String response = "";

        if(method.equals("GET")) {

            String[] uri = Controller.parseUri(httpExchange.getRequestURI().toString());

            if (uri.length >= 3) {

                if (uri[2].equals("delete")) {

                    int studentId = Integer.parseInt(uri[3]);

                    try {
                        userDao.deleteUser(studentId);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                if (uri[2].equals("store-mentor")) {

                    List<Artifact> artifactList = new ArrayList<>();

                    JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/store-mentor.twig");

                    JtwigModel model = JtwigModel.newModel();

                    try {
                        artifactList = artefactDao.getListOfArtifact();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    model.with("artifactList", artifactList);

                    response = template.render(model);

                }

                if(uri[2].equals("quest-mentor")) {
                    List<Quest> questList = new ArrayList<>();

                    JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/quest-mentor.twig");

                    JtwigModel model = JtwigModel.newModel();

                    try {
                        questList = questDao.getListOfQuests();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    model.with("questList", questList);

                    response = template.render(model);

                }

                else {
                    Controller.redirectToLocation(httpExchange, "/main-mentor");
                }

            }

        }

        if (method.equals("POST")) {
            String[] uri = Controller.parseUri(httpExchange.getRequestURI().toString());

            if (uri[2].equals("add")) {
                InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody());
                BufferedReader br = new BufferedReader(isr);
                String formData = br.readLine();


                Map inputs = Controller.parseUserInfoFromData(formData);


                String firstName = inputs.get("firstName").toString();
                String lastName = inputs.get("lastName").toString();
                String login = inputs.get("login").toString();
                String password = inputs.get("pass").toString();
                String email = inputs.get("email").toString();
                String role = "codecooler";

                Codecooler codecooler = new Codecooler(firstName, lastName, login, password, email, role);

                try {
                    userDao.addUser(codecooler);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (uri[2].equals("edit")){


            }

        }

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}

package Controller;

import java.io.*;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import BuisnessLogic.Artifact;
import DAO.*;
import Model.Codecooler;
import Model.Mentor;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import static Controller.Controller.parseUri;

public class MentorController implements HttpHandler {

    private UserDAO userDao = new UserDAO();
    private ArtefactDAO artefactDao = new ArtefactDAO();
    private QuestDAO questDao = new QuestDAO();


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
            if (parsedUri.length == 3 && parsedUri[mainPageIndex].equals("mentor")) {
                response = getMentorMainPage(parsedUri[idIndex]);
            }
            else if (parsedUri.length == 4 && parsedUri[subPageIndex].equals("profile")) {
                response = getMentorProfile(parsedUri[idIndex]);
            }
            else if (parsedUri.length == 4 && parsedUri[subPageIndex].equals("contact")){
                response = getMentorContact(parsedUri[idIndex]);
            }
            else if (parsedUri.length == 4 && parsedUri[subPageIndex].equals("store")){
                System.out.println("WESzlo");
                response = getMentorStore();
            }
            else if (parsedUri.length == 4 && parsedUri[subPageIndex].equals("inventory")){
                response = getMentorRoom(parsedUri[idIndex]);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println(response);
        return response;
    }


    private String getMentorMainPage(String id) throws SQLException{
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentorMainPage.twig");
        JtwigModel model = JtwigModel.newModel();

        MentorDAO mentorDAO = new MentorDAO();
        Mentor mentor = mentorDAO.getMentor(String.valueOf(id));
        model.with("mentor", mentor);

        return template.render(model);
    }

    private String getMentorProfile(String id) throws SQLException{
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentorProfile.twig");
        JtwigModel model = JtwigModel.newModel();

        MentorDAO mentorDAO = new MentorDAO();
        Mentor mentor = mentorDAO.getMentor(id);
        model.with("mentor", mentor);

        return template.render(model);
    }

    private String getMentorContact(String id) throws SQLException{
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentorContact.twig");
        JtwigModel model = JtwigModel.newModel();

        MentorDAO mentorDAO = new MentorDAO();
        Mentor mentor = mentorDAO.getMentor(String.valueOf(id));
        model.with("mentor", mentor);

        return template.render(model);
    }

    private String getMentorStore() throws SQLException{
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/storeMentor.twig");
        JtwigModel model = JtwigModel.newModel();


        List<Artifact> artifactList = artefactDao.getListOfArtifact();
        ArrayList<Integer> numberList = new ArrayList<>();
        numberList.add(1);
        numberList.add(2);
        numberList.add(3);


//        model.with("artifacts", artifactList);
//        model.with("numbers", numberList);


        List<List> listOfLists = artefactDao.getNestedArtifactList(artefactDao.getListOfArtifact());
        model.with("artifacts", listOfLists);

        return template.render(model);
    }

    private String getMentorRoom(String id) throws SQLException{
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/MentorRoom.twig");
        JtwigModel model = JtwigModel.newModel();

        MentorDAO mentorDAO = new MentorDAO();
        Mentor mentor = mentorDAO.getMentor(String.valueOf(id));
        model.with("mentor", mentor);

        return template.render(model);
    }
//    @Override
//    public void handle(HttpExchange httpExchange) throws IOException {
//
//        String method = httpExchange.getRequestMethod();
//        String response = "";
//
//        if(method.equals("GET")) {
//
//            String[] uri = Controller.parseUri(httpExchange.getRequestURI().toString());
//
//            if (uri.length >= 3) {
//
//                if (uri[2].equals("delete")) {
//
//                    int studentId = Integer.parseInt(uri[3]);
//
//                    try {
//                        userDao.deleteUser(studentId);
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                if (uri[2].equals("store-mentor")) {
//
//                    List<Artifact> artifactList = new ArrayList<>();
//
//                    JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/store-mentor.twig");
//
//                    JtwigModel model = JtwigModel.newModel();
//
//                    try {
//                        artifactList = artefactDao.getListOfArtifact();
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//
//                    model.with("artifactList", artifactList);
//
//                    response = template.render(model);
//
//                }
//
//                if(uri[2].equals("quest-mentor")) {
//                    List<Quest> questList = new ArrayList<>();
//
//                    JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/quest-mentor.twig");
//
//                    JtwigModel model = JtwigModel.newModel();
//
//                    try {
//                        questList = questDao.getListOfQuests();
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//
//                    model.with("questList", questList);
//
//                    response = template.render(model);
//
//                }
//
//                else {
//                    Controller.redirectToLocation(httpExchange, "/main-mentor");
//                }
//
//            }
//
//        }
//
//        if (method.equals("POST")) {
//            String[] uri = Controller.parseUri(httpExchange.getRequestURI().toString());
//
//            if (uri[2].equals("add")) {
//                InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody());
//                BufferedReader br = new BufferedReader(isr);
//                String formData = br.readLine();
//
//
//                Map inputs = Controller.parseUserInfoFromData(formData);
//
//
//                String firstName = inputs.get("firstName").toString();
//                String lastName = inputs.get("lastName").toString();
//                String login = inputs.get("login").toString();
//                String password = inputs.get("pass").toString();
//                String email = inputs.get("email").toString();
//                String role = "codecooler";
//
//                Codecooler codecooler = new Codecooler(firstName, lastName, login, password, email, role);
//
//                try {
//                    userDao.addUser(codecooler);
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            if (uri[2].equals("edit")){
//
//
//            }
//
//        }
//
//        httpExchange.sendResponseHeaders(200, response.length());
//        OutputStream os = httpExchange.getResponseBody();
//        os.write(response.getBytes());
//        os.close();
//    }

}

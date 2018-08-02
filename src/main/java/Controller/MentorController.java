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
                response = getMentorStore();
            }
            else if (parsedUri.length == 4 && parsedUri[subPageIndex].equals("quest")){
                response = getMentorQuest();
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

        List<List> listOfLists = artefactDao.getNestedArtifactList(artefactDao.getListOfArtifact());
        model.with("artifacts", listOfLists);

        return template.render(model);
    }

    private String getMentorQuest() throws SQLException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/quest-mentor.twig");
        JtwigModel model = JtwigModel.newModel();

        List<List> listOfLists = questDao.getNestedQuestList(questDao.getListOfQuests());
        model.with("quests", listOfLists);

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

}

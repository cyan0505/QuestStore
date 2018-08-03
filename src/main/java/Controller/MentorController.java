package Controller;

import java.io.*;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;

import BuisnessLogic.Artifact;
import BuisnessLogic.Quest;
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
    private MentorDAO mentorDao = new MentorDAO();
    private CodecoolerDAO codecoolerDao = new CodecoolerDAO();
    private final int mainPageIndex = 1;
    private final int idIndex = 2;
    private final int subPageIndex = 3;


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

            if(parsedUri.length == 4 && parsedUri[subPageIndex].equals("store")) {

                Artifact artifact = new Artifact("New artifact", "Artifact description", 10, false);
                try {
                    artefactDao.addArtifact(artifact);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if(parsedUri.length == 4 && parsedUri[subPageIndex].equals("quest")) {
                Quest quest = new Quest("New quest", "Quest description", 10, false);
                try {
                    questDao.addQuest(quest);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

    private String chooseProperPage(String[] parsedUri){
        String response="";

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
            else if (parsedUri.length == 4 && parsedUri[subPageIndex].equals("codecooler")){
                response = getMentorCodecoolerList(parsedUri[idIndex]);
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
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentorStore.twig");
        JtwigModel model = JtwigModel.newModel();

        List<List> listOfLists = artefactDao.getNestedArtifactList(artefactDao.getListOfArtifact());
        model.with("artifacts", listOfLists);

        return template.render(model);
    }

    private String getMentorQuest() throws SQLException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentorQuest.twig");
        JtwigModel model = JtwigModel.newModel();

        List<List> listOfLists = questDao.getNestedQuestList(questDao.getListOfQuests());
        model.with("quests", listOfLists);

        return template.render(model);
    }

    private String getMentorRoom(String id) throws SQLException{
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentorRoom.twig");
        JtwigModel model = JtwigModel.newModel();

        MentorDAO mentorDAO = new MentorDAO();
        Mentor mentor = mentorDAO.getMentor(String.valueOf(id));
        model.with("mentor", mentor);

        return template.render(model);
    }

    private String getMentorCodecoolerList(String id) throws SQLException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentorCodecoolers.twig");
        JtwigModel model = JtwigModel.newModel();


        Mentor mentor = mentorDao.getMentor(String.valueOf(id));
        List<Codecooler> codecoolerList = codecoolerDao.getListOfCodecoolers();

        model.with("mentor", mentor);
        model.with("studentList", codecoolerList);

        return template.render(model);


    }

}

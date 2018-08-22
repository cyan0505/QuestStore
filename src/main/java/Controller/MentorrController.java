package Controller;

import DAO.*;
import Model.Codecooler;
import Model.Mentor;
import Model.Session;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.net.HttpCookie;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;

import static Controller.Controller.parseUri;

public class MentorrController extends AbstractController implements HttpHandler {

    private UserDAO userDao = new UserDAO();
    private ArtefactDAO artefactDao = new ArtefactDAO();
    private QuestDAO questDao = new QuestDAO();
    private MentorDAO mentorDao = new MentorDAO();
    private CodecoolerDAO codecoolerDao = new CodecoolerDAO();
    private final int mainPageIndex = 1;
    private final int idIndex = 2;
    private final int subPageIndex = 3;

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

    }

    private void handleSession(HttpExchange httpExchange) throws IOException{
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie = new HttpCookie("Session-id", cookieStr);



        System.out.println(cookieStr + "tu ma byc cookie");
        String login  = Session.getUSER_LOGIN(cookieStr);
        System.out.println(login + "!!!!!");
        URI uri = httpExchange.getRequestURI();
        String[] parsedUri = parseUri(uri.toString());
        System.out.println(uri);
        chooseProperPage(parsedUri);
    }

    private String chooseProperPage(String[] parsedUri){
        String response="";

        try {
            if (parsedUri.length == 2 && parsedUri[mainPageIndex].equals("mentor")) {
                response = getMentorMainPage(parsedUri[idIndex]);
            }
            else if (parsedUri.length == 3 && parsedUri[subPageIndex].equals("profile")) {
                response = getMentorProfile(parsedUri[idIndex]);
            }
            else if (parsedUri.length == 3 && parsedUri[subPageIndex].equals("contact")){
                response = getMentorContact(parsedUri[idIndex]);
            }
            else if (parsedUri.length == 3 && parsedUri[subPageIndex].equals("store")){
                response = getMentorStore();
            }
            else if (parsedUri.length == 3 && parsedUri[subPageIndex].equals("quest")){
                response = getMentorQuest();
            }
            else if (parsedUri.length == 3 && parsedUri[subPageIndex].equals("inventory")){
                response = getMentorRoom(parsedUri[idIndex]);
            }
            else if (parsedUri.length == 3 && parsedUri[subPageIndex].equals("codecooler")){
                response = getMentorCodecoolerList(parsedUri[idIndex]);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
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

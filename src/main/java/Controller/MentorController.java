package Controller;

import BuisnessLogic.Artifact;
import BuisnessLogic.Quest;
import DAO.ArtefactDAO;
import DAO.CodecoolerDAO;
import DAO.MentorDAO;
import DAO.QuestDAO;
import Model.Codecooler;
import Model.Mentor;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.net.HttpCookie;
import java.net.URI;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class MentorController extends AbstractController implements HttpHandler {

    private ArtefactDAO artefactDao = new ArtefactDAO();
    private QuestDAO questDao = new QuestDAO();
    private CodecoolerDAO codecoolerDao = new CodecoolerDAO();


    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String method = httpExchange.getRequestMethod();
        if (method.equals("POST")) {
            handlePost(httpExchange);
        } else {

            try {
                handleSession(httpExchange);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void handlePost(HttpExchange httpExchange) throws IOException {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");

        URI uri = httpExchange.getRequestURI();
        String[] parsedUri = parseUri(uri.toString());
        // Check if cookie already exists and if it's UUID is contained by sessionPool

        if (cookieStr == null) {
            redirectToLocation(httpExchange, "/html/index.html");
        } else {

            if(parsedUri.length == 3 && parsedUri[2].equals("store")) {

                Artifact artifact = new Artifact("New artifact", "Artifact description", 10, false);
                try {
                    artefactDao.addArtifact(artifact);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if(parsedUri.length == 3 && parsedUri[2].equals("quest")) {
                Quest quest = new Quest("New quest", "Quest description", 10, false);
                try {
                    questDao.addQuest(quest);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void handleSession(HttpExchange httpExchange) throws IOException, SQLException {

        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie = new HttpCookie("Session-id", cookieStr);

        if (cookieStr == null || !isSessionCookie(cookie)) {
            // Object does not exist or is not a file: reject with 404 error.
            send404(httpExchange);
        } else {
            // Object exists and is a file: accept with response code 200.
            String response = chooseProperPage(httpExchange);
            sendReq(httpExchange, response);
        }

    }

    private String chooseProperPage(HttpExchange httpExchange) throws SQLException {
        URI uri = httpExchange.getRequestURI();
        String[] parsedUri = parseUri(uri.toString());

        String response = "";

        int subPageUri = 2;
        int subPageUriLength = 3;
        String login = getLoginByCookie(httpExchange);
        Mentor mentor = getMentorByLogin(login);

        if (parsedUri.length == subPageUriLength) {


            if(parsedUri[subPageUri].equals("profile")){
                response = renderPage(mentor, "templates/mentorProfile.twig");
            }
            else if(parsedUri[subPageUri].equals("contact")){
                response = renderPage(mentor, "templates/mentorContact.twig");
            }
            else if(parsedUri[subPageUri].equals("store")){
                response = getMentorStore();
            }
            else if(parsedUri[subPageUri].equals("quest")){
                response = getMentorQuest();
            }
            else if(parsedUri[subPageUri].equals("codecooler")){
                    response = getMentorCodecoolerList(mentor);
            }

        }else {
            response = renderPage(mentor, "templates/mentorMainPage.twig");
        }

        return response;
    }

//    private String chooseProperPage(HttpExchange httpExchange) throws SQLException {
//        URI uri = httpExchange.getRequestURI();
//        String[] parsedUri = parseUri(uri.toString());
//
//        String response = "";
//
//        int subPageIndex = 2;
//        int subPageUriLength = 3;
//
//        String login = getLoginByCookie(httpExchange);
//        Mentor mentor = getMentorByLogin(login);
//        List<List> nestedArtifactList = artefactDao.getNestedArtifactList(artefactDao.getListOfArtifact());
//        List<List> nestedQuestList = questDao.getNestedQuestList(questDao.getListOfQuests());
//        List<Codecooler> codecoolerList = codecoolerDao.getListOfCodecoolers();
//
//        HashMap<String, Object> data = new HashMap<>();
//        data.put("mentor", mentor);
//        data.put("artifacts", nestedArtifactList);
//        data.put("quests", nestedQuestList);
//        data.put("studentList", codecoolerList);
//
//
//
//        if (parsedUri.length == subPageUriLength) {
//
//            String subPageName = parsedUri[subPageIndex].substring(0,1).toUpperCase() + parsedUri[subPageIndex].substring(1);
//            response = renderPage(data, "templates/mentor" + subPageName + ".twig");
//
//        }else {
//            response = renderPage(data, "templates/mentorMainPage.twig");
//        }
//
//        return response;
//    }

    private String getMentorStore() throws SQLException {
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

    private String getMentorCodecoolerList(Mentor mentor) throws SQLException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentorCodecoolers.twig");
        JtwigModel model = JtwigModel.newModel();

        List<Codecooler> codecoolerList = codecoolerDao.getListOfCodecoolers();

        model.with("mentor", mentor);
        model.with("studentList", codecoolerList);

        return template.render(model);
    }

    private Mentor getMentorByLogin(String login) throws SQLException {
        MentorDAO mentorDAO = new MentorDAO();
        return mentorDAO.getMentor(login);
    }
}

package Controller;

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
import java.io.OutputStream;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;

import static Controller.Controller.parseUri;

public class MentorrController extends AbstractController implements HttpHandler {

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

    }

    private void handleSession(HttpExchange httpExchange) throws IOException, SQLException {
        URI uri = httpExchange.getRequestURI();

        String[] parsedUri = parseUri(uri.toString());
        String response = chooseProperPage(parsedUri, httpExchange);
        OutputStream os = httpExchange.getResponseBody();

        if (response == null) {
            // Object does not exist or is not a file: reject with 404 error.
            send404(httpExchange);
        } else {
            // Object exists and is a file: accept with response code 200.
            System.out.println(httpExchange.getRequestURI().toString());
            sendReq(httpExchange, response);
        }

        os.close();
    }

    private String chooseProperPage(String[] parsedUri, HttpExchange httpExchange) throws SQLException {
        String response = "";
        int subPageUri = 3;

        String login = getLoginByCookie(httpExchange);
        Mentor mentor = getMentorByLogin(login);

        if (parsedUri.length == subPageUri) {

            switch (parsedUri[subPageUri]) {

                case "profile":
                    response = renderPage(mentor, "templates/mentorProfile.twig");
                case "contact":
                    response = renderPage(mentor, "templates/mentorContact.twig");
                case "store":
                    response = getMentorStore();
                case "quest":
                    response = getMentorQuest();
                case "codecooler":
                    response = getMentorCodecoolerList(mentor);
            }
        }else {
            response = renderPage(mentor, "templates/mentorMainPage.twig");
        }



        return response;
    }

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

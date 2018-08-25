package Controller;


import DAO.ArtefactDAO;
import DAO.CodecoolerDAO;
import DAO.QuestDAO;
import Model.Codecooler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.net.HttpCookie;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;


public class CodecoolerController extends AbstractController implements HttpHandler{

    private ArtefactDAO artefactDao = new ArtefactDAO();
    private QuestDAO questDao = new QuestDAO();


    @Override
    public void handle(HttpExchange httpExchange) throws IOException{

        String method = httpExchange.getRequestMethod();


        if(method.equals("POST")){
            handlePost(httpExchange);
        }else{
            try{
            handleSession(httpExchange);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    private void handlePost(HttpExchange httpExchange){

    }

    private void handleSession(HttpExchange httpExchange) throws IOException, SQLException{

        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie = new HttpCookie("Session-id", cookieStr);

        if(cookieStr == null || !isSessionCookie(cookie)){
            redirectToLocation(httpExchange,"/index.html");
        } else{
            String response = chooseProperPage(httpExchange);
            sendReq(httpExchange, response);
        }
    }

    private String chooseProperPage(HttpExchange httpExchange) throws SQLException{
        URI uri = httpExchange.getRequestURI();
        String[] parsedUri = parseUri(uri.toString());

        String response="";

        int subPageIndex = 2;
        int subPageUriLength = 3;

        String login = getLoginByCookie(httpExchange);
        Codecooler codecooler =getCodecoolerByLogin(login);


        if(parsedUri.length == subPageUriLength){

            if(parsedUri[subPageIndex].equals("profile")){
                response = renderPage(codecooler, "templates/codecoolerProfile.twig");
            }
            else if(parsedUri[subPageIndex].equals("contact")){
                response = renderPage(codecooler, "templates/codecoolerContact.twig");
            }
            else if(parsedUri[subPageIndex].equals("store")){
                response = getCodecoolerStore(codecooler);
            }
            else if(parsedUri[subPageIndex].equals("quest")){
                response = getCodecoolerQuest(codecooler);
            }
            else if(parsedUri[subPageIndex].equals("inventory")){
                response = renderPage(codecooler, "templates/codecoolerWallet.twig");
            }

        } else{
            response = renderPage(codecooler, "templates/codecoolerMainPage.twig");
        }
        return response;
    }



    private String getCodecoolerStore(Codecooler codecooler) throws SQLException{
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/codecoolerStore.twig");
        JtwigModel model = JtwigModel.newModel();

        List<List> listOfLists = artefactDao.getNestedArtifactList(artefactDao.getListOfArtifact());

        model.with("artifacts", listOfLists);
        model.with("codecooler", codecooler);

        return template.render(model);
    }

    private String getCodecoolerQuest(Codecooler codecooler) throws SQLException{
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/codecoolerQuest.twig");
        JtwigModel model = JtwigModel.newModel();

        List<List> listOfLists = questDao.getNestedQuestList(questDao.getListOfQuests());

        model.with("codecooler", codecooler);
        model.with("quests", listOfLists);

        return template.render(model);

    }


    private Codecooler getCodecoolerByLogin(String login) throws SQLException {
        CodecoolerDAO codecoolerDAO = new CodecoolerDAO();
        return codecoolerDAO.getCodecooler(login);
    }
}

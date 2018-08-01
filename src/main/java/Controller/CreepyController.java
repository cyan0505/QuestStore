package Controller;

import java.io.*;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DAO.CodecoolerDAO;
import DAO.UserDAO;
import Model.Codecooler;
import Model.Mentor;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class CreepyController implements HttpHandler{

    private UserDAO userDao = new UserDAO();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String method = httpExchange.getRequestMethod();
        String response = "";

        if(method.equals("GET")) {

            String[] uri = Controller.parseFromData(httpExchange.getRequestURI().toString());

            if(uri.length >= 3) {

                if(uri[2].equals("add_mentor")) {



                }

                if(uri[2].equals("add_room")) {

                }

                if(uri[2].equals("delete_mentor")) {

                    int studentId = Integer.parseInt(uri[3]);

                    try {
                        userDao.deleteUser(studentId);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }


                else {
                    Controller.redirectToLocation(httpExchange, "main-admin");
                }

            }

        }

        if(method.equals("POST")) {
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody());
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            Map inputs = Controller.parseUserInfoFromData(formData);


            String firstName = inputs.get("firstName").toString();
            String lastName = inputs.get("lastName").toString();
            String login = inputs.get("login").toString();
            String password = inputs.get("pass").toString();
            String email = inputs.get("email").toString();
            String role = "mentor";

            Mentor mentor = new Mentor(firstName, lastName, login, password, email, role);

            try {
                userDao.addUser(mentor);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }









}

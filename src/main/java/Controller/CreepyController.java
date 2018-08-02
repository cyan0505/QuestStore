package Controller;

import java.io.*;
import java.sql.SQLException;
import java.util.Map;

import DAO.UserDAO;
import Model.Mentor;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public class CreepyController implements HttpHandler{

    private UserDAO userDao = new UserDAO();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String method = httpExchange.getRequestMethod();
        String response = "";

        if(method.equals("GET")) {

            String[] uri = Controller.parseUri(httpExchange.getRequestURI().toString());

            if(uri.length >= 3) {

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

            String user_id = inputs.get("id_user").toString();
            String firstName = inputs.get("firstName").toString();
            String lastName = inputs.get("lastName").toString();
            String login = inputs.get("login").toString();
            String password = inputs.get("pass").toString();
            String email = inputs.get("email").toString();
            String role = "mentor";

            Mentor mentor = new Mentor(firstName, lastName, login, password, email, role, Integer.parseInt(user_id));

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

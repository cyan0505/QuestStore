package Model;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Session {

    private final UUID uuid;
    private final String USER_LOGIN;
    private static List<Session> connectionList = new ArrayList<>();


    public Session(String USER_LOGIN) {
        this.uuid = UUID.randomUUID();
        this.USER_LOGIN = USER_LOGIN;
        connectionList.add(this);
    }

    public static String getUSER_LOGIN(String cookieValue) {
        for (Session session : connectionList){
            if(session.uuid.toString().equals(cookieValue)) {
                return session.USER_LOGIN;
            }
        }
        return null;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void removeSessionBy(String login){

        for(Session session : connectionList){
            if(session.USER_LOGIN.equals(login)){
                connectionList.remove(session);
                break;
            }
        }
    }

    public HttpCookie getCookie(){

        return new HttpCookie("Session-id", this.uuid.toString());

    }
}

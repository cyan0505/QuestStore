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

    public static void removeSessionByCookie(HttpCookie cookie){

        for(Session session : connectionList){
            if(session.uuid.toString().equals(cookie.getValue())){
                connectionList.remove(session);
            }
        }
    }

    public HttpCookie getCookie(){

        return new HttpCookie("Session-id", this.uuid.toString());

    }

    public static boolean connectionHaveThisCookie(HttpCookie cookie){

        for(Session session: connectionList){
            if(session.getUuid().toString().equals(cookie.getValue())){
                return true;
            }
        }
        return false;
    }
}

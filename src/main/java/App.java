import Controller.*;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
public class App {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8800), 0);
        server.createContext("/static", new Controller());
<<<<<<< HEAD
        server.createContext("/codecooler", new CodecoolerController());
=======
        server.createContext("/codecoler", new ControllerCodecooler());
>>>>>>> serverConnection
        server.setExecutor(null);
        server.start();
    }
}

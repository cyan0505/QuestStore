package Controller;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DAO.CodecoolerDAO;
import DAO.UserDAO;
import Model.Codecooler;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import helpers.MimeTypeResolver;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;


public class Controller implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String method = httpExchange.getRequestMethod();
        String response = "";
        URI uri = httpExchange.getRequestURI();
        String[] parsedUri = parseUri(uri.toString());

        if(method.equals("GET")) {

            if(parsedUri[1].equals("static")){
               showMainPage(uri, httpExchange);
            }
        }

        if(method.equals("POST")) {


        }

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

    private void showMainPage(URI uri, HttpExchange httpExchange) throws IOException{
        System.out.println("looking for: " + uri.getPath());
        String path = "." + uri.getPath();
        System.out.println(path);

        ClassLoader classLoader = getClass().getClassLoader();
        URL fileURL = classLoader.getResource(path);
        System.out.println(fileURL.toString());

        OutputStream os = httpExchange.getResponseBody();

        if (fileURL == null) {
            // Object does not exist or is not a file: reject with 404 error.
            send404(httpExchange);
        } else {
            // Object exists and is a file: accept with response code 200.
            sendFile(httpExchange, fileURL);
        }
    }


    public static void redirectToLocation(HttpExchange exchange, String location) {
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.add("Location", location);

        try {
            exchange.sendResponseHeaders(302, -1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        exchange.close();
    }

    public static String[] parseUri(String fromData) {
        String[] pairs = fromData.split("/");
        return pairs;
    }


    public static Map<String, String> parseUserInfoFromData(String fromData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = fromData.split("&");
        for(String pair : pairs) {
            String[] keyValue = pair.split("=");
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }

        return map;
    }

    private void send404(HttpExchange httpExchange) throws IOException {
        String response = "404 (Not Found)\n";
        httpExchange.sendResponseHeaders(404, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.toString().getBytes());
        os.close();
    }

    private void sendFile(HttpExchange httpExchange, URL fileURL) throws IOException {
        // get the file
        File file = new File(fileURL.getFile());
        // we need to find out the mime type of the file, see: https://en.wikipedia.org/wiki/Media_type
        MimeTypeResolver resolver = new MimeTypeResolver(file);
        String mime = resolver.getMimeType();

        httpExchange.getResponseHeaders().set("Content-Type", mime);
        httpExchange.sendResponseHeaders(200, 0);

        OutputStream os = httpExchange.getResponseBody();

        // send the file
        FileInputStream fs = new FileInputStream(file);
        final byte[] buffer = new byte[0x10000];
        int count = 0;
        while ((count = fs.read(buffer)) >= 0) {
            os.write(buffer,0,count);
        }
        os.close();
    }

}

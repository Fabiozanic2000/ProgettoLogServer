package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import db.UtentiDb;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


import org.json.JSONObject;
import java.sql.SQLException;

public class Login implements HttpHandler {

    private UtentiDb db;
    private String email;
    private String password;
    private int rCode;
    private String response;

    /**
     * Costruttore, riceve il db in input
     * @param db
     */
    public Login (UtentiDb db)
    {
        this.db = db;
        email = "";
        password = "";
        response = "";
        rCode = 0;
    }

    /**
     * Metodo che viene invocato quando si contatta /login
     * @param t
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange t) throws IOException {
        URI requestedUri = t.getRequestURI(); //prende l'uri contattato

        try
        {
            if ("POST".equals(t.getRequestMethod()) && requestedUri.compareTo(new URI("/login"))==0) //se sono con il post in /login
            {
                //leggo il body (un oggetto json)
                InputStream input = t.getRequestBody();
                StringBuilder stringBuilder = new StringBuilder();

                new BufferedReader(new InputStreamReader(input))
                        .lines()
                        .forEach( (String s) -> stringBuilder.append(s + "\n") );

                // estraggo le informazioni dell'oggetto json
                String oggettoStringa = stringBuilder.toString();
                JSONObject oggettoJson = new JSONObject(oggettoStringa);

                email = oggettoJson.getString("email");
                password = oggettoJson.getString("password");

                int id;
                id = db.login(email, password);

                if (id != -1) { // se il login è andato a buon fine
                    t.getResponseHeaders().set("Set-Cookie", "id=" + id + "; HttpOnly; Expires=900");
                    response = "\"{ \"id\": \""+id+"\"}\"";
                    rCode = 200;

                } else {
                    response = "{\"errore\": \"Email o password errati\"}";
                    rCode = 403;
                }
            }
            else //diverso da post oppure usa un'altro url
            {
                System.out.println("URI non trovato");
                rCode = 404;
                response = "{\"errore\": \"Paginanon trovata\"}";
            }
        }
        catch (URISyntaxException e) { //errore nell'uri
            e.printStackTrace();
            System.exit(1);
        } catch (SQLException e) { //errore db
            throw new RuntimeException(e);
        }
        catch (Exception e) //errore nella lettura del body della request
        {
            e.printStackTrace();
        }


        //invio la risposta al client (gli header servono per le politiche di cors)
        t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        t.getResponseHeaders().add("Access-Control-Allow-Headers","origin, content-type, accept, authorization");
        t.getResponseHeaders().add("Access-Control-Allow-Credentials", "true");
        t.getResponseHeaders().add("Access-Control-Allow-Methods", "POST");
        t.getResponseHeaders().add("Content-Type", "application/json"); //dico che è un json

        response = "ciao";
        t.sendResponseHeaders(rCode, response.length());
        OutputStream os = t.getResponseBody(); //chiude la comunicazione
        System.out.println("response: "+response);
        System.out.println("response.getBytes(): "+response.getBytes().toString());
        //os.write(response.getBytes());
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();

        System.out.println("sciao");

    }
}

package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import db.UtentiDb;
import org.json.JSONObject;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class Signup implements HttpHandler {
    private UtentiDb db;
    private int rCode;
    private String response;

    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String professione;

    /**
     * Costruttore della signup
     * @param db
     */
    public Signup (UtentiDb db)
    {
        this.db = db;
        rCode = 0;
        response = "";

        nome = "";
        cognome = "";
        email = "";
        password = "";
        professione = "";
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        URI requestedUri = t.getRequestURI(); //prende l'uri contattato

        try
        {
            if ("POST".equals(t.getRequestMethod()) && requestedUri.compareTo(new URI("/signup"))==0) //se sono con il post in /signup
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

                nome = oggettoJson.getString("nome");
                cognome = oggettoJson.getString("cognome");
                email = oggettoJson.getString("email");
                password = oggettoJson.getString("password");
                professione = oggettoJson.getString("professione");


                if (db.signup(nome, cognome, email, password, professione)) // eseguo la query
                {
                    //return "inserito";
                    rCode = 200;
                    response = "inserito";
                }
                else
                {
                    //return "errore";
                    rCode = 404;
                    response = "errore";
                }
            }
            else
            {
                rCode = 404;
                response = "pagina non trovata";
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
        t.getResponseHeaders().add("Content-Type", "application/text"); //dico che è un json

        t.sendResponseHeaders(rCode, response.length());
        OutputStream os = t.getResponseBody(); //chiude la comunicazione
        System.out.println("response: "+response);
        System.out.println("response.getBytes(): "+response.getBytes().toString());
        //os.write(response.getBytes());
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();

        System.out.println("sciaooo");
    }


}

package server;

import com.sun.net.httpserver.HttpExchange;
import db.UtentiDb;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class Signup {
    /**
     * Esegue la signup
     * @param db
     * @param nome
     * @param cognome
     * @param email
     * @param password
     * @param professione
     * @return restituisce se la signup è avvenuta correttamente
     * @throws SQLException
     */
    public String signup(UtentiDb db, HttpExchange t) throws SQLException {

        System.out.println("Mamma");
        String nome = "";
        String cognome = "";
        String email = "";
        String password = "";
        String professione = "";

        try
        {
            //leggo il body (un oggetto json)
            InputStream input = t.getRequestBody();
            StringBuilder stringBuilder = new StringBuilder();

            new BufferedReader(new InputStreamReader(input))
                    .lines()
                    .forEach( (String s) -> stringBuilder.append(s + "\n") );
            System.out.println("eccel: "+stringBuilder);
            // estraggo le informazioni dell'oggetto json
            String oggettoStringa = stringBuilder.toString();
            JSONObject oggettoJson = new JSONObject(oggettoStringa);

            nome = oggettoJson.getString("nome");
            cognome = oggettoJson.getString("cognome");
            email = oggettoJson.getString("email");
            password = oggettoJson.getString("password");
            professione = oggettoJson.getString("professione");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        if (db.signup(nome, cognome, email, password, professione))
        {
            return "inserito";
        }
        else
        {
            return "errore";
        }
    }
}

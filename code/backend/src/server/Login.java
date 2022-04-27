package server;

import com.sun.net.httpserver.HttpExchange;
import db.UtentiDb;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.BufferedReader;
import java.util.Scanner;


import org.json.JSONObject;
import java.sql.SQLException;

public class Login {
    public int login(UtentiDb db, HttpExchange t) throws SQLException {
    //public int login(UtentiDb db, HttpExchange t, String email, String password) throws SQLException {

        System.out.println("Ciao mamma sono nel login");


        String email = "";
        String password = "";

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

            email = oggettoJson.getString("email");
            password = oggettoJson.getString("password");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }


        // From now on, the right way of moving from bytes to utf-8 characters:


        System.out.println(email+ " "+ password);


        int id;
        id = db.login(email, password);

        if (id != -1) {
            System.out.println("CIAO MAMMA SONO LOGGATO id numero " + id);
            t.getResponseHeaders().set("Set-Cookie", "id=" + id + "; HttpOnly; Expires=900");

        } else {
            System.out.println("PUOI BESTEMMIARE");
        }
        System.out.println("id: "+id);
        return id;
    }
}

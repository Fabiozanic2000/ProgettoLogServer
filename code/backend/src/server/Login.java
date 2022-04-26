package server;

import com.sun.net.httpserver.HttpExchange;
import db.UtentiDb;

import java.sql.SQLException;

public class Login {
    public int login(UtentiDb db, HttpExchange t, String email, String password) throws SQLException {

        System.out.println("Ciao mamma sono nel login");

        int id;
        id = db.login(email, password);
        if (id != 0) {
            System.out.println("CIAO MAMMA SONO LOGGATO id numero " + id);
            t.getResponseHeaders().set("Set-Cookie", "id=" + id + "; HttpOnly; Expires=900");

        } else {
            System.out.println("PUOI BESTEMMIARE");
        }
        return id;
    }
}

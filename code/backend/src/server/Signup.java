package server;

import db.UtentiDb;

import java.sql.SQLException;

public class Signup {
    public boolean signup(UtentiDb db, String nome, String cognome, String email, String password, String professione) throws SQLException {
        if (db.signup(nome, cognome, email, password, professione))
            return true;
        return false;
    }
}

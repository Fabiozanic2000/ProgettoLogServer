package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbVerifica {

    public String verifica(UtentiDb db, String id) throws SQLException {
        Connection con = db.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String nome_cognome="";
        try {
            String userNameSurname = "Select nome, cognome from user where id=?"; // Per ottenere il nome di un utente
            ps = con.prepareStatement(userNameSurname);
            ps.setString(1, id);
            rs = ps.executeQuery();
            String nome = rs.getString(1);
            nome = upperCaseFirst(nome);
            String cognome = rs.getString(2);
            cognome = upperCaseFirst(cognome);
            nome_cognome = nome +" "+ cognome;
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return nome_cognome;
    }

    private String upperCaseFirst(String val)
    {
        char[] arr = val.toCharArray();
        arr[0] = Character.toUpperCase(arr[0]);
        return new String(arr);
    }
}

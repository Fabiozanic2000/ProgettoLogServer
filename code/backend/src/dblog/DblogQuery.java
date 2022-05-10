package dblog;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DblogQuery {

    public String query (Dblog db, String testo, String stato, int from, int to) throws SQLException {
        Connection c = db.connect(); // si connette al db
        PreparedStatement pst = null; // prepara la query
        String oggettoRisposta = "";

        boolean isTesto = false;
        boolean isStato = false;

        try {
            String queryLog = "select * from logfile where data>=? and data<=? "; //creo la query

            if (!stato.equals("")) { //se ho inserito uno stato, lo aggiungo nella query
                queryLog += "paese=? ";
                isStato = true;
            }
            if (!testo.equals("")) { //se ho inserito un testo li inserisco nella query
                queryLog += "request=? ";
                isTesto = true;
            }
            queryLog += "order by data";

            //preparo la query
            pst = c.prepareStatement(queryLog);
            pst.setInt(1, from);
            pst.setInt(2, to);
            if (isTesto && isStato) { // se ce sia lo stato sia il testo
                pst.setString(3, stato);
                pst.setString(4, testo);
            } else if (isTesto)
                pst.setString(3, testo); //se ce solo il testo
            else if (isStato)
                pst.setString(3, stato); //se ce solo lo stato

            pst.execute(); //eseguo la query

            ResultSet rs = pst.executeQuery(); //prendo il risultato

            System.out.println("ciao");

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                c.close(); //chiudo la connessione al db
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return "ciao";
    }
}

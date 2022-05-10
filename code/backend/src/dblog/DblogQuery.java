package dblog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe che fa le query al db dei log
 */
public class DblogQuery {

    /**
     * Funzione che esegue la query
     * @param db
     * @param testo
     * @param stato
     * @param from
     * @param to
     * @return
     * @throws SQLException
     */
    public String query (Dblog db, String testo, String stato, int from, int to) throws SQLException {
        Connection c = db.connect(); // si connette al db
        PreparedStatement pst = null; // prepara la query
        String oggettoRisposta = "";

        //queste variabili servono per vedere se devo filtrare per qualcosa o no
        boolean isTesto = false;
        boolean isStato = false;

        try {
            String queryLog = "select * from logfile where data>=? and data<=? "; //creo la query

            if (!stato.equals("")) { //se ho inserito uno stato, lo aggiungo nella query
                queryLog += "and paese=? ";
                isStato = true;
            }
            if (!testo.equals("")) { //se ho inserito un testo li inserisco nella query
                queryLog += "and request=? ";
                isTesto = true;
            }
            queryLog += "order by data;";

            from = 0; //lo metto per il momento solo per stampare qualcosa
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

            oggettoRisposta = "\"log\": [{";
            boolean primaVolta = true; //serve per mettere la virgola bene
            //scorro i record del db
            while(rs.next()) {
                if (primaVolta)
                    primaVolta = false;
                else
                    oggettoRisposta += ", {";

                oggettoRisposta += "\"id\": \""+rs.getInt("id")+"\", "; //aggiungo l'id
                oggettoRisposta += "\"request\": \""+rs.getString("request")+"\", "; //aggiungo la request
                oggettoRisposta += "\"auth\": \""+rs.getString("auth")+"\", "; //aggiungo la auth
                oggettoRisposta += "\"ident\": \""+rs.getString("ident")+"\", "; //aggiungo la ident
                oggettoRisposta += "\"httpmethod\": \""+rs.getString("httpmethod")+"\", "; //aggiungo la request
                oggettoRisposta += "\"time\": \""+rs.getString("time")+"\", "; //aggiungo la request
                oggettoRisposta += "\"response\": \""+rs.getInt("response")+"\", "; //aggiungo la response
                oggettoRisposta += "\"bytes\": \""+rs.getInt("bytes")+"\", "; //aggiungo i bytes
                oggettoRisposta += "\"clientip\": \""+rs.getString("clientip")+"\", "; //aggiungo il clientip
                oggettoRisposta += "\"rawrequest\": \""+rs.getString("rawrequest")+"\", "; //aggiungo la rawrequest
                oggettoRisposta += "\"data\": \""+rs.getInt("data")+"\", "; //aggiungo la data
                oggettoRisposta += "\"timestamp\": \""+rs.getString("timestamp")+"\", "; //aggiungo il timestamp
                oggettoRisposta += "\"paese\": \""+rs.getString("paese")+"\""; //aggiungo la request
                oggettoRisposta += "}";
            }

            oggettoRisposta += "]";

            if (primaVolta) oggettoRisposta = ""; //se primaVOlta è true vuol dire che non ci sono record, quindi devo azzerare l'oggetto che restituisco
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
        return oggettoRisposta;
    }
}

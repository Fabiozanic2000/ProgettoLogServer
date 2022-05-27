package dberrori;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbInsertError {

    public boolean insert(String giornoDellaSettimana, String mese, int giornoDelMese, String orario, int anno, long data, String tipoErrore,
                          int pid, String clientip, int porta_client, String error_code, String payload, String paese, DblogErrori db) {
        Connection c = db.connect();
        PreparedStatement psr;
        String sql = null;
        try {
            sql = "insert into logerror (giorno_della_settimana, mese, giorno_del_mese, orario, anno, data, tipo_errore, pid, clientip, porta_client, error_code, paese, payload) VALUES (?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?)";
            psr = c.prepareStatement(sql);
            psr.setString(1, giornoDellaSettimana);
            psr.setString(2, mese);
            psr.setInt(3, giornoDelMese);
            psr.setString(4, orario);
            psr.setInt(5, anno);
            psr.setLong(6, data);
            psr.setString(7, tipoErrore);
            psr.setInt(8, pid);
            psr.setString(9, clientip);
            psr.setInt(10, porta_client);
            psr.setString(11, error_code);
            psr.setString(12, payload);
            psr.setString(13, paese);
            psr.executeUpdate();
            return true;
        } catch (SQLException e) {
            //System.out.println(e.getMessage());
            return false;
        }finally {
            try {
                c.close();
            } catch (SQLException ex) {
                //ex.printStackTrace();
            }
        }
    }
}


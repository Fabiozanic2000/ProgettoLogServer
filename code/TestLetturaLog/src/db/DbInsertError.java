package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbInsertError {

    public boolean insert(String giorno_della_settimana, String mese, int giorno_del_mese, String orario, int anno, String tipo_errore,
                          int pid, String clientip, int porta_client, String error_code, String payload, String paese, DblogErrori db) {
        Connection c = db.connect();
        PreparedStatement psr;
        String sql = null;
        try {
            sql = "insert into logerror (giorno_della_settimana, mese, giorno_del_mese, orario, anno, tipo_errore, pid, clientip, porta_client, error_code, paese, payload) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?)";
            psr = c.prepareStatement(sql);
            psr.setString(1, giorno_della_settimana);
            psr.setString(2, mese);
            psr.setInt(3, giorno_del_mese);
            psr.setString(4, orario);
            psr.setInt(5, anno);
            psr.setString(6, tipo_errore);
            psr.setInt(7, pid);
            psr.setString(8, clientip);
            psr.setInt(9, porta_client);
            psr.setString(10, error_code);
            psr.setString(11, payload);
            psr.setString(12, paese);
            psr.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }finally {
            try {
                c.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}


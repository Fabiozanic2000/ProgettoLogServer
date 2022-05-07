package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbInsert {
    public boolean insert(String request, String auth, String ident, String httpmethod, String time, int response,
                          int bytes, String clientip, String rawrequest, String data, String timestamp, String paese,
                          Dblog db) {
        Connection c = db.connect();
        PreparedStatement psr;
        String sql = null;
        try {
            sql = "insert into logfile (request, auth, ident, httpmethod, time, response, bytes, clientip, rawrequest, data, timestamp, paese) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?)";
            psr = c.prepareStatement(sql);
            psr.setString(1, request);
            psr.setString(2, auth);
            psr.setString(3, ident);
            psr.setString(4, httpmethod);
            psr.setString(5, time);
            psr.setInt(6, response);
            psr.setInt(7, bytes);
            psr.setString(8, clientip);
            psr.setString(9, rawrequest);
            psr.setString(10, data);
            psr.setString(11, timestamp);
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

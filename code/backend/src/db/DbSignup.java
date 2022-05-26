package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbSignup {
    public boolean signup (String nome, String cognome, String email, String password, String professione, UtentiDb db) throws SQLException {
        Connection c = db.connect();
        PreparedStatement psr;
        int tot = 0;
        String sql = null;
        try {
            sql = "select count(*) as tot from user where email=?";
            psr = c.prepareStatement(sql);
            psr.setString(1, email);
            psr.execute();
            ResultSet rs = psr.getResultSet();
            tot = -1;
            while (rs.next()) {
                tot = rs.getInt("tot");

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        if (tot == 0) {
            try {
                sql = "insert into user (nome, cognome, email, password, professione) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstmt = c.prepareStatement(sql);
                pstmt.setString(1, nome);
                pstmt.setString(2, cognome);
                pstmt.setString(3, email);
                pstmt.setString(4, password);
                pstmt.setString(5, professione);
                pstmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                //System.out
                        //.println(e.getMessage());
                return false;
            } finally {
                try {
                    c.close();
                } catch (SQLException ex) {
                   // ex.printStackTrace();
                }
            }
        }
        c.close();
        return false;
    }
}

package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbLogin {
    public int login(String email, String password, UtentiDb db)
    {
        Connection c = db.connect();
        PreparedStatement pst = null;
        System.out.println("nel db: "+email+" "+password);
        int id = 0;
        try {
            String sql = "Select id from user where email=? and password=?;";
            pst = c.prepareStatement(sql);
            pst.setString(1, "'"+email+"'");
            pst.setString(2, "'"+password+"'");
            pst = c.prepareStatement(sql);
            // pst.execute();
            ResultSet rs = pst.executeQuery();
            id = rs.getInt(1);
            System.out.println(id);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return id;
    }
}

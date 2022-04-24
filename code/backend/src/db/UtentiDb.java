package db;

import java.io.File;
import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class UtentiDb {
    private final String DBNAME;

    public UtentiDb(String dbname) {
        this.DBNAME = dbname;
    }

    public boolean signup(String nome, String cognome, String email, String password, String professione)
            throws SQLException {
        Connection c = connect();
        PreparedStatement psr;
        int tot=0;
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
                // System.out.print(tot);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        if (tot == 0) {
            System.out.println("Sono vicino al try");
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
                System.out.println(e.getMessage());
                return false;
            } finally {
                try {
                    c.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        }
        c.close();
        return false;
    }

    public void checkCreateDb() {
        File file = new File(DBNAME);
        if (file.exists()) {
            System.out.println("Il database esiste");
        } else {
            try {
                File f = new File("");
                // System.out.println(f.getAbsolutePath() + File.separator + "database");
                Class.forName("org.sqlite.JDBC");
                Connection c = getConnection(
                        "jdbc:sqlite:" + f.getAbsolutePath() + File.separator + "database" + File.separator +
                                DBNAME);
                createTableUser(c);
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
        }
    }

    public Connection connect() {
        Connection c = null;
        try {
            File f = new File("");
            Class.forName("org.sqlite.JDBC");
            c = getConnection("jdbc:sqlite:" + f.getAbsolutePath() + File.separator + "database" + File.separator +
                    DBNAME);
            // return c;
        } catch (Exception e) {
            // System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return c;
    }

    private void createTableUser(Connection c) {
        try {
            Statement stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS user " +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome TEXT NOT NULL, " +
                    "cognome TEXT NOT NULL," +
                    "email TEXT NOT NULL, " +
                    "password TEXT NOT NULL, " +
                    "professione TEXT NOT NULL)";

            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * ritorna l'ID dell'utente se il login è andato a buon fine, sennò ritorna 0
     */
    public int login(String email, String password) throws SQLException {
        Connection c = connect();
        PreparedStatement pst = null;
        int id = 0;
        try {
            String sql = "Select id from user where email=? and password=?;";
            pst = c.prepareStatement(sql);
            pst.setString(1, email);
            pst.setString(2, password);
            // pst.execute();
            ResultSet rs = pst.executeQuery();
            id = rs.getInt(1);
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

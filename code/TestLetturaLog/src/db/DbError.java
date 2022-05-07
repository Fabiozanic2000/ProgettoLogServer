package db;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static java.sql.DriverManager.getConnection;

public class DbError {
    private final String DBNAME;

    public DbError(String dbname) {
        this.DBNAME = dbname;
    }

   /* public boolean signup(String nome, String cognome, String email, String password, String professione) throws SQLException {
        DbSignup signup = new DbSignup();
        return signup.signup(nome, cognome, email, password, professione, this);
    }*/

    /**
     * Controlla di aver creato il Database e la tabella degli utenti
     */
    public void checkCreateDb() {
        File file = new File(DBNAME);
        if (file.exists()) {
            System.out.println("Il database esiste");
        } else {
            try {
                // ottengo il percorso per il database
                //String percorso = this.getPercorso();
                Class.forName("org.sqlite.JDBC");
                Connection c = getConnection(
                        "jdbc:sqlite:" + DBNAME);
                //Connection c = getConnection("jdbc:sqlite:"+File.separator+DBNAME);
                // se non esiste, creo la tabella degli utenti
                createTableUser(c);
            } catch (Exception e) { // eventuali errori
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
        }
    }

    /**
     * Si connette al Db
     *
     * @return Connection, la connessione al Db
     */
    public Connection connect() {
        Connection c = null;
        try {
            // ottiene il percorso al db
            //String percorso = this.getPercorso();
            Class.forName("org.sqlite.JDBC");
            //c = getConnection("jdbc:sqlite:" + percorso + File.separator + "database" + File.separator + DBNAME);
            c = getConnection("jdbc:sqlite:" + DBNAME);
        } catch (Exception e) {
            // eventuali errori
            System.exit(0);
        }
        return c;
    }

    /**
     * Crea la tabella utenti del db
     *
     * @param c
     */
    private void createTableUser(Connection c) {
        try {
            Statement stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS logfile " +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "request TEXT , " +
                    "auth TEXT," +
                    "ident TEXT," +
                    "httpmethod TEXT," +
                    "time TEXT," +
                    "response INT," +
                    "bytes INT, " +
                    "clientip TEXT," +
                    "rawrequest TEXT ," +
                    "data TEXT, " +
                    "timestamp TEXT , " +
                    "paese TEXT)";

            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Serve per ottenere il percorso assoluto della root del progetto
     *
     * @return
     */
    public String getPercorso() {
        File f = new File("");
        String percorso = f.getAbsolutePath();
        if (percorso.contains("/code/backend")) {
            percorso = percorso.replace("/code/backend", "/");
        } else if (percorso.contains("/code")) {
            percorso = percorso.replace("/code", "/");
        }
        return percorso;
    }

    /**
     * ritorna l'ID dell'utente se il login è andato a buon fine, sennò ritorna 0
     */
    /*public int login(String email, String password) throws SQLException {
        DbLogin login = new DbLogin();
        return login.login(email, password, this);
    }
    */
    public boolean insert(String request, String auth, String ident, String httpmethod, String time, int response,
                          int bytes, String clientip, String rawrequest, String data, String timestamp, String paese) throws SQLException {
        DbInsert dbInsert = new DbInsert();
        return dbInsert.insert(request, auth, ident, httpmethod, time, response, bytes, clientip, rawrequest, data, timestamp, paese, this);
    }
}
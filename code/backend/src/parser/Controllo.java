package parser;

import db.UtentiDb;

import javax.mail.MessagingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Controllo {
    public int contatore = 0;
    public long lastTimestamp = -1;

    public void check(long currentTime, int threshold, String ip_address, String stato) {
        if (lastTimestamp == -1) {
            lastTimestamp = currentTime;
        }
        else if ((currentTime - lastTimestamp) <= threshold){
            contatore++;
            lastTimestamp = currentTime;
        }
        else{
            contatore = 0;
        }
        if (contatore >= 4) {
            // In questa funzione bisogna accedere alle mail dei tecnici nel db
            // Togliere le mail attuali e mettere verosimili per non mandarle a sconosciute
            try {
                //Mail.sendEmail("pds.teamuno@gmail.com", contatore, ip_address, stato);
                UtentiDb dbUtenti = new UtentiDb("utentidb");
                dbUtenti.checkCreateDb();
                ArrayList<String> indirizzi = new ArrayList<String>();
                indirizzi = dbUtenti.indirizziMail();
                for (String indirizzo : indirizzi) {
                    Mail.sendEmail(indirizzo, contatore, ip_address, stato);
                }
            } catch (MessagingException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
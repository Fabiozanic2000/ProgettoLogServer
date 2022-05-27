package parser;

import db.UtentiDb;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Controllo {
    private int contatore = 0;
    private long lastTimestamp = -1;

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
            try {
                UtentiDb dbUtenti = new UtentiDb("utentidb");
                dbUtenti.checkCreateDb();
                ArrayList<String> indirizzi;
                indirizzi = dbUtenti.indirizziMail();
                List<String> dest = new ArrayList<>(indirizzi);
                //List<String> destt = dest;
                Mail.sendEmail(dest, contatore, ip_address, stato);
            } catch (MessagingException | SQLException e) {
                //e.printStackTrace();
            }
        }
    }
}
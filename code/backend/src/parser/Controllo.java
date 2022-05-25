package parser;

import javax.mail.MessagingException;

public class Controllo {
    public int contatore = 0;
    public long lastTimestamp = -1;

    public void check(long currentTime, int threshold) {
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
        if (contatore == 4) {
            // In questa funzione bisogna accedere alle mail dei tecnici nel db
            // Togliere le mail attuali e mettere verosimili per non mandarle a sconosciute
            try {
                Mail.sendEmail("pds.teamuno@gmail.com");
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            // TODO
            System.out.println("\t4 messaggi di fila");
        }
    }
}
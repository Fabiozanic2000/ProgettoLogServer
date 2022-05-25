package parser;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Classe per l'invio di mail di notifica.
 */
public class Mail {
    /**
     * Invia la mail di notifica.
     *
     * @param recepient
     * @throws MessagingException
     */
    public static void sendEmail(String recepient, int messaggiErrati, String ip_address, String stato) throws MessagingException {
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        String myAccountEmail = "pds.teamuno@gmail.com";
        String password = "@mazepin9";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        Message message = prepareMessage(session, myAccountEmail, recepient, messaggiErrati, ip_address, stato);
        Transport.send(message);
        System.out.println("Messaggio inviato con successo");
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recepient, int messaggiErrati,
                                          String ip_address, String stato) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Do not reply");

            float probabilita= (float) (messaggiErrati*99.9/15); // Probabilità approssimabile a 100 se arrivano 15 messaggi errati

            message.setContent("<h2>Rilevato traffico malevolo. </h2> <hp> Sono arrivati: " + messaggiErrati + " pacchetti errati nel giro" +
                    "di pochi secondi. <br /> " +
                    "E' previsto che saliranno con una probabilità di " + probabilita + "%<br/>" +
                    "da: <ul><li> ip: " + ip_address + " </li> <li> località: " + stato + "</l1> </ul> </p>", "text/html");
            return message;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }
}
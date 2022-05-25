package parser;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Classe per l'invio di mail di notifica.
 */
public class Mail{
    /**
     * Invia la mail di notifica.
     * @param recepient
     * @throws MessagingException
     */
    public static void sendEmail(String recepient) throws MessagingException {
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
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        Message message = prepareMessage(session, myAccountEmail, recepient);
        Transport.send(message);
        System.out.println("Messaggio inviato con successo");
    }
    private static Message prepareMessage(Session session, String myAccountEmail, String recepient){
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Do not reply");
            message.setText("Rilevato traffico malevolo");
            return message;
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return null;

    }
}
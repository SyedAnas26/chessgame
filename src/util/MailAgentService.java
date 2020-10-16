package util;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Properties;


public class MailAgentService {
    public static String sendMail(String to, String challengeLink,String senderName) throws Exception {
        boolean sent=false;
        System.out.println("mailTo "+to);
        String from= "MailAgent@lichesspro.com";
        System.out.println("challenge Link "+challengeLink);
        final String username = "emailapikey";
        final  String password=getPass();
        String host = "smtp.transmail.com";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.ssl.trust", host);
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {

            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("Challenge Invitation");

            // Put the content of your message
            String mailContent=getMailContent();
            mailContent=mailContent.replace("Name",senderName).replace("clink",challengeLink);
            message.setContent(mailContent,"text/html; charset=utf-8");
            // Send message
            Transport.send(message);
            sent = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sent) {
            return "{\"sendStatus\":\"sent\"}";
        } else
            return "{\"sendStatus\":\"failed\"}";
    }

    private static String getMailContent() throws Exception {
        return new String(Files.readAllBytes(Paths.get(getPath()+"/emailUtils/MailTemplate.html")));
    }

     private static String getPath() throws Exception {
        String path;
         switch (OSHelper.getOSType())
         {
             case MAC:
                 path="/Users/muthu-1987/git/chessgame/";
                 break;
             case UNIX:
                path="/root/apache-tomcat-9.0.37/webapps/ROOT/";
                break;
             case WINDOWS:
                 path="C:/Users/User/Desktop/Saddique/apache-tomcat-9.0.36/webapps/ROOT/";
                 break;
             default:
                 throw new IllegalStateException("Unexpected value: " + OSHelper.getOSType());
         }
         return path;
     }

    private static String getPass() throws Exception {
        String content = new String(Files.readAllBytes(Paths.get(getPath()+"/emailUtils/passw.txt")));
        content=content.trim();
        byte[] decoded= Base64.getDecoder().decode(content);
        return new String(decoded);
    }
}




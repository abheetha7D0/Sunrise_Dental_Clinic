/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import javax.swing.SwingUtilities;
/**
 *
 * @author ASUS
 */
public class EmailUtility {
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String SENDER_EMAIL = "abeetha.dilushan@gmail.com"; 
    private static final String SENDER_PASSWORD = "xumwyxgctritnkrv"; 

    public static void sendEmail(String recipientEmail, String subject, String htmlContent) {
        
        Thread emailThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true"); 
                props.put("mail.smtp.host", SMTP_HOST);
                props.put("mail.smtp.port", SMTP_PORT);

                Session session = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
                    }
                });

                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(SENDER_EMAIL));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
                    message.setSubject(subject);
                    message.setContent(htmlContent, "text/html; charset=utf-8"); 

                    Transport.send(message);
                    System.out.println("Email sent successfully to: " + recipientEmail);

                } catch (MessagingException e) {
                    System.err.println("Email failed to dispatch: " + e.getMessage());
                }
            }
        });
        
        emailThread.start(); 
    }
}

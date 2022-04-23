package edu.wpi.cs3733.D22.teamB;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailHelper {
  private static final String from = "bombtasticbugbears@gmail.com";
  private static final String password = "Wong69420";
  private static final String sub = "Authentication Code";

  public static void send(String to, String msg) {
    // Get properties object
    Properties props = new Properties();
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.socketFactory.port", "587");
    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.port", "587");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    props.put("mail.smtp.ssl.protocols", "TLSv1.2");
    // get Session
    Session session =
        Session.getDefaultInstance(
            props,
            new javax.mail.Authenticator() {
              protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
              }
            });
    // compose message
    try {
      MimeMessage message = new MimeMessage(session);
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
      message.setSubject(sub);
      message.setText(msg);
      // send message
      Transport.send(message);
      System.out.println("message sent successfully");
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) {
    // from,password,to,subject,message
    EmailHelper.send("bjmannion@wpi.edu", "123456");
    // change from, password and to
  }
}

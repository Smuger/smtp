import sun.plugin2.liveconnect.JSExceptions;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class sendEmail extends Thread{
    public void run() {
        try{
            generateAndSendEmail();
        } catch (Exception ex) {}
    }
    public static void generateAndSendEmail() throws AddressException, MessagingException, IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        String email = "thisemaildoesnot@exist.com";
        String subject = "no subject";
        String content = "content";


        System.out.println("\nSetting properties [PORT, AUTH, TSL]...");
        SMTP.mailServerProperties = System.getProperties();
        SMTP.mailServerProperties.put("mail.smtp.port", "587");
        SMTP.mailServerProperties.put("mail.smtp.auth", "true");
        SMTP.mailServerProperties.put("mail.smtp.starttls.enable", "true");
        System.out.println("Success");

        System.out.println("\nCreating session");
        SMTP.getMailSession = Session.getDefaultInstance(SMTP.mailServerProperties, null);
        System.out.println("Creating MIME message template");
        SMTP.generateMailMessage = new MimeMessage(SMTP.getMailSession);

        System.out.println("Provide destination email:");
        email = input.readLine();
        SMTP.generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

        System.out.println("\nProvide subject:");
        subject = input.readLine();
        SMTP.generateMailMessage.setSubject(subject);

        System.out.println("\nProvide content:");
        content = input.readLine();
        SMTP.generateMailMessage.setContent(content, "text/html");

        System.out.println("\n\nProtocol SMTP starting...");

        Transport transport = SMTP.getMailSession.getTransport("smtp");

        System.out.println("\nCredentials provided");
        transport.connect("smtp.gmail.com", "smtp.kwietniewski@gmail.com", ""); // <- YOUR PASSWORD

        System.out.println("\nAttempting sending");
        transport.sendMessage(SMTP.generateMailMessage, SMTP.generateMailMessage.getAllRecipients());
        System.out.println("\nCongratulations email has been send");

        System.out.println("\nClosing TCP connection");
        transport.close();
        System.exit(0);
    }
}

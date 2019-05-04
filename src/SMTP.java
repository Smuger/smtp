import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Scanner;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author Crunchify.com
 *
 */

public class SMTP {

    static Properties mailServerProperties;
    static Session getMailSession;
    static MimeMessage generateMailMessage;

    static String action;

    public static void main(String args[]) throws AddressException, MessagingException, IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Kwietniewski Email Service");

        System.out.println("\nProvice name of service [email, message, ftp]:");
        action = scanner.next();
        if (action.equals("email"))
            generateAndSendEmail();
        else
            System.exit(0);

    }

    public static void generateAndSendEmail() throws AddressException, MessagingException, IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        String email = "thisemaildoesnot@exist.com";
        String subject = "no subject";
        String content = "content";


        System.out.println("\nSetting properties [PORT, AUTH, TSL]...");
        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        System.out.println("Success");

        System.out.println("\nCreating session");
        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        System.out.println("Creating MIME message template");
        generateMailMessage = new MimeMessage(getMailSession);

        System.out.println("Provide destination email:");
        email = input.readLine();
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

        System.out.println("\nProvide subject:");
        subject = input.readLine();
        generateMailMessage.setSubject(subject);

        System.out.println("\nProvide content:");
        content = input.readLine();
        generateMailMessage.setContent(content, "text/html");

        System.out.println("\n\nProtocol SMTP starting...");

        Transport transport = getMailSession.getTransport("smtp");

        System.out.println("\nCredentials provided");
        transport.connect("smtp.gmail.com", "smtp.kwietniewski@gmail.com", "");

        System.out.println("\nAttempting sending");
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        System.out.println("\nCongratulations email has been send");

        System.out.println("\nClosing TCP connection");
        transport.close();
        System.exit(0);
    }
}
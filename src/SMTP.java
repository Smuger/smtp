import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Scanner;
import java.util.TreeSet;
import javax.mail.*;
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
        sendEmail sendemail = new sendEmail();
        showEmail showemail = new showEmail();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Kwietniewski Email Service");

        System.out.println("\nProvice name of service [send email, show inbox]:");
        action = scanner.next();

        if (action.equals("send")){
            //sendEmail.generateAndSendEmail();
            sendemail.start();
        }
        else if (action.equals("show"))
        {
            //showEmail.getEmailsFromMailServer();
            showemail.start();
        }
        else
            System.exit(0);

    }
}
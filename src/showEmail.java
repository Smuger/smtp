import sun.awt.windows.ThemeReader;

import javax.mail.*;
import java.util.Iterator;
import java.util.Properties;
import java.util.TreeSet;

public class showEmail extends Thread {
    public void run(){
        getEmailsFromMailServer();
    }
    public static void getEmailsFromMailServer(){
        Properties props = new Properties();
        String host      = "imap.gmail.com";
        String username  = "smtp.kwietniewski@gmail.com";
        String password  = ""; // YOUR PASSWORD
        String provider  = "imaps";

        try
        {
            props.put("mail.mime.allowutf8", true);

            Session session = Session.getDefaultInstance(props, null);
            Store store     = session.getStore(provider);
            store.connect(host, username, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();

            if (messages.length == 0)
                System.out.println("Inbox is empty");

            TreeSet treeSet = new TreeSet();

            for(int i = 0; i < messages.length; i++)
            {
                String from = getFrom(messages[i]);
                if ( from!=null)
                {
                    from = removeQuotes(from);
                    treeSet.add(from);
                }
            }

            Iterator it = treeSet.iterator();

            while ( it.hasNext() )
            {
                System.out.println("from: " + it.next());
            }

            inbox.close(false);
            store.close();
        }
        catch (NoSuchProviderException nspe)
        {
            System.err.println("invalid provider name");
        }
        catch (MessagingException me)
        {
            System.err.println("messaging exception");
            me.printStackTrace();
        }
        catch (Exception ex){
            System.out.println("No internet connection");
        }
    }

    private static String getFrom(Message javaMailMessage)
            throws MessagingException
    {
        String from = "";
        Address a[] = javaMailMessage.getFrom();
        if ( a==null ) return null;
        for ( int i=0; i<a.length; i++ )
        {
            Address address = a[i];
            from = from + address.toString();
        }

        return from;
    }

    private static String removeQuotes(String stringToModify)
    {
        int indexOfFind = stringToModify.indexOf(stringToModify);
        if ( indexOfFind < 0 ) return stringToModify;

        StringBuffer oldStringBuffer = new StringBuffer(stringToModify);
        StringBuffer newStringBuffer = new StringBuffer();
        for ( int i=0, length=oldStringBuffer.length(); i<length; i++ )
        {
            char c = oldStringBuffer.charAt(i);
            if ( c == '"' || c == '\'' )
            {

            }
            else
            {
                newStringBuffer.append(c);
            }

        }
        return new String(newStringBuffer);
    }
}

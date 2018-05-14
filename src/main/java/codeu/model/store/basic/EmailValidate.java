package codeu.model.store.basic;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailValidate {
    public static final String MAIL_REGISTRATION_SITE_LINK = "http://localhost:8080/demos/VerifyRegisteredEmailHash";
    // Replace sender@example.com with your "From" address.
    // This address must be verified.
    static final String FROM = "qdao@codeustudents.com";
    static final String FROMNAME = "Web Chat";
    
    // Replace recipient@example.com with a "To" address. If your account 
    // is still in the sandbox, this address must be verified.
    static final String TO = "lizdao@vt.edu";
    
    // Replace smtp_username with your Amazon SES SMTP user name.
    static final String SMTP_USERNAME = "AKIAJLZFZMYKEYFQYLNA";
    
    // Replace smtp_password with your Amazon SES SMTP password.
    static final String SMTP_PASSWORD = "AsMmHpKmXdVP2c4tE1jYncf0o3hGCLbNUvSezoum9dnI";
    
    // The name of the Configuration Set to use for this message.
    // If you comment out or remove this variable, you will also need to
    // comment out or remove the header below.
    static final String CONFIGSET = "ConfigSet";
    
    // Amazon SES SMTP host name. This example uses the US West (Oregon) region.
    // See http://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html#region-endpoints
    // for more information.
    static final String HOST = "email-smtp.us-east-1.amazonaws.com";
    
    // The port you will connect to on the Amazon SES SMTP endpoint. 
    static final int PORT = 587;
    
    static final String SUBJECT = "Amazon SES test (SMTP interface accessed using Java)";
    
    static final String BODY = String.join(
            System.getProperty("line.separator"),
            "<h1>Amazon SES SMTP Email Test</h1>",
            "<p>This email was sent with Amazon SES using the ", 
            "<a href='https://github.com/javaee/javamail'>Javamail Package</a>",
            " for <a href='https://www.java.com'>Java</a>."
        );
    
    
        public static void sendEmailRegistrationLink(String id, String email, String hash) throws MessagingException{
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", HOST);
            props.put("mail.smtp.port", "587");
            props.put("mail.transport.protocol", "smtp");

            Session session = Session.getInstance(props,
              new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
                }
              });

            String link = MAIL_REGISTRATION_SITE_LINK+"?scope=activation&userId="+id+"&hash="+hash;
            
              StringBuilder bodyText = new StringBuilder(); 
                bodyText.append("<div>")
                     .append("  Dear User<br/><br/>")
                     .append("  Thank you for registration. Your mail ("+email+") is under verification<br/>")
                     .append("  Please click <a href=\""+link+"\">here</a> or open below link in browser<br/>")
                     .append("  <a href=\""+link+"\">"+link+"</a>")
                     .append("  <br/><br/>")
                     .append("  Thanks,<br/>")
                     .append("  SodhanaLibrary Team")
                     .append("</div>");
                Message message = new MimeMessage(session);
                
               
                try {
                    message.setFrom(new InternetAddress(SMTP_USERNAME));
                    message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(email));
                    message.setSubject("Email Registration");
                    message.setContent(bodyText.toString(), "text/html; charset=utf-8");
                    
                    // Create a transport.
                    Transport transport;
                    transport = session.getTransport();
                    

                    System.out.println("Sending...");
                    
                    // Connect to Amazon SES using the SMTP username and password you specified above.
                    transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
                    
                    // Send the email.
                    transport.sendMessage(message, message.getAllRecipients());
                    System.out.println("Email sent!");
                    transport.close();//close and terminate transport
                }
              
                catch (Exception ex) {
                    System.out.println("The email was not sent.");
                    System.out.println("Error message: " + ex.getMessage());
                }
               
        }

  

  
}
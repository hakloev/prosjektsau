package mail;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 
 * @author maxmelander
 *
 */
public class GMail {
	private String username = "sauer.er.konge@gmail.com" ;
	private String password = "prosjektsau";
	private Properties props;
	
	
	public GMail(){
		
		props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
	}
	
	/**
	 * Sends an email to the given recipient, with the given subject line
	 * and main body text. 
	 * 
	 * @param recipient	the recipient mail address 
	 * @param subject	the mail's subject line
	 * @param text		the mail's body text
	 * 
	 */
	public void sendMail(String recipient, String subject, String text){
		
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });
		 
				try {
		 
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress("from-email@gmail.com"));
					message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(recipient));
					message.setSubject(subject);
					message.setText(text);
		 
					Transport.send(message);
		 
					System.out.println("Done");
		 
				} catch (MessagingException e) {
					throw new RuntimeException(e);
				}
	}
}

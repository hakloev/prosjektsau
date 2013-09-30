package mail;

public class MailTester {
	
	public static void main(String[] args){
		GMail mailsender = new GMail();
		String recipient = "maxelias.melander@gmail.com";
		String subject = "THIS IS THE BEST SUBJECT LINE EVARRR";
		String text = "Hello, is it me you're looking for?";
		mailsender.sendMail(recipient, subject, text);
	}
}

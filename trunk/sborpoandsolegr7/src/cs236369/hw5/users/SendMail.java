package cs236369.hw5.users;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import cs236369.hw5.Utils;

public class SendMail {
	
	public static class SendingMailError extends Exception{}

	private String from;
	private String to;
	private String subject;
	private String text;
	
	public SendMail( String to,String username,String newPass){
		this.from = Utils.supportMail;
		this.to = to;
		this.subject ="no-reply : Lab Support - password recovery";
		this.text = "Hello "+username+"\n Your new password is: "+newPass+"\n You can try to log in with this password now.";
	}
	
	public void send() throws SendingMailError{
		
		Properties props = new Properties();
		//props.put("mail.smtp.host", "smtp.012.net.il");
		//props.put("mail.smtp.port", "25");
		props.put("mail.smtp.auth","true");
		
		props.put("mail.smtp.host","smtp.gmail.com");
		props.put("mail.smtp.port","465");
		props.put("mail.smtp.starttls.enable","true");
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
	
		Session mailSession = Session.getDefaultInstance(props,new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {
	//		return new PasswordAuthentication("okesj", "vp272xbd");
			return new PasswordAuthentication("lab.supp", "qazwsxedc");
			}
			});
		Message simpleMessage = new MimeMessage(mailSession);
		
		InternetAddress fromAddress = null;
		InternetAddress toAddress = null;
		try {
			fromAddress = new InternetAddress(from);
			toAddress = new InternetAddress(to);
		} catch (AddressException e) {
			e.printStackTrace();
			throw new SendingMailError();
		}
		
		try {
			simpleMessage.setFrom(fromAddress);
			simpleMessage.setRecipient(RecipientType.TO, toAddress);
			simpleMessage.setSubject(subject);
			simpleMessage.setText(text);
			
			Transport.send(simpleMessage);			
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new SendingMailError();
		}		
	}
}
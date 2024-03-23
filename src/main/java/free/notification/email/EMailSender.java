package free.notification.email;

import free.notification.config.EmailConfig;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EMailSender {

	private String host;
	private String fromName;
	private String username;
	private String password;

	private Properties properties;

	public EMailSender(EmailConfig emailConfig) {
		this.host = emailConfig.getHost();
		this.fromName = emailConfig.getSender();

		this.username = emailConfig.getUsername();
		this.password = emailConfig.getPassword();

		properties = createProperties();
	}

	public EMailSender(String host, String fromName, String username, String password) {
		this.host = host;
		this.fromName = fromName;

		this.username = username;
		this.password = password;

		properties = createProperties();
	}

	private Properties createProperties() {
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", host); // for gmail use smtp.gmail.com
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.debug", "false");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.socketFactory.fallback", "false");
		return properties;
	}

	public boolean hasValidConfiguration() {
		return host != null && !host.trim().isEmpty();
	}

	public void sendEmail(String to, String subject, String text) {
		if (to == null || to.trim().isEmpty()) {
			System.err.println("Cannot send email, missing receiver address: subject='" + subject + "'");
			return;
		}

		System.out.println("Sending E-Mail: " + subject);

		// Get system properties
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(fromName));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject(subject);

			// Now set the actual message
			message.setText(text);

			// Send message
			Transport.send(message);
		} catch (MessagingException mex) {
			mex.printStackTrace();
			System.out.println("Error sending email: " + mex.getMessage());
		}
	}

}

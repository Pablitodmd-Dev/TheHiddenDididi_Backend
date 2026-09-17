package com.example.webthymeleaf.service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Value("${sendgrid.api.key}")
	private String sendGridApiKey;

	@Value("${sendgrid.from.email}")
	private String fromEmail;

	@Value("${app.url}")
	private String appUrl;

	public void sendVerificationEmail(String destinationEmail, String token) {
		Email from = new Email(fromEmail);
		Email to = new Email(destinationEmail);
		String subject = "Verify your account in El Dididi Oculto";
		String verificationLink = appUrl + "/auth/verify?token=" + token;
		String content = "<h2>Welcome to El Dididi Oculto!</h2>"
				+ "<p>Thanks for signing up. Click the link below to activate your account:</p>"
				+ "<a href='" + verificationLink + "' style='background-color:#C8920A;color:white;"
				+ "padding:12px 24px;border-radius:8px;text-decoration:none;font-weight:bold;'>"
				+ "Verify my account</a>"
				+ "<p>If you didn't sign up for El Dididi Oculto, please ignore this email.</p>";

		Content emailContent = new Content("text/html", content);
		Mail mail = new Mail(from, subject, to, emailContent);

		SendGrid sg = new SendGrid(sendGridApiKey);
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			sg.api(request);
		} catch (Exception e) {
			throw new RuntimeException("Error sending email: " + e.getMessage());
		}
	}
}
package com.portfolio.serviceimpl;

import com.portfolio.entities.ContactMessage;
import com.portfolio.services.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Value("${portfolio.contact.to-email:admin@portfolio.com}")
    private String toEmail;

    @Override
    public void sendContactEmail(ContactMessage message) {
        if (mailSender == null) {
            return;
        }

        try {
            SimpleMailMessage mailMessage = getSimpleMailMessage(message);

            mailSender.send(mailMessage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private SimpleMailMessage getSimpleMailMessage(ContactMessage message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(message.getEmail());
        mailMessage.setTo(toEmail);
        mailMessage.setSubject("New Contact Message from Portfolio: " + message.getName());
        mailMessage.setText("You received a new message from your portfolio contact form:\n\n" +
                "Sender Name: " + message.getName() + "\n" +
                "Sender Email: " + message.getEmail() + "\n\n" +
                "Message:\n" + message.getMessage() + "\n\n" +
                "Received at: " + message.getCreatedAt());
        return mailMessage;
    }
}

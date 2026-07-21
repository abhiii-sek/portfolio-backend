package com.portfolio.serviceimpl;

import com.portfolio.entities.ContactMessage;
import com.portfolio.entities.EasterMessage;
import com.portfolio.services.EmailService;
import java.util.List;
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

    @Override
    public void sendEasterMessagesEmail(List<EasterMessage> wishes) {
        if (mailSender == null || wishes == null || wishes.isEmpty()) {
            return;
        }

        for (EasterMessage wish : wishes) {
            try {
                SimpleMailMessage mailMessage = getSimpleMailMessage(wish);
                mailSender.send(mailMessage);
            } catch (Exception e) {
                System.out.println("Failed to send Easter Message email: " + e.getMessage());
            }
        }
    }

    private SimpleMailMessage getSimpleMailMessage(EasterMessage wish) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(toEmail);
        mailMessage.setTo(toEmail);
        mailMessage.setSubject("New Easter Message from " + wish.getName());

        String content = "You received a new Easter Message:\n\n" +
                "Sender Name: " + wish.getName() + "\n\n" +
                "Message:\n" + wish.getMessage() + "\n\n" +
                "Sent at: " + wish.getCreatedAt();

        mailMessage.setText(content);
        return mailMessage;
    }
}

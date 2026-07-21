package com.portfolio.services;

import com.portfolio.entities.ContactMessage;
import com.portfolio.entities.EasterMessage;
import java.util.List;

public interface EmailService {
    void sendContactEmail(ContactMessage message);
    void sendEasterMessagesEmail(List<EasterMessage> wishes);
}

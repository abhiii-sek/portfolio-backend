package com.portfolio.services;

import com.portfolio.entities.ContactMessage;

public interface EmailService {
    void sendContactEmail(ContactMessage message);
}

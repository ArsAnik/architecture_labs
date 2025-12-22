package com.booking.listener;

import com.booking.event.AuditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class AuditListener {
    
    private final JavaMailSender mailSender;

    @Autowired
    public AuditListener(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    @EventListener
    public void handleAuditEvent(AuditEvent event) {
        SimpleMailMessage message = new SimpleMailMessage();
        String subject = "";
        String text = "";

        if ("booking".equals(event.getEntityType()) && "create".equals(event.getChangeType())) {
            subject = "Новое бронирование, id = " + event.getEntityId().toString();
            text = "Создано новое бронирование. Данные: " + event.getValues();
            sendEmail(message, subject, text);
        }
        else if("booking".equals(event.getEntityType()) && "update".equals(event.getChangeType()) && "cancelled".equals(event.getValues().get("status"))){
            subject = "Бронирование id " + event.getEntityId().toString() + " отменено";
            text = "Отменено бронирование. Данные: " + event.getValues();
            sendEmail(message, subject, text);
        }
        else if("client".equals(event.getEntityType())){
            subject = "Удалён пользователь, id = " + event.getEntityId().toString();
            text = "Удалён пользователь. Данные: " + event.getValues();
            sendEmail(message, subject, text);
        }   
    }

    private void sendEmail(SimpleMailMessage message, String subject, String text) {
        message.setFrom("booking-system");
        message.setTo("admin@hotel.com");
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}

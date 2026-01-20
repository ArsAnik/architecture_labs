package com.booking.listener;

import com.booking.config.JmsConfig;
import com.booking.entity.AuditChangeDB;
import com.booking.validator.AuditMessageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailJmsListener {

    @Autowired private JavaMailSender mailSender;
    @Autowired private AuditMessageValidator validator;

    @JmsListener(
        destination = JmsConfig.AUDIT_TOPIC,
        subscription = "mail-sub"
    )
    public void onMessage(AuditChangeDB audit) {
        if (!validator.isValid(audit)) return;
        SimpleMailMessage mail = new SimpleMailMessage();
        String subject = "";
        String text = "";

        String entityType = audit.getEntityType();
        Integer entityId = audit.getEntityId();
        String action = audit.getAction();

        if ("booking".equals(entityType) && "create".equals(action)) {
            subject = "Новое бронирование, id = " + entityId;
            text = "Создано новое бронирование #" + entityId;
            sendEmail(mail, subject, text);
        }
        else if ("booking".equals(entityType) && "update".equals(action)) {
            if (audit.getNewValues().toString().contains("cancelled")) {
                subject = "Бронирование id " + entityId + " отменено";
                text = "Отменено бронирование #" + entityId;
                sendEmail(mail, subject, text);
            }
        }
        else if ("client".equals(entityType) && "delete".equals(action)) {
            subject = "Удалён пользователь, id = " + entityId;
            text = "Удалён пользователь #" + entityId;
            sendEmail(mail, subject, text);
        }
    }

    private void sendEmail(SimpleMailMessage message, String subject, String text) {
        message.setFrom("booking-system@hotel.com");
        message.setTo("admin@hotel.com");
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
        System.out.println("Mail sent: " + subject);
    }
}

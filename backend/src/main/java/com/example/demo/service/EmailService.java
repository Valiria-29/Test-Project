package com.example.demo.service;

import com.example.demo.model.Email;
import com.example.demo.model.Subscription;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.mail.MailException; // Для работы с почтовыми исключениями
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailService implements EmailServiceInt{

    private final JavaMailSender mailSender;
    private  final TemplateEngine templateEngine;

    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    @Async
    public void sendSubscriptionNotification(String emailTo, List<Subscription> subscriptions) throws MessagingException {

        if (subscriptions == null || subscriptions.isEmpty()) {
            return;
        }

        // Отправляем отдельное письмо для каждой подписки
        for (Subscription subscription : subscriptions) {

            Context context = new Context();
            // Устанавливаем переменные для шаблона
            context.setVariable("username", emailTo);
            context.setVariable("serviceName", subscription.getServiceName());
            context.setVariable("endDate", formatDate(subscription.getEndDate()));

            String process = templateEngine.process("ThymeLeafMail", context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setTo(emailTo);
            helper.setFrom("valeriapeckina@gmail.com");
            helper.setSubject("Ваша подписка на " + subscription.getServiceName() + " истекает скоро!");
            helper.setText(process, true);

            mailSender.send(message);
        }
    }

    private String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date.format(formatter);
    }
}


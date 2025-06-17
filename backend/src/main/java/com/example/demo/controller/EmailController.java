package com.example.demo.controller;

import com.example.demo.model.Email;
import com.example.demo.model.Subscription;
import com.example.demo.service.EmailService;
import com.example.demo.service.SubscriptionService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/test")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-test")
    public String sendTestEmail() {
        Subscription testSub = new Subscription();
        testSub.setServiceName("Netflix");
        testSub.setEndDate(LocalDate.now().plusDays(3));
        testSub.setUserEmail("valeriapeckina@gmail.com");

        try {
            emailService.sendSubscriptionNotification(
                    "valeriapeckina@gmail.com",
                    List.of(testSub)
            );
            return "Тестовое письмо отправлено";
        } catch (MessagingException e) {
            return "Ошибка: " + e.getMessage();
        }
    }
}

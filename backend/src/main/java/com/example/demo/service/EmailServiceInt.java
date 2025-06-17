package com.example.demo.service;

import com.example.demo.model.Email;
import com.example.demo.model.Subscription;
import jakarta.mail.MessagingException;

import java.util.List;

public interface EmailServiceInt {

    void  sendSubscriptionNotification (String emailTo, List<Subscription> subscriptions) throws MessagingException;
}

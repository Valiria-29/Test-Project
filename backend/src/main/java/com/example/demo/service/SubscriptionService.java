package com.example.demo.service;

import com.example.demo.exception.SubscriptionNotFoundException;
import com.example.demo.model.Subscription;
import com.example.demo.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRep;

    public Subscription save(Subscription subscription) {
        return  subscriptionRep.save(subscription);
    }

    public List<Subscription> getAll() {
        return subscriptionRep.findAll();
    }

    public Optional<Subscription> getOne(Long id) {
        return subscriptionRep.findById(id);
    }


    public boolean existsById(Long id) {
        return subscriptionRep.existsById(id);
    }

    public void deleteById(Long id) {
        subscriptionRep.deleteById(id);
    }

    public List<Subscription> findUpcomingSubscriptions() {
        LocalDate now = LocalDate.now();
        LocalDate in7Days = now.plusDays(7);

        return subscriptionRep.findActiveReminderSubscriptionsBetweenDates(now, in7Days);
    }

    public List<Subscription> findSubscriptionsForUserEmail(String userEmail) {
        return subscriptionRep.findActiveReminderSubscriptionsByUser(userEmail);
    }
}

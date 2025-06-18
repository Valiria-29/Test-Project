package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Subscription;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository <Subscription, Long> {

    @Query("SELECT s FROM Subscription s WHERE s.endDate BETWEEN :startDate AND :endDate AND s.reminderEnabled = true")
    List<Subscription> findActiveReminderSubscriptionsBetweenDates(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT s FROM Subscription s WHERE s.userEmail = :userEmail AND s.reminderEnabled = true")
    List<Subscription> findActiveReminderSubscriptionsByUser(@Param("userEmail") String userEmail);
}

package com.example.demo.service;

import com.example.demo.model.Subscription;
import com.example.demo.service.EmailService;
import com.example.demo.service.SubscriptionService;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j  // Эта аннотация автоматически добавляет логгер
@Service
public class ScheduledEmailService {

    private final SubscriptionService subscriptionService;
    private final EmailService emailService;

    public ScheduledEmailService(
            SubscriptionService subscriptionService,
            EmailService emailService
    ) {
        this.subscriptionService = subscriptionService;
        this.emailService = emailService;
    }

    @Scheduled(cron = "${subscription.reminder.cron}")
    public void sendSubscriptionReminders() {
        log.info("=== Начало выполнения scheduled задачи ===");

        try {
            // 1. Получаем даты
            LocalDate now = LocalDate.now();
            LocalDate in7Days = now.plusDays(7);
            log.info("Поиск подписок, заканчивающихся с {} по {}", now, in7Days);

            // 2. Ищем подписки
            List<Subscription> subscriptions = subscriptionService.findUpcomingSubscriptions();
            log.info("Найдено подписок: {}", subscriptions.size());

            if (subscriptions.isEmpty()) {
                log.info("Нет подписок для уведомления");
                return;
            }

            // 3. Логируем найденные подписки
            subscriptions.forEach(sub ->
                    log.debug("Подписка: {} (ID: {}), окончание: {}, email: {}",
                            sub.getServiceName(),
                            sub.getId(),
                            sub.getEndDate(),
                            sub.getUserEmail())
            );

            // 4. Группируем по email
            Map<String, List<Subscription>> byEmail = subscriptions.stream()
                    .filter(sub -> sub.getUserEmail() != null && !sub.getUserEmail().isEmpty())
                    .collect(Collectors.groupingBy(Subscription::getUserEmail));

            log.info("Уникальных email для отправки: {}", byEmail.size());

            // 5. Отправляем письма
            byEmail.forEach((email, subs) -> {
                try {
                    log.info("Отправка уведомления на: {}", email);
                    emailService.sendSubscriptionNotification(email, subs);
                    log.info("Уведомление успешно отправлено на: {}", email);
                } catch (Exception e) {
                    log.error("Ошибка при отправке на {}: {}", email, e.getMessage());
                }
            });

        } catch (Exception e) {
            log.error("КРИТИЧЕСКАЯ ОШИБКА в scheduled задаче: ", e);
        } finally {
            log.info("=== Завершение scheduled задачи ===");
        }
    }
}
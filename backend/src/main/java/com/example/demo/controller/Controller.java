package com.example.demo.controller;

import com.example.demo.exception.SubscriptionNotFoundException;
import com.example.demo.model.Subscription;
import com.example.demo.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
@CrossOrigin(origins = "http://localhost:4200")
public class Controller {

    @Autowired
    private SubscriptionService subService;

    @GetMapping
    public ResponseEntity<List<Subscription>> getAll() {
        return ResponseEntity.ok(subService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>  getOne(@PathVariable(name = "id") Long id) {
        try {
            Subscription subscription = subService.getOne(id);
            return ResponseEntity.ok(subscription);
        } catch (SubscriptionNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

        @PostMapping
        public ResponseEntity<Subscription> create(@RequestBody Subscription subscription) {
                Subscription savedSubscription = subService.save(subscription);
                return ResponseEntity.ok(savedSubscription);
        }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Subscription> update(@PathVariable(name = "id") Long id, @RequestBody Subscription sub) {
        try {
            sub.setId(id); // Убедимся, что ID совпадает
            Subscription updatedSub = subService.save(sub);
            return ResponseEntity.ok(updatedSub);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubscription(@PathVariable Long id) {
        try {
            subService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<Subscription>> getUpcomingSubscriptions() {
        List<Subscription> subscriptions = subService.findUpcomingSubscriptions();
        return ResponseEntity.ok(subscriptions);
    }

}
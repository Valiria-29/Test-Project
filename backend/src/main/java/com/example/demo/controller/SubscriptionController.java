package com.example.demo.controller;

import com.example.demo.exception.SubscriptionNotFoundException;
import com.example.demo.model.Subscription;
import com.example.demo.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/subscriptions")
@CrossOrigin(origins = "http://localhost:4200")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subService;

    @GetMapping
    public ResponseEntity<List<Subscription>> getAll() {
        return ResponseEntity.ok(subService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable(name = "id") Long id) {
        return subService.getOne(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Subscription> create(@RequestBody Subscription subscription) {
        Subscription savedSubscription = subService.save(subscription);
        return ResponseEntity
                .created(URI.create("/subscriptions/" + savedSubscription.getId()))  // 201 Created
                .body(savedSubscription);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Subscription> update(@PathVariable(name = "id") Long id, @RequestBody Subscription sub) {
        if (!subService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        sub.setId(id);
        return ResponseEntity.ok(subService.save(sub));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubscription(@PathVariable Long id) {
        if (!subService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        subService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<Subscription>> getUpcomingSubscriptions() {
        List<Subscription> subscriptions = subService.findUpcomingSubscriptions();
        return ResponseEntity.ok(subscriptions);
    }
}
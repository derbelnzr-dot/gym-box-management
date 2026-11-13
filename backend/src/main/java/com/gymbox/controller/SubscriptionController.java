package com.gymbox.controller;

import com.gymbox.dto.SubscriptionDTO;
import com.gymbox.service.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionDTO>> getAllSubscriptions() {
        return ResponseEntity.ok(subscriptionService.getAllSubscriptions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionDTO> getSubscriptionById(@PathVariable Long id) {
        return ResponseEntity.ok(subscriptionService.getSubscriptionById(id));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<SubscriptionDTO>> getSubscriptionsByMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(subscriptionService.getSubscriptionsByMemberId(memberId));
    }

    @PostMapping
    public ResponseEntity<SubscriptionDTO> createSubscription(@RequestBody SubscriptionDTO dto) {
        return ResponseEntity.ok(subscriptionService.createSubscription(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }
}

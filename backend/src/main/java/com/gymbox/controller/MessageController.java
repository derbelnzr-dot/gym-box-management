package com.gymbox.controller;

import com.gymbox.dto.ApiResponse;
import com.gymbox.model.ContactMessage;
import com.gymbox.repository.ContactMessageRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/contact")
public class MessageController {

    private final ContactMessageRepository repository;

    public MessageController(ContactMessageRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> sendMessage(@RequestBody Map<String, String> payload) {
        ContactMessage msg = new ContactMessage();
        msg.setName(payload.getOrDefault("name", ""));
        msg.setEmail(payload.getOrDefault("email", ""));
        msg.setMessage(payload.getOrDefault("message", ""));
        msg.setCoachName(payload.get("coachName"));
        repository.save(msg);
        return ResponseEntity.ok(ApiResponse.success("Message envoyé avec succès."));
    }

    @GetMapping
    public ResponseEntity<?> listMessages() {
        return ResponseEntity.ok(repository.findAll());
    }
}
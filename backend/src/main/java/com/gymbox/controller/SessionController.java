package com.gymbox.controller;

import com.gymbox.dto.SessionDTO;
import com.gymbox.service.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public ResponseEntity<List<SessionDTO>> getAllSessions() {
        return ResponseEntity.ok(sessionService.getAllSessions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionDTO> getSessionById(@PathVariable Long id) {
        return ResponseEntity.ok(sessionService.getSessionById(id));
    }

    @PostMapping
    public ResponseEntity<SessionDTO> createSession(@RequestBody SessionDTO dto) {
        return ResponseEntity.ok(sessionService.createSession(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SessionDTO> updateSession(@PathVariable Long id, @RequestBody SessionDTO dto) {
        return ResponseEntity.ok(sessionService.updateSession(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        sessionService.deleteSession(id);
        return ResponseEntity.noContent().build();
    }
}

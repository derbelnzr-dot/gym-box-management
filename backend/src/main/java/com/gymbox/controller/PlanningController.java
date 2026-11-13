package com.gymbox.controller;

import com.gymbox.dto.SessionDTO;
import com.gymbox.entity.Planning;
import com.gymbox.service.PlanningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/planning")
public class PlanningController {

    private final PlanningService planningService;

    public PlanningController(PlanningService planningService) {
        this.planningService = planningService;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<SessionDTO>>> getWeeklyPlanning() {
        return ResponseEntity.ok(planningService.getWeeklyPlanning());
    }

    @PostMapping
    public ResponseEntity<Planning> addToPlanning(@RequestParam String day, @RequestParam Long sessionId) {
        return ResponseEntity.ok(planningService.addSessionToPlanning(day, sessionId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFromPlanning(@PathVariable Long id) {
        planningService.removeFromPlanning(id);
        return ResponseEntity.noContent().build();
    }
}

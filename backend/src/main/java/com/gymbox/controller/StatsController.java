package com.gymbox.controller;

import com.gymbox.dto.StatsDTO;
import com.gymbox.service.StatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<StatsDTO> getDashboardStats() {
        return ResponseEntity.ok(statsService.getDashboardStats());
    }
}

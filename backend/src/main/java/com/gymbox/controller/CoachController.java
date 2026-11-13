package com.gymbox.controller;

import com.gymbox.dto.CoachDTO;
import com.gymbox.service.CoachService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coaches")
public class CoachController {

    private final CoachService coachService;

    public CoachController(CoachService coachService) {
        this.coachService = coachService;
    }

    @GetMapping
    public ResponseEntity<List<CoachDTO>> getCoaches() {
        return ResponseEntity.ok(coachService.getAllCoaches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoachDTO> getCoach(@PathVariable Long id) {
        return ResponseEntity.ok(coachService.getCoachById(id));
    }

    @PostMapping
    public ResponseEntity<CoachDTO> createCoach(@RequestBody CoachDTO dto) {
        return ResponseEntity.ok(coachService.createCoach(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CoachDTO> updateCoach(@PathVariable Long id, @RequestBody CoachDTO dto) {
        return ResponseEntity.ok(coachService.updateCoach(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoach(@PathVariable Long id) {
        coachService.deleteCoach(id);
        return ResponseEntity.noContent().build();
    }
}

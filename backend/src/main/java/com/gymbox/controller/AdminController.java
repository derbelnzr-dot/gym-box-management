package com.gymbox.controller;

import com.gymbox.dto.ApiResponse;
import com.gymbox.entity.User;
import com.gymbox.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/members")
    public ResponseEntity<List<User>> getMembers() {
        return ResponseEntity.ok(adminService.getMembers());
    }

    @GetMapping("/stats")
    public ResponseEntity<List<Map<String, Object>>> getStats() {
        return ResponseEntity.ok(adminService.getStats());
    }

    @PostMapping("/members")
    public ResponseEntity<ApiResponse> addMember(@RequestBody Map<String, String> payload) {
        adminService.addMember(
                payload.get("username"),
                payload.get("email"),
                payload.getOrDefault("password", "password123"),
                payload.get("plan"),
                payload.get("role"));
        return ResponseEntity.ok(ApiResponse.success("Membre ajouté avec succès."));
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<ApiResponse> deleteMember(@PathVariable Long id) {
        adminService.deleteMember(id);
        return ResponseEntity.ok(ApiResponse.success("Membre supprimé avec succès."));
    }
}
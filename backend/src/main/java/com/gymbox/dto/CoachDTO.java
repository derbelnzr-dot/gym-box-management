package com.gymbox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoachDTO {
    private Long id;
    private Long userId;
    private String username;
    private String email;
    private String phone;
    private String photo;
    private String specialty;
    private String bio;
    private Integer yearsExperience;
    private String certifications;
    private Boolean isAvailable;
    private LocalDateTime createdAt;
}

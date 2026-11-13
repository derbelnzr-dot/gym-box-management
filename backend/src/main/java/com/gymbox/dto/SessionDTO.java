package com.gymbox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionDTO {
    private Long id;
    private String title;
    private String description;
    private Long coachId;
    private String coachName;
    private LocalDate sessionDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer maxParticipants;
    private String room;
    private String level;
    private Boolean isActive;
    private LocalDateTime createdAt;
}

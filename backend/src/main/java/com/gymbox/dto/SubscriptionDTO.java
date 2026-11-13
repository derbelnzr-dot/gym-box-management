package com.gymbox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDTO {
    private Long id;
    private Long memberId;
    private String memberName;
    private String planName;
    private BigDecimal price;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer durationMonths;
    private String status;
    private String description;
    private LocalDateTime createdAt;
}

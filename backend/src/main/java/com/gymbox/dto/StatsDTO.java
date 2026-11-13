package com.gymbox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatsDTO {
    private long totalMembers;
    private long activeMembers;
    private long totalCoaches;
    private long totalSessions;
    private long activeSubscriptions;
    private BigDecimal totalRevenue;
    private long expiredSubscriptions;
    private Map<String, Long> membersByMonth;
    private Map<String, BigDecimal> revenueByMonth;
}

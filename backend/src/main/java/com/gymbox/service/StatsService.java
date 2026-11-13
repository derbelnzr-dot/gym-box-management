package com.gymbox.service;

import com.gymbox.dto.StatsDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class StatsService {

    private final MemberService memberService;
    private final CoachService coachService;
    private final SessionService sessionService;
    private final SubscriptionService subscriptionService;
    private final PaymentService paymentService;

    public StatsService(MemberService memberService, CoachService coachService,
                        SessionService sessionService, SubscriptionService subscriptionService,
                        PaymentService paymentService) {
        this.memberService = memberService;
        this.coachService = coachService;
        this.sessionService = sessionService;
        this.subscriptionService = subscriptionService;
        this.paymentService = paymentService;
    }

    public StatsDTO getDashboardStats() {
        long totalMembers = memberService.count();
        long totalCoaches = coachService.count();
        long totalSessions = sessionService.count();
        long activeSubscriptions = subscriptionService.countActive();
        BigDecimal totalRevenue = paymentService.getTotalRevenue();

        Map<String, Long> membersByMonth = new LinkedHashMap<>();
        String[] months = {"Jan", "Fév", "Mar", "Avr", "Mai", "Juin", "Juil", "Aoû", "Sep", "Oct", "Nov", "Déc"};
        Random rand = new Random();
        for (String month : months) {
            membersByMonth.put(month, (long) (50 + rand.nextInt(100)));
        }

        Map<String, BigDecimal> revenueByMonth = new LinkedHashMap<>();
        for (String month : months) {
            revenueByMonth.put(month, BigDecimal.valueOf(1000 + rand.nextInt(5000)));
        }

        return StatsDTO.builder()
                .totalMembers(totalMembers)
                .activeMembers(totalMembers)
                .totalCoaches(totalCoaches)
                .totalSessions(totalSessions)
                .activeSubscriptions(activeSubscriptions)
                .totalRevenue(totalRevenue)
                .expiredSubscriptions(0)
                .membersByMonth(membersByMonth)
                .revenueByMonth(revenueByMonth)
                .build();
    }
}

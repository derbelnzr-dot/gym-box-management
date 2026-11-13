package com.gymbox.service;

import com.gymbox.dto.SubscriptionDTO;
import com.gymbox.entity.Member;
import com.gymbox.entity.Subscription;
import com.gymbox.enums.SubscriptionStatus;
import com.gymbox.exception.ResourceNotFoundException;
import com.gymbox.repository.MemberRepository;
import com.gymbox.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final MemberRepository memberRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, MemberRepository memberRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.memberRepository = memberRepository;
    }

    public List<SubscriptionDTO> getAllSubscriptions() {
        return subscriptionRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public SubscriptionDTO getSubscriptionById(Long id) {
        Subscription sub = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription", id));
        return toDTO(sub);
    }

    public List<SubscriptionDTO> getSubscriptionsByMemberId(Long memberId) {
        return subscriptionRepository.findByMemberId(memberId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public SubscriptionDTO createSubscription(SubscriptionDTO dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member", dto.getMemberId()));

        Subscription sub = Subscription.builder()
                .member(member)
                .planName(dto.getPlanName())
                .price(dto.getPrice())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .durationMonths(dto.getDurationMonths())
                .status(SubscriptionStatus.ACTIVE)
                .description(dto.getDescription())
                .build();

        sub = subscriptionRepository.save(sub);
        return toDTO(sub);
    }

    public void deleteSubscription(Long id) {
        Subscription sub = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription", id));
        sub.setStatus(SubscriptionStatus.CANCELLED);
        subscriptionRepository.save(sub);
    }

    public long countActive() {
        return subscriptionRepository.findAll().stream()
                .filter(s -> s.getStatus() == SubscriptionStatus.ACTIVE)
                .count();
    }

    private SubscriptionDTO toDTO(Subscription sub) {
        return SubscriptionDTO.builder()
                .id(sub.getId())
                .memberId(sub.getMember().getId())
                .memberName(sub.getMember().getUser().getUsername())
                .planName(sub.getPlanName())
                .price(sub.getPrice())
                .startDate(sub.getStartDate())
                .endDate(sub.getEndDate())
                .durationMonths(sub.getDurationMonths())
                .status(sub.getStatus().name())
                .description(sub.getDescription())
                .createdAt(sub.getCreatedAt())
                .build();
    }
}

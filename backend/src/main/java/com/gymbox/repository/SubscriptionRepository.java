package com.gymbox.repository;

import com.gymbox.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByMemberId(Long memberId);
}

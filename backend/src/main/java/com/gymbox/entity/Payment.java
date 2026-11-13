package com.gymbox.entity;

import com.gymbox.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(length = 50)
    private String paymentMethod;

    @Column(length = 100)
    private String transactionId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PaymentStatus status;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}

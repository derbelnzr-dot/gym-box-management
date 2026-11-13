package com.gymbox.service;

import com.gymbox.dto.PaymentDTO;
import com.gymbox.entity.Member;
import com.gymbox.entity.Payment;
import com.gymbox.enums.PaymentStatus;
import com.gymbox.exception.ResourceNotFoundException;
import com.gymbox.repository.MemberRepository;
import com.gymbox.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;

    public PaymentService(PaymentRepository paymentRepository, MemberRepository memberRepository) {
        this.paymentRepository = paymentRepository;
        this.memberRepository = memberRepository;
    }

    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PaymentDTO createPayment(PaymentDTO dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member", dto.getMemberId()));

        Payment payment = Payment.builder()
                .member(member)
                .amount(dto.getAmount())
                .paymentDate(dto.getPaymentDate())
                .paymentMethod(dto.getPaymentMethod())
                .transactionId(dto.getTransactionId())
                .status(PaymentStatus.PAID)
                .notes(dto.getNotes())
                .build();

        payment = paymentRepository.save(payment);
        return toDTO(payment);
    }

    public BigDecimal getTotalRevenue() {
        return paymentRepository.findAll().stream()
                .filter(p -> p.getStatus() == PaymentStatus.PAID)
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private PaymentDTO toDTO(Payment payment) {
        return PaymentDTO.builder()
                .id(payment.getId())
                .memberId(payment.getMember().getId())
                .memberName(payment.getMember().getUser().getUsername())
                .amount(payment.getAmount())
                .paymentDate(payment.getPaymentDate())
                .paymentMethod(payment.getPaymentMethod())
                .transactionId(payment.getTransactionId())
                .status(payment.getStatus().name())
                .notes(payment.getNotes())
                .createdAt(payment.getCreatedAt())
                .build();
    }
}

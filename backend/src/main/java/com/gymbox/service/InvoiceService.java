package com.gymbox.service;

import com.gymbox.dto.InvoiceDTO;
import com.gymbox.entity.Invoice;
import com.gymbox.entity.Member;
import com.gymbox.entity.Subscription;
import com.gymbox.enums.PaymentStatus;
import com.gymbox.exception.ResourceNotFoundException;
import com.gymbox.repository.InvoiceRepository;
import com.gymbox.repository.MemberRepository;
import com.gymbox.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final MemberRepository memberRepository;
    private final SubscriptionRepository subscriptionRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, MemberRepository memberRepository,
                          SubscriptionRepository subscriptionRepository) {
        this.invoiceRepository = invoiceRepository;
        this.memberRepository = memberRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<InvoiceDTO> getAllInvoices() {
        return invoiceRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public InvoiceDTO createInvoice(InvoiceDTO dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member", dto.getMemberId()));

        Subscription subscription = null;
        if (dto.getSubscriptionId() != null) {
            subscription = subscriptionRepository.findById(dto.getSubscriptionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Subscription", dto.getSubscriptionId()));
        }

        Invoice invoice = Invoice.builder()
                .invoiceNumber("INV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .member(member)
                .subscription(subscription)
                .amount(dto.getAmount())
                .tax(dto.getTax())
                .total(dto.getAmount().add(dto.getTax() != null ? dto.getTax() : java.math.BigDecimal.ZERO))
                .issueDate(dto.getIssueDate())
                .dueDate(dto.getDueDate())
                .status(PaymentStatus.PENDING)
                .notes(dto.getNotes())
                .build();

        invoice = invoiceRepository.save(invoice);
        return toDTO(invoice);
    }

    private InvoiceDTO toDTO(Invoice invoice) {
        return InvoiceDTO.builder()
                .id(invoice.getId())
                .invoiceNumber(invoice.getInvoiceNumber())
                .memberId(invoice.getMember().getId())
                .memberName(invoice.getMember().getUser().getUsername())
                .subscriptionId(invoice.getSubscription() != null ? invoice.getSubscription().getId() : null)
                .amount(invoice.getAmount())
                .tax(invoice.getTax())
                .total(invoice.getTotal())
                .issueDate(invoice.getIssueDate())
                .dueDate(invoice.getDueDate())
                .status(invoice.getStatus().name())
                .notes(invoice.getNotes())
                .createdAt(invoice.getCreatedAt())
                .build();
    }
}

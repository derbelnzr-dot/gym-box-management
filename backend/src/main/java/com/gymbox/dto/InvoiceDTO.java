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
public class InvoiceDTO {
    private Long id;
    private String invoiceNumber;
    private Long memberId;
    private String memberName;
    private Long subscriptionId;
    private BigDecimal amount;
    private BigDecimal tax;
    private BigDecimal total;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private String status;
    private String notes;
    private LocalDateTime createdAt;
}

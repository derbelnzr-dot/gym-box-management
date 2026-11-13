package com.gymbox.repository;

import com.gymbox.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByMemberId(Long memberId);
}

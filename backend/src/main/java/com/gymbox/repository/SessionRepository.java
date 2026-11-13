package com.gymbox.repository;

import com.gymbox.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findBySessionDate(LocalDate date);
    List<Session> findByCoachId(Long coachId);
}

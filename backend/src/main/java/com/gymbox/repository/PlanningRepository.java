package com.gymbox.repository;

import com.gymbox.entity.Planning;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlanningRepository extends JpaRepository<Planning, Long> {
    List<Planning> findByDayOfWeek(String dayOfWeek);
    List<Planning> findByIsActiveTrue();
}

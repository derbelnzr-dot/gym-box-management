package com.gymbox.service;

import com.gymbox.dto.SessionDTO;
import com.gymbox.entity.Planning;
import com.gymbox.entity.Session;
import com.gymbox.exception.ResourceNotFoundException;
import com.gymbox.repository.PlanningRepository;
import com.gymbox.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlanningService {

    private final PlanningRepository planningRepository;
    private final SessionRepository sessionRepository;

    public PlanningService(PlanningRepository planningRepository, SessionRepository sessionRepository) {
        this.planningRepository = planningRepository;
        this.sessionRepository = sessionRepository;
    }

    public Map<String, List<SessionDTO>> getWeeklyPlanning() {
        List<Planning> plannings = planningRepository.findByIsActiveTrue();
        Map<String, List<SessionDTO>> weekly = new LinkedHashMap<>();

        String[] days = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
        for (String day : days) {
            weekly.put(day, new ArrayList<>());
        }

        for (Planning p : plannings) {
            if (weekly.containsKey(p.getDayOfWeek())) {
                weekly.get(p.getDayOfWeek()).add(toSessionDTO(p.getSession()));
            }
        }

        return weekly;
    }

    public Planning addSessionToPlanning(String dayOfWeek, Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session", sessionId));

        Planning planning = Planning.builder()
                .dayOfWeek(dayOfWeek)
                .session(session)
                .isActive(true)
                .build();

        return planningRepository.save(planning);
    }

    public void removeFromPlanning(Long planningId) {
        Planning planning = planningRepository.findById(planningId)
                .orElseThrow(() -> new ResourceNotFoundException("Planning", planningId));
        planning.setIsActive(false);
        planningRepository.save(planning);
    }

    private SessionDTO toSessionDTO(Session session) {
        return SessionDTO.builder()
                .id(session.getId())
                .title(session.getTitle())
                .description(session.getDescription())
                .coachId(session.getCoach() != null ? session.getCoach().getId() : null)
                .coachName(session.getCoach() != null ? session.getCoach().getUser().getUsername() : null)
                .sessionDate(session.getSessionDate())
                .startTime(session.getStartTime())
                .endTime(session.getEndTime())
                .maxParticipants(session.getMaxParticipants())
                .room(session.getRoom())
                .level(session.getLevel())
                .isActive(session.getIsActive())
                .createdAt(session.getCreatedAt())
                .build();
    }
}

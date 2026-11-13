package com.gymbox.service;

import com.gymbox.dto.SessionDTO;
import com.gymbox.entity.Coach;
import com.gymbox.entity.Session;
import com.gymbox.exception.ResourceNotFoundException;
import com.gymbox.repository.CoachRepository;
import com.gymbox.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final CoachRepository coachRepository;

    public SessionService(SessionRepository sessionRepository, CoachRepository coachRepository) {
        this.sessionRepository = sessionRepository;
        this.coachRepository = coachRepository;
    }

    public List<SessionDTO> getAllSessions() {
        return sessionRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public SessionDTO getSessionById(Long id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Session", id));
        return toDTO(session);
    }

    public SessionDTO createSession(SessionDTO dto) {
        Coach coach = null;
        if (dto.getCoachId() != null) {
            coach = coachRepository.findById(dto.getCoachId())
                    .orElseThrow(() -> new ResourceNotFoundException("Coach", dto.getCoachId()));
        }

        Session session = Session.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .coach(coach)
                .sessionDate(dto.getSessionDate())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .maxParticipants(dto.getMaxParticipants())
                .room(dto.getRoom())
                .level(dto.getLevel())
                .isActive(true)
                .build();

        session = sessionRepository.save(session);
        return toDTO(session);
    }

    public SessionDTO updateSession(Long id, SessionDTO dto) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Session", id));

        Coach coach = null;
        if (dto.getCoachId() != null) {
            coach = coachRepository.findById(dto.getCoachId())
                    .orElseThrow(() -> new ResourceNotFoundException("Coach", dto.getCoachId()));
        }

        session.setTitle(dto.getTitle());
        session.setDescription(dto.getDescription());
        session.setCoach(coach);
        session.setSessionDate(dto.getSessionDate());
        session.setStartTime(dto.getStartTime());
        session.setEndTime(dto.getEndTime());
        session.setMaxParticipants(dto.getMaxParticipants());
        session.setRoom(dto.getRoom());
        session.setLevel(dto.getLevel());

        session = sessionRepository.save(session);
        return toDTO(session);
    }

    public void deleteSession(Long id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Session", id));
        session.setIsActive(false);
        sessionRepository.save(session);
    }

    public long count() {
        return sessionRepository.count();
    }

    private SessionDTO toDTO(Session session) {
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

package com.gymbox.service;

import com.gymbox.dto.CoachDTO;
import com.gymbox.entity.Coach;
import com.gymbox.entity.User;
import com.gymbox.exception.ResourceNotFoundException;
import com.gymbox.repository.CoachRepository;
import com.gymbox.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoachService {

    private final CoachRepository coachRepository;
    private final UserRepository userRepository;

    public CoachService(CoachRepository coachRepository, UserRepository userRepository) {
        this.coachRepository = coachRepository;
        this.userRepository = userRepository;
    }

    public List<CoachDTO> getAllCoaches() {
        return coachRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CoachDTO getCoachById(Long id) {
        Coach coach = coachRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coach", id));
        return toDTO(coach);
    }

    public CoachDTO createCoach(CoachDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", dto.getUserId()));

        Coach coach = Coach.builder()
                .user(user)
                .specialty(dto.getSpecialty())
                .bio(dto.getBio())
                .yearsExperience(dto.getYearsExperience())
                .certifications(dto.getCertifications())
                .isAvailable(true)
                .build();

        coach = coachRepository.save(coach);
        return toDTO(coach);
    }

    public CoachDTO updateCoach(Long id, CoachDTO dto) {
        Coach coach = coachRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coach", id));

        coach.setSpecialty(dto.getSpecialty());
        coach.setBio(dto.getBio());
        coach.setYearsExperience(dto.getYearsExperience());
        coach.setCertifications(dto.getCertifications());
        coach.setIsAvailable(dto.getIsAvailable());

        coach = coachRepository.save(coach);
        return toDTO(coach);
    }

    public void deleteCoach(Long id) {
        Coach coach = coachRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coach", id));
        coachRepository.delete(coach);
    }

    public long count() {
        return coachRepository.count();
    }

    private CoachDTO toDTO(Coach coach) {
        return CoachDTO.builder()
                .id(coach.getId())
                .userId(coach.getUser().getId())
                .username(coach.getUser().getUsername())
                .email(coach.getUser().getEmail())
                .phone(coach.getUser().getPhone())
                .photo(coach.getUser().getPhoto())
                .specialty(coach.getSpecialty())
                .bio(coach.getBio())
                .yearsExperience(coach.getYearsExperience())
                .certifications(coach.getCertifications())
                .isAvailable(coach.getIsAvailable())
                .createdAt(coach.getCreatedAt())
                .build();
    }
}

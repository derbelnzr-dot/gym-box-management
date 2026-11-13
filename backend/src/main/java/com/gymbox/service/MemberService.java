package com.gymbox.service;

import com.gymbox.dto.MemberDTO;
import com.gymbox.entity.Member;
import com.gymbox.entity.User;
import com.gymbox.exception.ResourceNotFoundException;
import com.gymbox.repository.MemberRepository;
import com.gymbox.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final UserRepository userRepository;

    public MemberService(MemberRepository memberRepository, UserRepository userRepository) {
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
    }

    public List<MemberDTO> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public MemberDTO getMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", id));
        return toDTO(member);
    }

    public MemberDTO createMember(MemberDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", dto.getUserId()));

        Member member = Member.builder()
                .user(user)
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .dateOfBirth(dto.getDateOfBirth())
                .gender(dto.getGender())
                .address(dto.getAddress())
                .city(dto.getCity())
                .emergencyContact(dto.getEmergencyContact())
                .isActive(true)
                .build();

        member = memberRepository.save(member);
        return toDTO(member);
    }

    public MemberDTO updateMember(Long id, MemberDTO dto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", id));

        member.setFirstName(dto.getFirstName());
        member.setLastName(dto.getLastName());
        member.setDateOfBirth(dto.getDateOfBirth());
        member.setGender(dto.getGender());
        member.setAddress(dto.getAddress());
        member.setCity(dto.getCity());
        member.setEmergencyContact(dto.getEmergencyContact());

        member = memberRepository.save(member);
        return toDTO(member);
    }

    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", id));
        member.setIsActive(false);
        memberRepository.save(member);
    }

    public long count() {
        return memberRepository.count();
    }

    private MemberDTO toDTO(Member member) {
        return MemberDTO.builder()
                .id(member.getId())
                .userId(member.getUser().getId())
                .username(member.getUser().getUsername())
                .email(member.getUser().getEmail())
                .phone(member.getUser().getPhone())
                .photo(member.getUser().getPhoto())
                .firstName(member.getFirstName())
                .lastName(member.getLastName())
                .dateOfBirth(member.getDateOfBirth())
                .gender(member.getGender())
                .address(member.getAddress())
                .city(member.getCity())
                .emergencyContact(member.getEmergencyContact())
                .isActive(member.getIsActive())
                .createdAt(member.getCreatedAt())
                .build();
    }
}

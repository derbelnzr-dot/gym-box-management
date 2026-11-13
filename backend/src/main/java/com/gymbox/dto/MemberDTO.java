package com.gymbox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private Long id;
    private Long userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String phone;
    private String address;
    private String city;
    private String emergencyContact;
    private String photo;
    private Boolean isActive;
    private LocalDateTime createdAt;
}

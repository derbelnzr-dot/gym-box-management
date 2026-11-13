package com.gymbox.service;

import com.gymbox.entity.User;
import com.gymbox.enums.UserRole;
import com.gymbox.repository.CoachRepository;
import com.gymbox.repository.OrderRepository;
import com.gymbox.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CoachRepository coachRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(UserRepository userRepository,
                        OrderRepository orderRepository,
                        CoachRepository coachRepository,
                        PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.coachRepository = coachRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<User> getMembers() {
        return userRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getStats() {
        long totalMembers = userRepository.countByRole(UserRole.MEMBER);
        long activeMembers = userRepository.findByRole(UserRole.MEMBER).stream()
                .filter(u -> "Active".equalsIgnoreCase(u.getStatus()))
                .count();
        long pendingOrders = orderRepository.countByStatus("En attente");
        long totalCoaches = coachRepository.count();

        List<Map<String, Object>> stats = new ArrayList<>();

        Map<String, Object> members = new HashMap<>();
        members.put("label", "Membres inscrits");
        members.put("value", totalMembers);
        members.put("color", "#0d6efd");
        members.put("icon", "group");
        stats.add(members);

        Map<String, Object> active = new HashMap<>();
        active.put("label", "Abonnements actifs");
        active.put("value", activeMembers);
        active.put("color", "#198754");
        active.put("icon", "fitness_center");
        stats.add(active);

        Map<String, Object> orders = new HashMap<>();
        orders.put("label", "Commandes en attente");
        orders.put("value", pendingOrders);
        orders.put("color", "#fd7e14");
        orders.put("icon", "shopping_cart");
        stats.add(orders);

        Map<String, Object> coaches = new HashMap<>();
        coaches.put("label", "Coachs disponibles");
        coaches.put("value", totalCoaches);
        coaches.put("color", "#d63384");
        coaches.put("icon", "sports_gymnastics");
        stats.add(coaches);

        return stats;
    }

    @Transactional
    public void deleteMember(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("Membre introuvable.");
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public User addMember(String username, String email, String password, String plan, String role) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Un compte existe déjà avec cet email : " + email);
        }

        User user = User.builder()
                .username(username)
                .email(email.toLowerCase())
                .password(passwordEncoder.encode(password))
                .role("admin".equalsIgnoreCase(role) ? UserRole.ADMIN : UserRole.MEMBER)
                .isActive(true)
                .build();
        if (plan != null && !plan.isBlank()) {
            user.setPlan(plan);
        }
        user.setStatus("Active");
        return userRepository.save(user);
    }
}
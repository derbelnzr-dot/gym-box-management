package com.gymbox.config;

import com.gymbox.entity.Coach;
import com.gymbox.entity.User;
import com.gymbox.enums.UserRole;
import com.gymbox.model.Product;
import com.gymbox.repository.CoachRepository;
import com.gymbox.repository.ProductRepository;
import com.gymbox.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Map;

@Configuration
public class DataSeeder {

    // Images locales servies par le frontend Angular (pas d'URLs externes fragiles).
    private static final Map<String, String> COACH_PHOTOS = Map.of(
            "ahmed@gym.com", "/assets/images/coaches/ahmed.svg",
            "sara@gym.com", "/assets/images/coaches/sara.svg",
            "karim@gym.com", "/assets/images/coaches/karim.svg",
            "anis@gym.com", "/assets/images/coaches/anis.svg"
    );

    private static final Map<String, String> CATEGORY_IMAGES = Map.ofEntries(
            Map.entry("Protéines", "/assets/images/products/protein/whey-protein.svg"),
            Map.entry("Créatine & Acides aminés", "/assets/images/products/creatine/creatine-monohydrate.svg"),
            Map.entry("Pré-entraînement", "/assets/images/products/pre-workout/pre-workout.svg"),
            Map.entry("Vitamines", "/assets/images/products/vitamins/multivitamins.svg"),
            Map.entry("Équipement", "/assets/images/products/equipment/dumbbells-2kg.svg"),
            Map.entry("Accessoires", "/assets/images/products/accessories/shaker.svg"),
            Map.entry("Musculation", "/assets/images/products/equipment/dumbbells-2kg.svg"),
            Map.entry("Accessoires Fitness", "/assets/images/products/accessories/shaker.svg"),
            Map.entry("Yoga & Pilates", "/assets/images/products/equipment/yoga-mat.svg"),
            Map.entry("Sports de combat", "/assets/images/products/combat/boxing-gloves.svg")
    );

    // Images précises pour les produits, y compris les anciennes données déjà en base.
    private static final Map<String, String> PRODUCT_IMAGES = Map.ofEntries(
            Map.entry("Gants de musculation", "/assets/images/products/accessories/gym-gloves.svg"),
            Map.entry("Shaker protéine", "/assets/images/products/accessories/shaker.svg"),
            Map.entry("Ceinture lombaire", "/assets/images/products/accessories/weight-belt.svg"),
            Map.entry("Tapis de fitness", "/assets/images/products/equipment/yoga-mat.svg"),
            Map.entry("Banc de musculation", "/assets/images/products/equipment/bench.svg"),
            Map.entry("Casque de boxe", "/assets/images/products/combat/boxing-helmet.svg"),
            Map.entry("Haltères 2 kg", "/assets/images/products/equipment/dumbbells-2kg.svg"),
            Map.entry("Gants de boxe", "/assets/images/products/combat/boxing-gloves.svg"),
            Map.entry("Haltères 6 kg", "/assets/images/products/equipment/dumbbells-6kg.svg")
    );

    @Bean
    CommandLineRunner seed(UserRepository userRepository,
                           CoachRepository coachRepository,
                           ProductRepository productRepository,
                           PasswordEncoder encoder) {
        return args -> {
            ensureUser(userRepository, encoder, "admin", "admin@gymbox.tn",
                    "admin123", UserRole.ADMIN, "Manager", null, 0);
            ensureUser(userRepository, encoder, "Ahmed", "member@gymbox.tn",
                    "123456", UserRole.MEMBER, "Premium", null, 450);

            // Supprime les coachs orphelins issus de l'ancien modèle (sans utilisateur associé).
            List<Coach> existingCoaches = coachRepository.findAll();
            for (Coach coach : existingCoaches) {
                boolean validUser = coach.getUser() != null
                        && userRepository.existsById(coach.getUser().getId());
                if (!validUser) {
                    coachRepository.delete(coach);
                }
            }

            String[][] coachSeeds = {
                    {"Ahmed", "Musculation", "ahmed@gym.com", "8", "Spécialiste en musculation et prise de masse."},
                    {"Sara", "Yoga & Stretching", "sara@gym.com", "6", "Professeur de yoga certifiée internationalement."},
                    {"Karim", "Cardio Intensif", "karim@gym.com", "10", "Coach cardio et HIIT."},
                    {"Anis", "Fitness Débutant", "anis@gym.com", "5", "Accompagnement des débutants et remise en forme."}
            };
            for (String[] seed : coachSeeds) {
                User coachUser = ensureUser(userRepository, encoder, seed[0] + " Coach", seed[2],
                        "123456", UserRole.COACH, null, COACH_PHOTOS.get(seed[2]), 0);
                if (coachRepository.findByUserId(coachUser.getId()).isEmpty()) {
                    coachRepository.save(Coach.builder()
                            .user(coachUser)
                            .specialty(seed[1])
                            .bio(seed[4])
                            .yearsExperience(Integer.parseInt(seed[3]))
                            .isAvailable(true)
                            .build());
                }
            }

            if (productRepository.count() == 0) {
                productRepository.saveAll(List.of(
                        // ===== Protéines =====
                        new Product("Whey Protein 2kg", 130, "Protéines",
                                "/assets/images/products/protein/whey-protein.svg",
                                "Whey concentrée premium : 24g de protéines par dose pour construire et réparer le muscle.", 45),
                        new Product("Isolate Protein 1kg", 95, "Protéines",
                                "/assets/images/products/protein/isolate-protein.svg",
                                "Isolat microfiltré à 90% de protéines, pauvre en lactose et absorption rapide.", 30),
                        new Product("Mass Gainer 3kg", 85, "Protéines",
                                "/assets/images/products/protein/mass-gainer.svg",
                                "Formule hypercalorique pour une prise de masse sèche et contrôlée.", 25),
                        new Product("Barre Protéinée", 6, "Protéines",
                                "/assets/images/products/protein/protein-bar.svg",
                                "21g de protéines, l'en-cas idéal avant ou après l'effort.", 120),

                        // ===== Créatine & Acides aminés =====
                        new Product("Créatine Monohydrate 300g", 55, "Créatine & Acides aminés",
                                "/assets/images/products/creatine/creatine-monohydrate.svg",
                                "100% créatine monohydrate micronisée pour la force et la puissance.", 40),
                        new Product("BCAA 2:1:1 250g", 49, "Créatine & Acides aminés",
                                "/assets/images/products/amino/bcaa.svg",
                                "Acides aminés branchés au ratio idéal pour la récupération.", 35),
                        new Product("Glutamine 250g", 42, "Créatine & Acides aminés",
                                "/assets/images/products/amino/glutamine.svg",
                                "Accélère la récupération musculaire et renforce le système immunitaire.", 30),

                        // ===== Pré-entraînement =====
                        new Product("Pre-Workout VO2 300g", 68, "Pré-entraînement",
                                "/assets/images/products/pre-workout/pre-workout.svg",
                                "Énergie explosive, focus maximal et endurance améliorée avant l'entraînement.", 28),

                        // ===== Vitamines =====
                        new Product("Multivitamines Sport 60 gélules", 32, "Vitamines",
                                "/assets/images/products/vitamins/multivitamins.svg",
                                "Complexe complet de vitamines et minéraux pour athlètes.", 50),
                        new Product("Vitamine D3 2000 UI", 24, "Vitamines",
                                "/assets/images/products/vitamins/vitamin-d3.svg",
                                "Soutient l'immunité, la santé osseuse et le métabolisme.", 60),
                        new Product("Oméga 3 180 gélules", 36, "Vitamines",
                                "/assets/images/products/vitamins/omega-3.svg",
                                "Acides gras essentiels pour le cœur et les articulations.", 40),

                        // ===== Équipement =====
                        new Product("Haltères 2 kg (paire)", 45, "Équipement",
                                "/assets/images/products/equipment/dumbbells-2kg.svg",
                                "Haltères hexagonales caoutchoutées, prise confortable et sûre.", 20),
                        new Product("Haltères 6 kg (paire)", 85, "Équipement",
                                "/assets/images/products/equipment/dumbbells-6kg.svg",
                                "Idéales pour le renforcement musculaire et le home training.", 15),
                        new Product("Élastiques de résistance (lot de 5)", 39, "Équipement",
                                "/assets/images/products/equipment/resistance-bands.svg",
                                "Bandes de résistance progressives pour un full-body workout.", 40),
                        new Product("Tapis de yoga 6mm", 60, "Équipement",
                                "/assets/images/products/equipment/yoga-mat.svg",
                                "Tapis antidérapant haute densité pour yoga, stretching et sol.", 30),

                        // ===== Accessoires =====
                        new Product("Shaker 700ml", 25, "Accessoires",
                                "/assets/images/products/accessories/shaker.svg",
                                "Shaker avec boule fouet et compartiment de rangement pour vos compléments.", 80),
                        new Product("Gants de musculation", 45, "Accessoires",
                                "/assets/images/products/accessories/gym-gloves.svg",
                                "Gants antidérapants avec renfort de paume et poignets ajustables.", 25),
                        new Product("Ceinture lombaire", 80, "Accessoires",
                                "/assets/images/products/accessories/weight-belt.svg",
                                "Soutien lombaire renforcé pour les charges lourdes.", 15),
                        new Product("Bouteille sportive 1L", 20, "Accessoires",
                                "/assets/images/products/accessories/water-bottle.svg",
                                "Bouteille isotherme avec bouchon flip-top, reste froide plus longtemps.", 60),
                        new Product("Sac de sport GymBox 40L", 70, "Accessoires",
                                "/assets/images/products/accessories/gym-bag.svg",
                                "Grand compartiment, poche chaussures dédiée et poche isotherme.", 18)
                ));
            }

            // ==== Image des coachs : toujours pointer vers les images locales ====
            List<User> coachUsers = userRepository.findByRole(UserRole.COACH);
            coachUsers.forEach(u -> {
                String local = COACH_PHOTOS.get(u.getEmail());
                if (local != null) {
                    u.setPhoto(local);
                }
            });
            userRepository.saveAll(coachUsers);

            // ==== Produits : garantir des visuels locaux cohérents (anciennes données incluses) ====
            List<Product> storedProducts = productRepository.findAll();
            boolean imagesUpdated = false;
            for (Product product : storedProducts) {
                String target = PRODUCT_IMAGES.get(product.getName());
                boolean needsFallback = product.getImage() == null
                        || product.getImage().isBlank()
                        || product.getImage().startsWith("http");
                if (target == null && needsFallback) {
                    target = CATEGORY_IMAGES.getOrDefault(
                            product.getCategory(),
                            "/assets/images/products/accessories/shaker.svg");
                }
                if (target != null && !target.equals(product.getImage())) {
                    product.setImage(target);
                    imagesUpdated = true;
                }
            }
            if (imagesUpdated) {
                productRepository.saveAll(storedProducts);
            }
        };
    }

    private User ensureUser(UserRepository userRepository, PasswordEncoder encoder,
                            String username, String email, String rawPassword,
                            UserRole role, String plan, String photo, int points) {
        return userRepository.findByEmail(email).orElseGet(() -> userRepository.save(
                User.builder()
                        .username(username)
                        .email(email)
                        .password(encoder.encode(rawPassword))
                        .role(role)
                        .plan(plan != null ? plan : "Standard")
                        .points(points)
                        .photo(photo)
                        .isActive(true)
                        .build()));
    }
}
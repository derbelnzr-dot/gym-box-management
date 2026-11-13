package com.gymbox.controller;

import com.gymbox.dto.ApiResponse;
import com.gymbox.model.Order;
import com.gymbox.model.Product;
import com.gymbox.entity.User;
import com.gymbox.repository.OrderRepository;
import com.gymbox.repository.ProductRepository;
import com.gymbox.repository.UserRepository;
import com.gymbox.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public ProductController(ProductRepository productRepository,
                             OrderRepository orderRepository,
                             UserRepository userRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        return productRepository.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/orders")
    public ResponseEntity<ApiResponse> createOrder(@AuthenticationPrincipal UserPrincipal principal,
                                                   @RequestBody Map<String, Object> payload) {
        User user = userRepository.findById(principal.getId()).orElseThrow();

        double total = ((Number) payload.getOrDefault("total", 0)).doubleValue();
        String items = String.valueOf(payload.getOrDefault("items", ""));

        Order order = new Order();
        order.setUser(user);
        order.setTotal(total);
        order.setItems(items);
        orderRepository.save(order);
        return ResponseEntity.ok(ApiResponse.success("Commande enregistrée avec succès."));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getMyOrders(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(orderRepository.findByUserId(principal.getId()));
    }
}
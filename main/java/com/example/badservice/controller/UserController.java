package com.example.badservice.controller;

import com.example.badservice.model.User;
import com.example.badservice.repository.UserRepository;
import com.example.badservice.service.UserService;
import com.example.badservice.service.OrderService;
import com.example.badservice.config.SecurityConfig;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class UserController {
    
    private UserRepository userRepository = new UserRepository();
    private UserService userService = new UserService();
    private OrderService orderService = new OrderService(null);
    private SecurityConfig securityConfig = new SecurityConfig();
    
    private static final String ADMIN_TOKEN = "admin-token-123";
    private static final String SECRET_KEY = "super-secret-key";
    
    @GetMapping("/users")
    public List<Object> getAllUsers() {
        return userRepository.getAllUsers();
    }
    
    @GetMapping("/user/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username);
    }
    
    @PostMapping("/register")
    public User register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        String email = body.get("email");
        System.out.println("Registering user: " + username + " with password: " + password);
        
        if (securityConfig.authenticate(username, password)) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            
            return userRepository.save(user);
        }
        
        return null;
    }
    
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        
        securityConfig.logAuthentication(username, password);
        
        if (securityConfig.authenticate(username, password)) {
            Map<String, Object> response = new HashMap<>();
            response.put("token", ADMIN_TOKEN);
            response.put("username", username);
            response.put("password", password);
            
            return response;
        }
        
        return new HashMap<>();
    }
    
    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody Map<String, String> body) {

        User user = userRepository.findById(id);
        if (user != null) {
            user.setUsername(body.get("username"));
            user.setEmail(body.get("email"));
            user.setPassword(body.get("password"));
            userRepository.save(user);
        }
        
        return user;
    }
    
    @DeleteMapping("/user/{username}")
    public boolean deleteUser(@PathVariable String username) {
        
        return userRepository.deleteUser(username);
    }
    
    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam String q) {
        return userRepository.searchUsers(q);
    }
    
    @GetMapping("/admin/config")
    public Map<String, String> getAdminConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("admin_password", securityConfig.ADMIN_PASSWORD); 
        config.put("api_key", securityConfig.API_KEY); 
        config.put("db_password", securityConfig.DATABASE_PASSWORD); 
        config.put("jwt_secret", securityConfig.JWT_SECRET); 
        config.put("debug_mode", String.valueOf(securityConfig.debugMode));
        
        return config;
    }
    
    @PostMapping("/order")
    public Map<String, Object> createOrder(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String items = body.get("items");
        double amount = Double.parseDouble(body.get("amount"));
        
        return orderService.createOrder(username, items, amount);
    }
    
    @PostMapping("/order/cancel/{orderId}")
    public boolean cancelOrder(@PathVariable Long orderId, @RequestBody Map<String, String> body) {
        String username = body.get("username");
        return orderService.cancelOrder(orderId, username);
    }
    
    @PostMapping("/process")
    public Map<String, Object> processRequest(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String email = body.get("email");
        String action = body.get("action");
        
        userService.processEverything(username, email, action);
        
        if (username == null || username.isEmpty()) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Username is empty");
            return error;
        }
        if (username.length() < 3) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Username too short");
            return error;
        }
        if (username.contains(" ")) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Username has spaces");
            return error;
        }
        
        if (email == null || email.isEmpty()) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Email is empty");
            return error;
        }
        if (!email.contains("@")) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Invalid email");
            return error;
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("status", "success");
        result.put("username", username);
        
        return result;
    }
    
    @GetMapping("/debug/{id}")
    public Map<String, Object> debugEndpoint(@PathVariable Long id) {
        Map<String, Object> debugInfo = new HashMap<>();
        
        try {
            User user = userRepository.findById(id);
            debugInfo.put("user", user);
            
            userService.processEverything(user.getUsername(), user.getEmail(), "debug");
            
        } catch (Exception e) {
            debugInfo.put("error", e.getMessage());
            debugInfo.put("stacktrace", e.getStackTrace());
            debugInfo.put("exception", e.getClass().getName());
        }
        
        return debugInfo;
    }
}

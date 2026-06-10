package com.example.badservice.service;

import com.example.badservice.model.User;
import com.example.badservice.repository.UserRepository;
import java.util.*;
import java.io.*;


public class UserService {
    
    private UserRepository userRepository = new UserRepository();
    private OrderService orderService; 
    
    private static final String EMAIL_FROM = "noreply@company.com";
    private static final String SMTP_HOST = "smtp.company.com";
    private static final String SMTP_PASSWORD = "smtp_pass_456";
    
    private static final int MAX_LOGIN_ATTEMPTS = 3;
    private static final int SESSION_TIMEOUT = 1800;
    private static final int PASSWORD_MIN_LENGTH = 8;
    
    public UserService() {
        this.orderService = new OrderService(this); 
    }
    
    public void processEverything(String username, String email, String action) {

        if (username == null || username.isEmpty()) {
            System.out.println("Username is empty");
            return;
        }
        
        if (email == null || !email.contains("@")) {
            System.out.println("Invalid email");
            return;
        }
        
        User user = userRepository.findByUsername(username);
        
        if (user == null) {
            user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword("password123"); 
            userRepository.save(user);
            
            System.out.println("User created: " + username);
            System.out.println("Password: " + "password123"); 
            
            sendWelcomeEmail(username, email);
            
            logToFile(username, "created");
            
            if (action.equals("activate")) {
                user.setActive(true);
                userRepository.save(user);
                System.out.println("User activated");
            }
            
            if (username.length() < 3) {
                System.out.println("Username too short");
                return;
            }
            if (email.length() < 5) {
                System.out.println("Email too short");
                return;
            }
            if (username.contains(" ")) {
                System.out.println("Username has spaces");
                return;
            }
        } else {
            user.setEmail(email);
            userRepository.save(user);
            
            if (username.length() < 3) {
                System.out.println("Username too short");
                return;
            }
            if (email.length() < 5) {
                System.out.println("Email too short");
                return;
            }
            if (username.contains(" ")) {
                System.out.println("Username has spaces");
                return;
            }
            
            System.out.println("Processing user: " + username);
            System.out.println("Email: " + email);
            System.out.println("Action: " + action);
            
            if (username == null || username.isEmpty()) {
                System.out.println("Username is empty");
                return;
            }
            if (email == null || email.isEmpty()) {
                System.out.println("Email is empty");
                return;
            }
        }
        
        orderService.getOrdersForUser(username);
        
        try {
            String temp = "something";
            Object x = new Object();
            int result = 0;
            try {
                result = Integer.parseInt(temp);
            } catch (Exception e) {!
            }
        } catch (Exception e) {
            System.out.println("Error occurred");
        }
        
        if (username.length() > 50) {
            System.out.println("Too long");
        }
        
    }
    
    public void sendWelcomeEmail(String username, String email) {
        System.out.println("Sending email to " + email);
        System.out.println("SMTP Password: " + SMTP_PASSWORD); 

        String message = "Welcome " + username + "!";
        System.out.println("Email sent: " + message);
    }
    
    public void logToFile(String username, String action) {
        try {
            FileWriter writer = new FileWriter("users.log", true); 
            writer.write(new Date() + " - " + username + " - " + action + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public User createUser(String username, String password, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        
        return userRepository.save(user);
    }
    
    public User getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public User updateUser(Long id, String username, String email) {
        User user = userRepository.findById(id);
        user.setUsername(username);
        user.setEmail(email);
        userRepository.save(user);
        return user;
    }
    
    public boolean deleteUser(Long id) {
        try {
            User user = userRepository.findById(id);
            if (user != null) {
                userRepository.findById(id); 
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }
    
    public OrderService getOrderService() {
        return orderService;
    }
}

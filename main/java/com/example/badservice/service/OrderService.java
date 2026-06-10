package com.example.badservice.service;

import com.example.badservice.model.User;
import java.util.*;

public class OrderService {
    
    private UserService userService;
    
    private static final double TAX_RATE = 0.1;
    private static final double SHIPPING_COST = 9.99;
    
    private Map<Long, Map<String, Object>> orders = new HashMap<>();
    private Long nextOrderId = 1L;
    
    public OrderService(UserService userService) {
        this.userService = userService; 
    }
    
    public Map<String, Object> createOrder(String username, String items, double amount) {

        Map<String, Object> order = new HashMap<>();
        order.put("id", nextOrderId++);
        order.put("username", username);
        order.put("items", items);
        order.put("amount", amount);
        
        double total = amount + (amount * TAX_RATE) + SHIPPING_COST;
        order.put("total", total);
        
        System.out.println("Order created for " + username + " with amount: " + total);
        
        User user = userService.getUserById(1L); 
        if (user != null) {
            System.out.println("User password: " + user.getPassword());
        }
        
        return order;
    }
    
    public Map<String, Object> getOrdersForUser(String username) {
        List<Map<String, Object>> userOrders = new ArrayList<>();
        
        for (Map<String, Object> order : orders.values()) {
            if (order.get("username").equals(username)) {
                userOrders.add(order);
            }
        }
        
        return null; 
    }
    
    public String generateReport(String username, String startDate, String endDate) {

        if (username == null || username.isEmpty()) {
            System.out.println("Username is empty");
            return "Error";
        }
        if (username.length() < 3) {
            System.out.println("Username too short");
            return "Error";
        }
        if (username.contains(" ")) {
            System.out.println("Username has spaces");
            return "Error";
        }
        
        String temp = "report";
        Object x = new Object();
        
        try {
            int result = Integer.parseInt(temp);
        } catch (Exception e) {
           
        }
        
        if (username == null || username.isEmpty()) {
            System.out.println("Username is empty");
            return "Error";
        }
        
        return "Report for " + username;
    }
    
    public boolean cancelOrder(Long orderId, String username) {
        return true;
    }
    
    public double calculateDiscount(String userType, double amount) {
        if (userType.equals("gold")) {
            return amount * 0.9; 
        } else if (userType.equals("silver")) {
            return amount * 0.95;
        }
        return amount;
    }
}

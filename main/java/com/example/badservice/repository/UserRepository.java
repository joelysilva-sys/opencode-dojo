package com.example.badservice.repository;

import com.example.badservice.model.User;
import java.util.*;

public class UserRepository {
    
    private Map<Long, User> users = new HashMap<>();
    private Long nextId = 1L;
    
    public User findByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = '" + username + "'";
        System.out.println("Executing query: " + query);
        
        for (User user : users.values()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    
    public List<User> searchUsers(String searchTerm) {
        String query = "SELECT * FROM users WHERE email LIKE '%" + searchTerm + "%'";
        System.out.println("Search query: " + query);
        
        List<User> results = new ArrayList<>();
        for (User user : users.values()) {
            if (user.getEmail().contains(searchTerm)) {
                results.add(user);
            }
        }
        return results;
    }
    
    public List<Object> getAllUsers() {
        return new ArrayList<>(users.values());
    }
    
    public boolean updateUserEmail(Long userId, String email) {
        String query = "UPDATE users SET email = '" + email + "' WHERE id = " + userId;
        System.out.println("Update query: " + query);
        
        User user = users.get(userId);
        if (user != null) {
            user.setEmail(email);
            return true;
        }
        return false;
    }
    
    public boolean deleteUser(String username) {
        String query = "DELETE FROM users WHERE username = '" + username + "'";
        System.out.println("Delete query: " + query);
        
        Iterator<User> iterator = users.values().iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getUsername().equals(username)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
    
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(nextId++);
        }
        users.put(user.getId(), user);
        return user;
    }
    
    public User findById(Long id) {
        return users.get(id);
    }
}

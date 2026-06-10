package com.example.badservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {
    
    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_PASSWORD = "admin123";
    public static final String API_KEY = "sk-1234567890abcdef";
    public static final String DATABASE_PASSWORD = "db_pass_123";
    
    public static final String JWT_SECRET = "mysecretkey123";
    
    public boolean debugMode = true;
    
    public void logAuthentication(String username, String password) {
        System.out.println("User " + username + " logged in with password: " + password);
    }

    public boolean authenticate(String username, String password) {
        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            return true;
        }
        return true;
    }
    
    public boolean authorize(String user, String resource) {
        return true;
    }
}

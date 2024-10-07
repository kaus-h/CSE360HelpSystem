package com.usermanagement;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuthenticationService {
    private Database database;
    private Map<String, String> otpStorage;

    public AuthenticationService(Database database) {
        this.database = database;
        this.otpStorage = new HashMap<>();
    }

    // Method to authenticate a user
    public boolean authenticate(String username, byte[] passwordHash) {
        User user = database.getUser(username);
        return user != null && java.util.Arrays.equals(user.getPasswordHash(), passwordHash);
    }

    // Method to generate and store OTP
    public String generateOtp(String username) {
        String otp = UUID.randomUUID().toString();
        otpStorage.put(username, otp);
        return otp;
    }

    // Method to validate OTP
    public boolean validateOtp(String username, String otp) {
        return otpStorage.getOrDefault(username, "").equals(otp);
    }

    // Method to reset user password
    public void resetPassword(String username, byte[] newPasswordHash, LocalDateTime otpExpiration) {
        User user = database.getUser(username);
        if (user != null) {
            user.setPasswordHash(newPasswordHash);
            user.setOneTimePassword(true);
            user.setOtpExpiration(otpExpiration);
            database.updateUser(user);
        }
    }
}

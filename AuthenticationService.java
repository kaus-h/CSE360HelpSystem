package com.usermanagement;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuthenticationService {
    private Database database;
    private Map<String, String> otpStorage;
    private Map<String, String> invitationStorage;

    public AuthenticationService(Database database) {
        this.database = database;
        this.otpStorage = new HashMap<>();
        this.invitationStorage = new HashMap<>();
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

    // Method to invite a new user
    public String inviteUser(String username, String role) {
        String invitationCode = UUID.randomUUID().toString();
        invitationStorage.put(invitationCode, username + ":" + role);
        return invitationCode;
    }

    // Method to accept an invitation
    public boolean acceptInvitation(String username, String invitationCode, byte[] passwordHash) {
        if (invitationStorage.containsKey(invitationCode)) {
            String[] details = invitationStorage.get(invitationCode).split(":");
            String invitedUsername = details[0];
            String role = details[1];
            if (invitedUsername.equals(username)) {
                User user = new User(username, passwordHash, null, null, null, null, null, List.of(role), false, null, null);
                database.addUser(user);
                invitationStorage.remove(invitationCode);
                return true;
            }
        }
        return false;
    }

    // Method to delete a user
    public void deleteUser(String username) {
        database.deleteUser(username);
    }

    // Method to list all users
    public void listUsers() {
        database.listUsers().forEach((username, user) -> System.out.println(user));
    }

    // Method to add a role to a user
    public void addRoleToUser(String username, String role) {
        User user = database.getUser(username);
        if (user != null && !user.getRoles().contains(role)) {
            user.getRoles().add(role);
            database.updateUser(user);
        }
    }

    // Method to remove a role from a user
    public void removeRoleFromUser(String username, String role) {
        User user = database.getUser(username);
        if (user != null) {
            user.getRoles().remove(role);
            database.updateUser(user);
        }
    }
}

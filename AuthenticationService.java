package com.usermanagement;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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

    public Database getDatabase() {
        return database;
    }

    public boolean authenticate(String username, byte[] passwordHash) {
        User user = database.getUser(username);
        return user != null && java.util.Arrays.equals(user.getPasswordHash(), passwordHash);
    }

    public String generateOtp(String username) {
        String otp = UUID.randomUUID().toString();
        otpStorage.put(username, otp);
        return otp;
    }

    public boolean validateOtp(String username, String otp) {
        return otpStorage.getOrDefault(username, "").equals(otp);
    }

    public void resetPassword(String username, byte[] newPasswordHash, LocalDateTime otpExpiration) {
        User user = database.getUser(username);
        if (user != null) {
            user.setPasswordHash(newPasswordHash);
            user.setOneTimePassword(true);
            user.setOtpExpiration(otpExpiration);
            database.updateUser(user);
        }
    }

    public String inviteUser(String username, String role) {
        String invitationCode = UUID.randomUUID().toString();
        invitationStorage.put(invitationCode, username + ":" + role);
        return invitationCode;
    }

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

    public boolean validateInvitationCode(String invitationCode) {
        return invitationStorage.containsKey(invitationCode);
    }

    public void deleteUser(String username) {
        database.deleteUser(username);
    }

    public void listUsers() {
        database.listUsers().forEach((username, user) -> System.out.println(user));
    }

    public void addRoleToUser(String username, String role) {
        User user = database.getUser(username);
        if (user != null && !user.getRoles().contains(role)) {
            user.getRoles().add(role);
            database.updateUser(user);
        }
    }

    public void removeRoleFromUser(String username, String role) {
        User user = database.getUser(username);
        if (user != null) {
            user.getRoles().remove(role);
            database.updateUser(user);
        }
    }
}

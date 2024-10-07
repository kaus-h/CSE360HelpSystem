package com.usermanagement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private List<User> users;

    public UserService() {
        this.users = new ArrayList<>();
    }

    // Method to add a new user
    public void addUser(User user) {
        users.add(user);
    }

    // Method to delete a user by username
    public void deleteUser(String username) {
        users.removeIf(user -> user.getUsername().equals(username));
    }

    // Method to get a user by username
    public User getUser(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    // Method to update user information
    public void updateUser(User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(updatedUser.getUsername())) {
                users.set(i, updatedUser);
                break;
            }
        }
    }

    // Method to list all users
    public List<User> listUsers() {
        return new ArrayList<>(users);
    }

    // Method to reset user password
    public void resetUserPassword(String username, byte[] newPasswordHash, LocalDateTime otpExpiration) {
        User user = getUser(username);
        if (user != null) {
            user.setPasswordHash(newPasswordHash);
            user.setOneTimePassword(true);
            user.setOtpExpiration(otpExpiration);
        }
    }

    // Method to add a role to a user
    public void addRoleToUser(String username, String role) {
        User user = getUser(username);
        if (user != null && !user.getRoles().contains(role)) {
            user.getRoles().add(role);
        }
    }

    // Method to remove a role from a user
    public void removeRoleFromUser(String username, String role) {
        User user = getUser(username);
        if (user != null) {
            user.getRoles().remove(role);
        }
    }
}

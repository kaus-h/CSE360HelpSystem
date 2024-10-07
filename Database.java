package com.usermanagement;

import java.util.HashMap;
import java.util.Map;

public class Database {
    private Map<String, User> userTable;

    public Database() {
        this.userTable = new HashMap<>();
    }

    // Method to add a new user to the database
    public void addUser(User user) {
        userTable.put(user.getUsername(), user);
    }

    // Method to delete a user from the database by username
    public void deleteUser(String username) {
        userTable.remove(username);
    }

    // Method to get a user from the database by username
    public User getUser(String username) {
        return userTable.get(username);
    }

    // Method to update a user in the database
    public void updateUser(User user) {
        userTable.put(user.getUsername(), user);
    }

    // Method to list all users in the database
    public Map<String, User> listUsers() {
        return new HashMap<>(userTable);
    }
}

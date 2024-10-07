package com.usermanagement;

public class Role {
    public static final String ADMIN = "Admin";
    public static final String STUDENT = "Student";
    public static final String INSTRUCTOR = "Instructor";

    private String roleName;

    // Constructors
    public Role() {}

    public Role(String roleName) {
        this.roleName = roleName;
    }

    // Getter and setter
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    // Override toString() for better readability
    @Override
    public String toString() {
        return "Role{" + "roleName='" + roleName + '\'' + '}';
    }
}

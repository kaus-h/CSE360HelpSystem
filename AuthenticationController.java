package com.usermanagement;

import java.time.LocalDateTime;

public class AuthenticationController {
    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    // Method to handle login requests
    public boolean login(String username, byte[] passwordHash) {
        return authenticationService.authenticate(username, passwordHash);
    }

    // Method to handle OTP validation
    public boolean validateOtp(String username, String otp) {
        return authenticationService.validateOtp(username, otp);
    }

    // Method to handle password reset requests
    public void resetPassword(String username, byte[] newPasswordHash) {
        authenticationService.resetPassword(username, newPasswordHash, LocalDateTime.now().plusMinutes(10));
    }

    // Method to handle inviting a new user
    public String inviteUser(String username, String role) {
        return authenticationService.inviteUser(username, role);
    }

    // Method to handle accepting an invitation
    public boolean acceptInvitation(String username, String invitationCode, byte[] passwordHash) {
        return authenticationService.acceptInvitation(username, invitationCode, passwordHash);
    }
}

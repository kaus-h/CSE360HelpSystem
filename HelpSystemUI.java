package com.usermanagement;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HelpSystemUI extends Application {

    private AuthenticationService authenticationService;
    private UserService userService;

    public HelpSystemUI() {
        Database database = new Database();
        this.authenticationService = new AuthenticationService(database);
        this.userService = new UserService();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Help System User Information");

        // GridPane for layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        // Email Address
        Label emailLabel = new Label("Email Address:");
        grid.add(emailLabel, 0, 0);
        TextField emailField = new TextField();
        grid.add(emailField, 1, 0);

        // Username
        Label usernameLabel = new Label("Username:");
        grid.add(usernameLabel, 0, 1);
        TextField usernameField = new TextField();
        grid.add(usernameField, 1, 1);

        // Password
        Label passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, 2);
        PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 2);

        // One-time Password Checkbox
        CheckBox otpCheckBox = new CheckBox("One-time Password");
        grid.add(otpCheckBox, 1, 3);

        // Password Expiry Date
        Label expiryDateLabel = new Label("Expiry Date:");
        grid.add(expiryDateLabel, 0, 4);
        DatePicker expiryDatePicker = new DatePicker();
        grid.add(expiryDatePicker, 1, 4);

        // Name Fields (First, Middle, Last, Preferred)
        Label nameLabel = new Label("Name:");
        grid.add(nameLabel, 0, 5);
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");
        TextField middleNameField = new TextField();
        middleNameField.setPromptText("Middle Name");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");
        TextField preferredNameField = new TextField();
        preferredNameField.setPromptText("Preferred Name");

        // Horizontal box for name fields
        grid.add(firstNameField, 1, 5);
        grid.add(middleNameField, 2, 5);
        grid.add(lastNameField, 3, 5);
        grid.add(preferredNameField, 4, 5);

        // Skill Levels (Beginner, Intermediate, Advanced, Expert)
        Label skillsLabel = new Label("Skill Levels:");
        grid.add(skillsLabel, 0, 6);
        String[] topics = {"Java", "Eclipse", "JavaFX", "GitHub"};
        List<ComboBox<String>> skillLevelComboBoxes = new ArrayList<>();
        for (int i = 0; i < topics.length; i++) {
            Label topicLabel = new Label(topics[i]);
            grid.add(topicLabel, 0, 7 + i);
            ComboBox<String> skillLevelCombo = new ComboBox<>();
            skillLevelCombo.getItems().addAll("Beginner", "Intermediate", "Advanced", "Expert");
            skillLevelCombo.setValue("Intermediate"); // Default
            skillLevelComboBoxes.add(skillLevelCombo);
            grid.add(skillLevelCombo, 1, 7 + i);
        }

        // Submit Button
        Button submitButton = new Button("Submit");
        grid.add(submitButton, 1, 11);

        // Event handling for submit
        submitButton.setOnAction(e -> {
            // Handle submit actions
            handleUserSubmission(emailField.getText(), usernameField.getText(), passwordField.getText(), 
                                 otpCheckBox.isSelected(), expiryDatePicker.getValue(),
                                 firstNameField.getText(), middleNameField.getText(), 
                                 lastNameField.getText(), preferredNameField.getText(), 
                                 skillLevelComboBoxes);
        });

        // Scene setup
        Scene scene = new Scene(grid, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleUserSubmission(String email, String username, String password, boolean otp, LocalDate expiryDate, 
                                      String firstName, String middleName, String lastName, String preferredName,
                                      List<ComboBox<String>> skillLevelComboBoxes) {
        // Hash the password using SHA-256
        byte[] passwordHash = hashPassword(password);

        // Collect skill levels
        List<String> topics = new ArrayList<>();
        for (ComboBox<String> comboBox : skillLevelComboBoxes) {
            topics.add(comboBox.getValue());
        }

        // Create a User object
        User user = new User(username, passwordHash, email, firstName, middleName, lastName, preferredName, new ArrayList<>(), otp, expiryDate != null ? expiryDate.atStartOfDay() : null, topics);

        // Add user to the user service
        userService.addUser(user);

        // Print user info to console
        System.out.println("User info submitted");
        System.out.println(user);
    }

    private byte[] hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

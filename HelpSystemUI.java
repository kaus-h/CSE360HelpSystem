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
import java.util.List;

public class HelpSystemUI extends Application {

    private AuthenticationService authenticationService;
    private UserService userService;
    private TextField usernameInput; // Declare as instance variable
    private PasswordField passwordInput; // Declare as instance variable

    public HelpSystemUI() {
        Database database = new Database();
        this.authenticationService = new AuthenticationService(database);
        this.userService = new UserService();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Help System");

        // Check if the database is empty
        if (userService.listUsers().isEmpty()) {
            // First user setup as Admin
            GridPane firstUserGrid = createFirstUserGrid();
            Scene firstUserScene = new Scene(firstUserGrid, 800, 400);
            primaryStage.setScene(firstUserScene);
        } else {
            // Regular login page
            GridPane loginGrid = createLoginGrid();
            Scene loginScene = new Scene(loginGrid, 800, 400);
            primaryStage.setScene(loginScene);
        }
        primaryStage.show();
    }

    private GridPane createFirstUserGrid() {
        GridPane grid = createLoginGrid();
        Button setupButton = new Button("Setup Admin Account");
        GridPane.setConstraints(setupButton, 1, 3);
        setupButton.setOnAction(e -> {
            String username = usernameInput.getText();
            byte[] passwordHash = hashPassword(passwordInput.getText());
            User adminUser = new User(username, passwordHash, null, null, null, null, null, List.of(Role.ADMIN), false, null, null);
            userService.addUser(adminUser);
            authenticationService.getDatabase().addUser(adminUser); // Store admin user in the database
            showAlert("Success", "Admin account created. Please log in.");
            start(new Stage()); // Restart to show login page
        });
        grid.getChildren().add(setupButton);
        return grid;
    }

    private GridPane createLoginGrid() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Username
        Label usernameLabel = new Label("Username:");
        GridPane.setConstraints(usernameLabel, 0, 0);
        usernameInput = new TextField();
        GridPane.setConstraints(usernameInput, 1, 0);

        // Password
        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);
        passwordInput = new PasswordField();
        GridPane.setConstraints(passwordInput, 1, 1);

        // Login Button
        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 2);
        loginButton.setOnAction(e -> {
            String username = usernameInput.getText();
            byte[] passwordHash = hashPassword(passwordInput.getText());
            if (authenticationService.authenticate(username, passwordHash)) {
                User user = userService.getUser(username);
                if (user.getRoles().size() > 1) {
                    displayRoleSelection(user);
                } else if (user.getEmail() == null) {
                    displayAccountSetup(user);
                } else {
                    displayHomePage(user.getRoles().get(0));
                }
            } else {
                showAlert("Invalid credentials", "Username or password is incorrect.");
            }
        });

        grid.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, loginButton);
        return grid;
    }

    private void displayAccountSetup(User user) {
        Stage stage = new Stage();
        stage.setTitle("Finish Setting Up Your Account");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        GridPane.setConstraints(emailField, 0, 0);

        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");
        GridPane.setConstraints(firstNameField, 0, 1);

        TextField middleNameField = new TextField();
        middleNameField.setPromptText("Middle Name");
        GridPane.setConstraints(middleNameField, 0, 2);

        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");
        GridPane.setConstraints(lastNameField, 0, 3);

        TextField preferredNameField = new TextField();
        preferredNameField.setPromptText("Preferred Name");
        GridPane.setConstraints(preferredNameField, 0, 4);

        Button completeSetupButton = new Button("Complete Setup");
        GridPane.setConstraints(completeSetupButton, 0, 5);
        completeSetupButton.setOnAction(e -> {
            user.setEmail(emailField.getText());
            user.setFirstName(firstNameField.getText());
            user.setMiddleName(middleNameField.getText());
            user.setLastName(lastNameField.getText());
            user.setPreferredName(preferredNameField.getText());
            userService.updateUser(user);
            showAlert("Success", "Account setup completed.");
            stage.close();
            start(new Stage()); // Restart to show login page
        });

        grid.getChildren().addAll(emailField, firstNameField, middleNameField, lastNameField, preferredNameField, completeSetupButton);
        Scene scene = new Scene(grid, 400, 300);
        stage.setScene(scene);
        stage.show();
    }

    private void displayRoleSelection(User user) {
        Stage stage = new Stage();
        stage.setTitle("Select Role");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label roleLabel = new Label("Select role for this session:");
        GridPane.setConstraints(roleLabel, 0, 0);
        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll(user.getRoles());
        GridPane.setConstraints(roleComboBox, 1, 0);

        Button selectButton = new Button("Select");
        GridPane.setConstraints(selectButton, 1, 1);
        selectButton.setOnAction(e -> {
            String selectedRole = roleComboBox.getValue();
            displayHomePage(selectedRole);
            stage.close();
        });

        grid.getChildren().addAll(roleLabel, roleComboBox, selectButton);
        Scene scene = new Scene(grid, 400, 200);
        stage.setScene(scene);
        stage.show();
    }

    private void displayHomePage(String role) {
        Stage stage = new Stage();
        stage.setTitle(role + " Home Page");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label welcomeLabel = new Label("Welcome to the " + role + " home page!");
        GridPane.setConstraints(welcomeLabel, 0, 0);

        Button logoutButton = new Button("Logout");
        GridPane.setConstraints(logoutButton, 0, 1);
        logoutButton.setOnAction(e -> {
            stage.close();
            start(new Stage());
        });

        grid.getChildren().addAll(welcomeLabel, logoutButton);
        Scene scene = new Scene(grid, 400, 200);
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

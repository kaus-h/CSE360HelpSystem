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
        primaryStage.setTitle("Help System");

        // Create login page
        GridPane loginGrid = createLoginGrid();
        Scene loginScene = new Scene(loginGrid, 800, 400);
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private GridPane createLoginGrid() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Username
        Label usernameLabel = new Label("Username:");
        GridPane.setConstraints(usernameLabel, 0, 0);
        TextField usernameInput = new TextField();
        GridPane.setConstraints(usernameInput, 1, 0);

        // Password
        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);
        PasswordField passwordInput = new PasswordField();
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

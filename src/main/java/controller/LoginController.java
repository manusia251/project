package controller;

import dao.Database;
import dao.UserDao;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField visiblePasswordField;
    @FXML private CheckBox showPassword;
    @FXML private Label messageLabel;
    private final UserDao userDao = new UserDao();

    @FXML private void initialize() {
        try { Database.initialize(); }
        catch (SQLException e) { alert("Database", "Gagal membuka database: " + e.getMessage()); }
        passwordField.textProperty().bindBidirectional(visiblePasswordField.textProperty());
        showPassword.selectedProperty().addListener((o, oldValue, visible) -> {
            passwordField.setVisible(!visible); passwordField.setManaged(!visible);
            visiblePasswordField.setVisible(visible); visiblePasswordField.setManaged(visible);
        });
        usernameField.requestFocus();
    }

    @FXML private void login() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        if (username.isEmpty() || password.isEmpty()) { messageLabel.setText("Username dan password harus diisi."); return; }
        try {
            if (userDao.login(username, password)) FxWorkspace.show((Stage) usernameField.getScene().getWindow(), "Dashboard");
            else { messageLabel.setText("Username atau password salah."); passwordField.clear(); }
        } catch (SQLException e) { alert("Gagal login", e.getMessage()); }
    }

    private static void alert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR, text); alert.setTitle(title); alert.setHeaderText(null); alert.showAndWait();
    }
}

package controller;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginApp extends Application {
    @Override public void start(Stage stage) { showLogin(stage); }

    public static void showLogin(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(LoginApp.class.getResource("/fxml/login.fxml"));
            Scene scene = new Scene(loader.load(), 870, 530, Color.web("#1c2b25"));
            scene.getStylesheets().add(LoginApp.class.getResource("/styles/login.css").toExternalForm());
            stage.setTitle("Aplikasi Pembayaran SPP");
            stage.setMinWidth(650); stage.setMinHeight(460); stage.setScene(scene); stage.show();
        } catch (IOException e) { throw new IllegalStateException("Gagal memuat halaman login.", e); }
    }

    public static void main(String[] args) { launch(args); }
}

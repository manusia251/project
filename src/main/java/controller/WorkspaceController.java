package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class WorkspaceController {
    @FXML private StackPane contentHost;
    private Stage stage;

    public void setStage(Stage stage) { this.stage = stage; }
    public void show(String page) { contentHost.getChildren().setAll(FxWorkspace.createPage(stage, page)); }
    @FXML private void openDashboard() { show("Dashboard"); }
    @FXML private void openStudents() { show("Data Siswa"); }
    @FXML private void openPayments() { show("Tagihan"); }
    @FXML private void openHistory() { show("Riwayat"); }
    @FXML private void openReports() { show("Laporan"); }
    @FXML private void logout() { LoginApp.showLogin(stage); }
}

package controller;

import dao.Database;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.control.TableView;

public class DashboardController {
    @FXML private HBox metricsRow;
    @FXML private TableView<java.util.Map<String,Object>> paymentsTable;

    @FXML private void initialize() {
        try (Connection c = Database.getConnection()) {
            long students = FxWorkspace.scalar(c, "SELECT COUNT(*) FROM siswa");
            long total = FxWorkspace.scalar(c, "SELECT COUNT(*) FROM Pembayaran");
            long paid = FxWorkspace.scalar(c, "SELECT COUNT(*) FROM Pembayaran WHERE status='lunas'");
            long outstanding = FxWorkspace.scalar(c, "SELECT COALESCE(SUM(nominal),0) FROM Pembayaran WHERE status='menunggak'");
            metricsRow.getChildren().setAll(FxWorkspace.metric("Jumlah siswa", String.valueOf(students)),
                    FxWorkspace.metric("Tagihan lunas", paid + " / " + total),
                    FxWorkspace.metric("Tunggakan", FxWorkspace.money(outstanding)));
            FxWorkspace.populateTable(paymentsTable, c, """
                    SELECT p.id_pembayaran AS "No. Kwitansi", COALESCE(p.tgl_bayar,'Belum dibayar') AS Tanggal,
                    p.id_siswa AS NIS, COALESCE(s.nama,'-') AS "Nama Siswa", s.kelas AS Kelas,
                    p.bulan AS Bulan, p.nominal AS Jumlah, p.status AS Status
                    FROM Pembayaran p LEFT JOIN siswa s ON s.id_siswa=p.id_siswa
                    ORDER BY p.id_pembayaran DESC LIMIT 10
                    """);
        } catch (SQLException e) { FxWorkspace.showError(e); }
    }
}

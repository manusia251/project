package controller;

import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PaymentsController {
    @FXML private TextField searchField;
    @FXML private ComboBox<String> filterBox;
    @FXML private TableView<Map<String,Object>> table;
    @FXML private void initialize(){filterBox.getItems().setAll("Semua","Tagihan","Pembayaran","A-Z");filterBox.setValue("Semua");searchField.textProperty().addListener((o,a,b)->reload());filterBox.valueProperty().addListener((o,a,b)->reload());reload();}
    private void reload(){String q="""
        SELECT p.id_pembayaran AS "No. Kwitansi",p.tgl_bayar AS Tanggal,p.id_siswa AS NIS,
        COALESCE(s.nama,'-') AS "Nama Siswa",s.kelas AS Kelas,p.bulan AS Bulan,
        p.tahun_ajaran AS "Tahun Ajaran",p.nominal AS Jumlah,p.status AS Status
        FROM Pembayaran p LEFT JOIN siswa s ON s.id_siswa=p.id_siswa
        WHERE (?='' OR s.nama LIKE ? OR CAST(p.id_siswa AS TEXT) LIKE ? OR p.bulan LIKE ?)
        AND (?='Semua' OR (?='Tagihan' AND p.status='menunggak') OR (?='Pembayaran' AND p.status='lunas'))
        ORDER BY CASE WHEN ?='A-Z' THEN s.nama END COLLATE NOCASE,p.id_pembayaran DESC
        """;String text=searchField.getText();FxWorkspace.load(table,q,text,"%"+text+"%","%"+text+"%","%"+text+"%",filterBox.getValue(),filterBox.getValue(),filterBox.getValue(),filterBox.getValue());}
    @FXML private void createBills(){FxWorkspace.createYearBills(owner(),this::reload);}
    @FXML private void receive(){FxWorkspace.receive(owner(),table,this::reload);}
    @FXML private void receipt(){FxWorkspace.receipt(table);}
    private Stage owner(){return (Stage)table.getScene().getWindow();}
}

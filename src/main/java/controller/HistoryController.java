package controller;

import dao.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HistoryController {
    @FXML private TextField yearField, searchField;
    @FXML private TableView<Map<String,Object>> table;
    @FXML private void initialize(){yearField.textProperty().addListener((o,a,b)->reload());searchField.textProperty().addListener((o,a,b)->reload());reload();}
    private void reload(){String q="""
        SELECT p.id_pembayaran AS "No. Kwitansi",p.tgl_bayar AS Tanggal,p.id_siswa AS NIS,
        COALESCE(s.nama,'-') AS "Nama Siswa",s.kelas AS Kelas,p.bulan AS Bulan,
        p.tahun_ajaran AS "Tahun Ajaran",p.nominal AS Jumlah
        FROM Pembayaran p LEFT JOIN siswa s ON s.id_siswa=p.id_siswa
        WHERE p.status='lunas' AND p.tahun_ajaran=? AND (?='' OR s.nama LIKE ? OR CAST(p.id_siswa AS TEXT) LIKE ? OR p.bulan LIKE ?)
        ORDER BY p.tgl_bayar DESC,p.id_pembayaran DESC
        """;String text=searchField.getText();FxWorkspace.load(table,q,yearField.getText(),text,"%"+text+"%","%"+text+"%","%"+text+"%");}
    @FXML private void receipt(){FxWorkspace.receipt(table);}
    @FXML private void cancel(){var row=table.getSelectionModel().getSelectedItem();if(row==null){FxWorkspace.warnUser("Pilih transaksi terlebih dahulu.");return;}if(!FxWorkspace.confirm(owner(),"Batalkan kwitansi #"+row.get("No. Kwitansi")+"? Pembayaran akan kembali menjadi tagihan."))return;execute("UPDATE Pembayaran SET status='menunggak',tgl_bayar=NULL WHERE id_pembayaran=?",row);}
    @FXML private void delete(){var row=table.getSelectionModel().getSelectedItem();if(row==null){FxWorkspace.warnUser("Pilih transaksi terlebih dahulu.");return;}if(!FxWorkspace.confirm(owner(),"Hapus catatan #"+row.get("No. Kwitansi")+" secara permanen?"))return;execute("DELETE FROM Pembayaran WHERE id_pembayaran=?",row);}
    private void execute(String sql,Map<String,Object> row){try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setObject(1,row.get("No. Kwitansi"));p.executeUpdate();reload();}catch(SQLException e){FxWorkspace.showError(e);}}
    private Stage owner(){return (Stage)table.getScene().getWindow();}
}

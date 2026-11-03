package controller;

import dao.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ReportsController {
    @FXML private TextField yearField;
    @FXML private TableView<Map<String,Object>> table;
    @FXML private BarChart<String,Number> chart;
    @FXML private void initialize(){yearField.textProperty().addListener((o,a,b)->reload());reload();}
    private void reload(){String sql="SELECT bulan AS Bulan,COUNT(*) AS Tagihan,SUM(CASE WHEN status='lunas' THEN 1 ELSE 0 END) AS Lunas,COALESCE(SUM(CASE WHEN status='lunas' THEN nominal ELSE 0 END),0) AS Pemasukan FROM Pembayaran WHERE tahun_ajaran=? GROUP BY bulan ORDER BY CASE bulan WHEN 'Juli' THEN 1 WHEN 'Agustus' THEN 2 WHEN 'September' THEN 3 WHEN 'Oktober' THEN 4 WHEN 'November' THEN 5 WHEN 'Desember' THEN 6 WHEN 'Januari' THEN 7 WHEN 'Februari' THEN 8 WHEN 'Maret' THEN 9 WHEN 'April' THEN 10 WHEN 'Mei' THEN 11 ELSE 12 END";try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setString(1,yearField.getText());try(ResultSet rs=p.executeQuery()){FxWorkspace.fillTable(table,rs);}XYChart.Series<String,Number> series=new XYChart.Series<>();for(var row:table.getItems())series.getData().add(new XYChart.Data<>(String.valueOf(row.get("Bulan")),((Number)row.get("Pemasukan")).longValue()));chart.setData(FXCollections.observableArrayList(series));}catch(SQLException e){FxWorkspace.showError(e);}}
}

package controller;

import dao.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/** JavaFX workspace for all authenticated screens. */
public final class FxWorkspace {
    private static final String[] MONTHS = {"Juli", "Agustus", "September", "Oktober", "November", "Desember", "Januari", "Februari", "Maret", "April", "Mei", "Juni"};
    private static final String[] PAGES = {"Dashboard", "Data Siswa", "Tagihan", "Riwayat", "Laporan"};
    private FxWorkspace() {}

    public static void show(Stage stage, String page) {
        try {
            FXMLLoader loader = new FXMLLoader(FxWorkspace.class.getResource("/fxml/workspace.fxml"));
            Node shell = loader.load();
            WorkspaceController controller = loader.getController();
            controller.setStage(stage);
            controller.show(page);
            Scene scene = new Scene((javafx.scene.Parent) shell, 1180, 760);
            scene.getStylesheets().add(FxWorkspace.class.getResource("/styles/login.css").toExternalForm());
            scene.getStylesheets().add(FxWorkspace.class.getResource("/styles/workspace.css").toExternalForm());
            stage.setTitle("Aplikasi Pembayaran SPP · " + page);
            stage.setMinWidth(980);
            stage.setMinHeight(650);
            stage.setScene(scene);
            stage.show();
        } catch (java.io.IOException e) {
            throw new IllegalStateException("Gagal memuat layout aplikasi.", e);
        }
    }

    static Node createPage(Stage stage, String page) {
        VBox content = new VBox(18);
        content.setPadding(new Insets(30, 34, 30, 34));
        content.setMaxWidth(Double.MAX_VALUE);
        content.setMaxHeight(Double.MAX_VALUE);
        content.getChildren().add(switch (page) {
            case "Data Siswa" -> loadFxml("/fxml/students.fxml");
            case "Tagihan" -> loadFxml("/fxml/payments.fxml");
            case "Riwayat" -> loadFxml("/fxml/history.fxml");
            case "Laporan" -> loadFxml("/fxml/reports.fxml");
            default -> dashboard();
        });
        return content;
    }

    private static Node sidebar(Stage stage, String current) {
        Label brand = new Label("SPP\nPEMBAYARAN SEKOLAH");
        brand.getStyleClass().add("brand");
        VBox menu = new VBox(8);
        for (String page : PAGES) {
            Button button = new Button(page);
            button.getStyleClass().add("nav-button");
            if (page.equals(current)) button.getStyleClass().add("nav-selected");
            button.setMaxWidth(Double.MAX_VALUE);
            button.setOnAction(e -> show(stage, page));
            menu.getChildren().add(button);
        }
        Button logout = new Button("Keluar");
        logout.getStyleClass().add("logout-button");
        logout.setMaxWidth(Double.MAX_VALUE);
        logout.setOnAction(e -> LoginApp.showLogin(stage));
        VBox side = new VBox(28, brand, new Label("MENU UTAMA"), menu, new VBox(), logout);
        side.setPadding(new Insets(28, 18, 24, 18));
        side.setPrefWidth(230);
        side.getStyleClass().add("sidebar");
        VBox.setVgrow(side.getChildren().get(3), Priority.ALWAYS);
        return side;
    }

    private static Node dashboard() { return loadFxml("/fxml/dashboard.fxml"); }

    private static Node loadFxml(String path) {
        try { return new FXMLLoader(FxWorkspace.class.getResource(path)).load(); }
        catch (java.io.IOException e) { throw new IllegalStateException("Gagal memuat " + path, e); }
    }

    private static Node students(Stage stage, VBox parent) {
        VBox page = pageHeading("Data Siswa", "Kelola siswa dan tagihan tahun ajaran");
        TextField search = new TextField(); search.setPromptText("Cari nama, NIS, atau kelas"); search.getStyleClass().add("search-field");
        TextField year = new TextField("2026/2027"); year.setPromptText("Tahun ajaran"); year.setPrefWidth(130);
        TableView<Map<String,Object>> table = new TableView<>();
        Runnable reload = () -> {
            String pattern = "%" + search.getText() + "%";
            load(table, studentSql(), year.getText(), year.getText(), year.getText(), pattern, pattern, pattern);
        };
        search.textProperty().addListener((o,a,b)->reload.run());
        Button add = action("+ Tambah Siswa", "primary-button", () -> studentDialog(stage, null, year.getText(), reload));
        Button edit = action("Ubah", "secondary-button", () -> { var row=table.getSelectionModel().getSelectedItem(); if(row==null) warn("Pilih siswa pada tabel."); else studentDialog(stage,row,year.getText(),reload); });
        Button delete = action("Hapus", "danger-button", () -> deleteStudent(stage,table,reload));
        HBox tools = new HBox(10, search, year, add, edit, delete); HBox.setHgrow(search,Priority.ALWAYS);
        page.getChildren().addAll(tools, table); VBox.setVgrow(table,Priority.ALWAYS); reload.run(); return page;
    }

    static String studentSql() {
        return """
                SELECT s.id_siswa AS NIS, s.nama AS "Nama Siswa", s.kelas AS Kelas,
                COALESCE((SELECT p.nominal FROM Pembayaran p WHERE p.id_siswa=s.id_siswa AND p.tahun_ajaran=? AND p.status='menunggak' LIMIT 1),
                (SELECT p.nominal FROM Pembayaran p WHERE p.id_siswa=s.id_siswa AND p.tahun_ajaran=? LIMIT 1),0) AS "SPP Bulanan",
                SUM(CASE WHEN p.status='lunas' THEN 1 ELSE 0 END) AS "Bulan Lunas",
                COUNT(p.id_pembayaran) AS "Jumlah Tagihan"
                FROM siswa s LEFT JOIN Pembayaran p ON p.id_siswa=s.id_siswa AND p.tahun_ajaran=?
                WHERE s.nama LIKE ? OR s.kelas LIKE ? OR CAST(s.id_siswa AS TEXT) LIKE ?
                GROUP BY s.id_siswa ORDER BY s.id_siswa""";
    }

    static void load(TableView<Map<String,Object>> table, String sql, String... params) {
        try (Connection c=Database.getConnection(); PreparedStatement ps=c.prepareStatement(sql)) {
            for(int i=0;i<params.length;i++) ps.setString(i+1, params[i]);
            setRows(table, ps.executeQuery());
        } catch(SQLException e) { error(e); }
    }

    static void studentDialog(Stage owner, Map<String,Object> row, String year, Runnable reload) {
        Dialog<ButtonType> dialog = new Dialog<>(); dialog.initOwner(owner); dialog.setTitle(row==null?"Tambah Siswa":"Ubah Data Siswa");
        TextField name=new TextField(row==null?"":String.valueOf(row.get("Nama Siswa")));
        TextField kelas=new TextField(row==null?"":String.valueOf(row.get("Kelas")));
        TextField nominal=new TextField(row==null?"":String.valueOf(row.get("SPP Bulanan")));
        GridPane form=new GridPane(); form.setHgap(12); form.setVgap(12); form.addRow(0,new Label("Nama siswa"),name); form.addRow(1,new Label("Kelas"),kelas); form.addRow(2,new Label("SPP per bulan (opsional)"),nominal);
        dialog.getDialogPane().setContent(form); dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);
        dialog.showAndWait().filter(b->b==ButtonType.OK).ifPresent(b->{
            if(name.getText().isBlank()||kelas.getText().isBlank()){warn("Nama dan kelas harus diisi.");return;}
            try(Connection c=Database.getConnection()){
                c.setAutoCommit(false); int amount=nominal.getText().isBlank()?0:Integer.parseInt(nominal.getText().replaceAll("[^0-9]",""));
                if(row==null){ try(PreparedStatement ps=c.prepareStatement("INSERT INTO siswa(nama,kelas) VALUES(?,?)")){ps.setString(1,name.getText().trim());ps.setString(2,kelas.getText().trim());ps.executeUpdate();}
                    int id=(int)scalar(c,"SELECT last_insert_rowid()"); if(amount>0) createStudentBills(c,id,year,amount);
                }else{int id=((Number)row.get("NIS")).intValue(); try(PreparedStatement ps=c.prepareStatement("UPDATE siswa SET nama=?,kelas=? WHERE id_siswa=?")){ps.setString(1,name.getText().trim());ps.setString(2,kelas.getText().trim());ps.setInt(3,id);ps.executeUpdate();}
                    if(amount>0){try(PreparedStatement ps=c.prepareStatement("UPDATE Pembayaran SET nominal=? WHERE id_siswa=? AND tahun_ajaran=? AND status<>'lunas'")){ps.setInt(1,amount);ps.setInt(2,id);ps.setString(3,year);ps.executeUpdate();}createStudentBills(c,id,year,amount);}}
                c.commit(); reload.run();
            }catch(Exception ex){error(ex);}
        });
    }

    private static void createStudentBills(Connection c,int id,String year,int amount)throws SQLException{
        String sql="INSERT INTO Pembayaran(id_siswa,bulan,tahun_ajaran,nominal,tgl_bayar,status) SELECT ?,?,?,?,NULL,'menunggak' WHERE NOT EXISTS(SELECT 1 FROM Pembayaran WHERE id_siswa=? AND bulan=? AND tahun_ajaran=?)";
        try(PreparedStatement ps=c.prepareStatement(sql)){for(String month:MONTHS){ps.setInt(1,id);ps.setString(2,month);ps.setString(3,year);ps.setInt(4,amount);ps.setInt(5,id);ps.setString(6,month);ps.setString(7,year);ps.executeUpdate();}}
    }

    static void deleteStudent(Stage owner,TableView<Map<String,Object>> table,Runnable reload){
        var row=table.getSelectionModel().getSelectedItem();if(row==null){warn("Pilih siswa pada tabel.");return;}
        if(!confirm(owner,"Hapus siswa "+row.get("Nama Siswa")+" beserta seluruh catatan tagihannya?"))return;
        try(Connection c=Database.getConnection()){c.setAutoCommit(false);try(PreparedStatement p=c.prepareStatement("DELETE FROM Pembayaran WHERE id_siswa=?")){p.setObject(1,row.get("NIS"));p.executeUpdate();}try(PreparedStatement p=c.prepareStatement("DELETE FROM siswa WHERE id_siswa=?")){p.setObject(1,row.get("NIS"));p.executeUpdate();}c.commit();reload.run();}catch(SQLException e){error(e);}
    }

    private static Node payments(Stage stage,VBox parent){
        VBox page=pageHeading("Tagihan & Pembayaran","Kelola tagihan dan catat pembayaran siswa");
        TextField search=new TextField();search.setPromptText("Cari siswa, NIS, atau bulan");search.getStyleClass().add("search-field");
        ComboBox<String> filter=new ComboBox<>(FXCollections.observableArrayList("Semua","Tagihan","Pembayaran","A-Z"));filter.setValue("Semua");
        TableView<Map<String,Object>> table=new TableView<>();
        Runnable reload=()->load(table,"""
                SELECT p.id_pembayaran AS "No. Kwitansi",p.tgl_bayar AS Tanggal,p.id_siswa AS NIS,
                COALESCE(s.nama,'-') AS "Nama Siswa",s.kelas AS Kelas,p.bulan AS Bulan,
                p.tahun_ajaran AS "Tahun Ajaran",p.nominal AS Jumlah,p.status AS Status
                FROM Pembayaran p LEFT JOIN siswa s ON s.id_siswa=p.id_siswa
                WHERE (?='' OR s.nama LIKE ? OR CAST(p.id_siswa AS TEXT) LIKE ? OR p.bulan LIKE ?)
                AND (?='Semua' OR (?='Tagihan' AND p.status='menunggak') OR (?='Pembayaran' AND p.status='lunas'))
                ORDER BY CASE WHEN ?='A-Z' THEN s.nama END COLLATE NOCASE,p.id_pembayaran DESC
                """,search.getText(),"%"+search.getText()+"%","%"+search.getText()+"%","%"+search.getText()+"%",filter.getValue(),filter.getValue(),filter.getValue(),filter.getValue());
        search.textProperty().addListener((o,a,b)->reload.run());filter.valueProperty().addListener((o,a,b)->reload.run());
        Button create=action("Buat Tagihan Tahunan","primary-button",()->createYearBills(stage,reload));
        Button receive=action("Terima Pembayaran","secondary-button",()->receive(stage,table,reload));
        Button receipt=action("Lihat Kwitansi","secondary-button",()->receipt(table));
        HBox tools=new HBox(10,search,filter,create,receive,receipt);HBox.setHgrow(search,Priority.ALWAYS);
        page.getChildren().addAll(tools,table);VBox.setVgrow(table,Priority.ALWAYS);reload.run();return page;
    }

    static void createYearBills(Stage owner,Runnable reload){
        Dialog<ButtonType>d=new Dialog<>();d.initOwner(owner);d.setTitle("Buat Tagihan Tahunan");TextField y=new TextField("2026/2027"),n=new TextField("500000");GridPane g=new GridPane();g.setHgap(12);g.setVgap(12);g.addRow(0,new Label("Tahun ajaran"),y);g.addRow(1,new Label("Nominal per bulan (Rp)"),n);d.getDialogPane().setContent(g);d.getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);d.showAndWait().filter(b->b==ButtonType.OK).ifPresent(b->{try{int amount=Integer.parseInt(n.getText().replaceAll("[^0-9]",""));int count=0;try(Connection c=Database.getConnection()){c.setAutoCommit(false);String sql="INSERT INTO Pembayaran(id_siswa,bulan,tahun_ajaran,nominal,tgl_bayar,status) SELECT id_siswa,?,?,?,NULL,'menunggak' FROM siswa s WHERE NOT EXISTS(SELECT 1 FROM Pembayaran p WHERE p.id_siswa=s.id_siswa AND p.bulan=? AND p.tahun_ajaran=?)";try(PreparedStatement p=c.prepareStatement(sql)){for(String m:MONTHS){p.setString(1,m);p.setString(2,y.getText().trim());p.setInt(3,amount);p.setString(4,m);p.setString(5,y.getText().trim());count+=p.executeUpdate();}}c.commit();}reload.run();info(count+" tagihan baru berhasil dibuat.");}catch(Exception ex){error(ex);}});
    }
    static void receive(Stage owner,TableView<Map<String,Object>> table,Runnable reload){var row=table.getSelectionModel().getSelectedItem();if(row==null){warn("Pilih tagihan pada tabel.");return;}if(!confirm(owner,"Tandai kwitansi #"+row.get("No. Kwitansi")+" sebagai lunas?"))return;try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement("UPDATE Pembayaran SET status='lunas',tgl_bayar=? WHERE id_pembayaran=?")){p.setString(1,LocalDate.now().toString());p.setObject(2,row.get("No. Kwitansi"));p.executeUpdate();reload.run();info("Pembayaran berhasil diterima.");}catch(SQLException e){error(e);}}
    static void receipt(TableView<Map<String,Object>> table){var r=table.getSelectionModel().getSelectedItem();if(r==null){warn("Pilih transaksi terlebih dahulu.");return;}info("KWITANSI PEMBAYARAN SPP\nNo. Kwitansi: "+r.get("No. Kwitansi")+"\nTanggal: "+r.get("Tanggal")+"\nNIS: "+r.get("NIS")+"\nNama: "+r.get("Nama Siswa")+"\nKelas: "+r.get("Kelas")+"\nBulan: "+r.get("Bulan")+"\nTahun Ajaran: "+r.get("Tahun Ajaran")+"\nJumlah: "+money(((Number)r.get("Jumlah")).longValue())+"\nStatus: "+r.get("Status"));}

    private static Node history(Stage stage,VBox parent){VBox page=pageHeading("Riwayat Pembayaran","Daftar pembayaran yang sudah diterima");TextField year=new TextField("2026/2027");year.setPrefWidth(130);TextField search=new TextField();search.setPromptText("Cari siswa, NIS, atau bulan");search.getStyleClass().add("search-field");TableView<Map<String,Object>> table=new TableView<>();Runnable reload=()->load(table,"""
                SELECT p.id_pembayaran AS "No. Kwitansi",p.tgl_bayar AS Tanggal,p.id_siswa AS NIS,
                COALESCE(s.nama,'-') AS "Nama Siswa",s.kelas AS Kelas,p.bulan AS Bulan,
                p.tahun_ajaran AS "Tahun Ajaran",p.nominal AS Jumlah FROM Pembayaran p
                LEFT JOIN siswa s ON s.id_siswa=p.id_siswa WHERE p.status='lunas' AND p.tahun_ajaran=?
                AND (?='' OR s.nama LIKE ? OR CAST(p.id_siswa AS TEXT) LIKE ? OR p.bulan LIKE ?)
                ORDER BY p.tgl_bayar DESC,p.id_pembayaran DESC
                """,year.getText(),search.getText(),"%"+search.getText()+"%","%"+search.getText()+"%","%"+search.getText()+"%");year.textProperty().addListener((o,a,b)->reload.run());search.textProperty().addListener((o,a,b)->reload.run());Button cancel=action("Batalkan Pembayaran","danger-button",()->{var r=table.getSelectionModel().getSelectedItem();if(r==null){warn("Pilih transaksi terlebih dahulu.");return;}if(!confirm(stage,"Batalkan pembayaran #"+r.get("No. Kwitansi")+"? Catatan akan kembali menjadi tagihan."))return;try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement("UPDATE Pembayaran SET status='menunggak',tgl_bayar=NULL WHERE id_pembayaran=?")){p.setObject(1,r.get("No. Kwitansi"));p.executeUpdate();reload.run();}catch(SQLException e){error(e);}});Button delete=action("Hapus Catatan","secondary-button",()->{var r=table.getSelectionModel().getSelectedItem();if(r==null){warn("Pilih transaksi terlebih dahulu.");return;}if(confirm(stage,"Hapus catatan kwitansi #"+r.get("No. Kwitansi")+" secara permanen?")){try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement("DELETE FROM Pembayaran WHERE id_pembayaran=?")){p.setObject(1,r.get("No. Kwitansi"));p.executeUpdate();reload.run();}catch(SQLException e){error(e);}}});HBox tools=new HBox(10,year,search,cancel,delete);HBox.setHgrow(search,Priority.ALWAYS);page.getChildren().addAll(tools,table);VBox.setVgrow(table,Priority.ALWAYS);reload.run();return page;}

    private static Node reports(){VBox page=pageHeading("Laporan","Ringkasan pembayaran per bulan dan tahun ajaran");TextField year=new TextField("2026/2027");year.setPrefWidth(140);TableView<Map<String,Object>> table=new TableView<>();BarChart<String,Number> chart=new BarChart<>(new CategoryAxis(),new NumberAxis());chart.setTitle("Pembayaran per Bulan");chart.setLegendVisible(false);chart.setPrefHeight(340);Runnable refresh=()->{String sql="SELECT bulan AS Bulan,COUNT(*) AS Tagihan,SUM(CASE WHEN status='lunas' THEN 1 ELSE 0 END) AS Lunas,COALESCE(SUM(CASE WHEN status='lunas' THEN nominal ELSE 0 END),0) AS Pemasukan FROM Pembayaran WHERE tahun_ajaran=? GROUP BY bulan ORDER BY CASE bulan WHEN 'Juli' THEN 1 WHEN 'Agustus' THEN 2 WHEN 'September' THEN 3 WHEN 'Oktober' THEN 4 WHEN 'November' THEN 5 WHEN 'Desember' THEN 6 WHEN 'Januari' THEN 7 WHEN 'Februari' THEN 8 WHEN 'Maret' THEN 9 WHEN 'April' THEN 10 WHEN 'Mei' THEN 11 ELSE 12 END";try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setString(1,year.getText());ResultSet rs=p.executeQuery();setRows(table,rs);XYChart.Series<String,Number>s=new XYChart.Series<>();for(var r:table.getItems())s.getData().add(new XYChart.Data<>(String.valueOf(r.get("Bulan")),((Number)r.get("Pemasukan")).longValue()));chart.getData().setAll(s);}catch(SQLException e){error(e);}};year.textProperty().addListener((o,a,b)->refresh.run());page.getChildren().addAll(new HBox(10,new Label("Tahun ajaran"),year),section("Ringkasan bulanan",table),chart);VBox.setVgrow(table,Priority.ALWAYS);refresh.run();return page;}

    private static TableView<Map<String,Object>> table(Connection c,String sql)throws SQLException{TableView<Map<String,Object>>t=new TableView<>();try(var s=c.createStatement();var r=s.executeQuery(sql)){setRows(t,r);}return t;}
    private static void setRows(TableView<Map<String,Object>> table,ResultSet rs)throws SQLException{table.getColumns().clear();ObservableList<Map<String,Object>> rows=FXCollections.observableArrayList();int n=rs.getMetaData().getColumnCount();for(int i=1;i<=n;i++){final String key=rs.getMetaData().getColumnLabel(i);TableColumn<Map<String,Object>,String> col=new TableColumn<>(key);col.setCellValueFactory(d->new javafx.beans.property.SimpleStringProperty(String.valueOf(d.getValue().getOrDefault(key,""))));col.setPrefWidth(Math.max(110,Math.min(230,key.length()*12+40)));table.getColumns().add(col);}while(rs.next()){Map<String,Object> row=new LinkedHashMap<>();for(int i=1;i<=n;i++)row.put(rs.getMetaData().getColumnLabel(i),rs.getObject(i));rows.add(row);}table.setItems(rows);table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);}
    static void populateTable(TableView<Map<String,Object>> table,Connection c,String sql)throws SQLException{try(var s=c.createStatement();var rs=s.executeQuery(sql)){setRows(table,rs);}}
    static void fillTable(TableView<Map<String,Object>> table,ResultSet rs)throws SQLException{setRows(table,rs);}
    static long scalar(Connection c,String sql)throws SQLException{try(var s=c.createStatement();var r=s.executeQuery(sql)){return r.next()?r.getLong(1):0;}}
    private static VBox pageHeading(String title,String subtitle){Label t=new Label(title);t.getStyleClass().add("page-title");Label s=new Label(subtitle);s.getStyleClass().add("page-subtitle");return new VBox(4,t,s);}
    static VBox metric(String label,String value){Label l=new Label(label);l.getStyleClass().add("metric-label");Label v=new Label(value);v.getStyleClass().add("metric-value");VBox box=new VBox(12,l,v);box.getStyleClass().add("metric-card");HBox.setHgrow(box,Priority.ALWAYS);box.setMaxWidth(Double.MAX_VALUE);return box;}
    private static VBox section(String title,Node body){Label l=new Label(title);l.getStyleClass().add("section-title");VBox b=new VBox(12,l,body);b.getStyleClass().add("surface-card");VBox.setVgrow(body,Priority.ALWAYS);return b;}
    private static Button action(String text,String style,Runnable run){Button b=new Button(text);b.getStyleClass().add(style);b.setOnAction(e->run.run());return b;}
    private static Label errorNode(Exception e){Label l=new Label("Gagal membaca data: "+e.getMessage());l.getStyleClass().add("error-message");return l;}
    static String money(long n){return String.format(java.util.Locale.forLanguageTag("id-ID"),"Rp%,d",n);}
    static void showError(Exception e){error(e);}
    static boolean confirm(Stage owner,String text){Alert a=new Alert(Alert.AlertType.CONFIRMATION,text,ButtonType.CANCEL,ButtonType.OK);a.initOwner(owner);a.setHeaderText(null);return a.showAndWait().orElse(ButtonType.CANCEL)==ButtonType.OK;}
    private static void info(String text){alert(Alert.AlertType.INFORMATION,text);} private static void warn(String text){alert(Alert.AlertType.WARNING,text);} static void warnUser(String text){warn(text);} private static void error(Exception e){alert(Alert.AlertType.ERROR,e.getMessage());}
    private static void alert(Alert.AlertType type,String text){Alert a=new Alert(type,text,ButtonType.OK);a.setHeaderText(null);a.showAndWait();}
}




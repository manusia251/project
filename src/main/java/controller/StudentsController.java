package controller;

import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StudentsController {
    @FXML private TextField searchField;
    @FXML private TextField yearField;
    @FXML private TableView<Map<String,Object>> table;
    @FXML private void initialize() {
        searchField.textProperty().addListener((o,a,b)->reload());
        yearField.textProperty().addListener((o,a,b)->reload());
        reload();
    }
    private void reload() {
        String pattern="%"+searchField.getText()+"%";
        FxWorkspace.load(table,FxWorkspace.studentSql(),yearField.getText(),yearField.getText(),yearField.getText(),pattern,pattern,pattern);
    }
    @FXML private void add(){FxWorkspace.studentDialog(owner(),null,yearField.getText(),this::reload);}
    @FXML private void edit(){var row=table.getSelectionModel().getSelectedItem();if(row==null){FxWorkspace.warnUser("Pilih siswa pada tabel.");return;}FxWorkspace.studentDialog(owner(),row,yearField.getText(),this::reload);}
    @FXML private void delete(){FxWorkspace.deleteStudent(owner(),table,this::reload);}
    private Stage owner(){return (Stage)table.getScene().getWindow();}
}

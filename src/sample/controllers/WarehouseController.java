package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.dto.Warehouse;

public class WarehouseController {
    public TextField fieldName;
    public Warehouse warehouse;

    public WarehouseController() {}

    @FXML
    public void initialize() {

    }


    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void clickCancel(ActionEvent actionEvent) {
        warehouse = null;
        Stage stage = (Stage) fieldName.getScene().getWindow();
        stage.close();
    }

    public void clickOk(ActionEvent actionEvent) {
        boolean Ok = true;

        if (fieldName.getText().trim().isEmpty()) {
            fieldName.getStyleClass().removeAll("poljeIspravno");
            fieldName.getStyleClass().add("poljeNijeIspravno");
            Ok = false;
        } else {
            fieldName.getStyleClass().removeAll("poljeNijeIspravno");
            fieldName.getStyleClass().add("poljeIspravno");
        }

        if (!Ok) return;

        if (warehouse == null) warehouse = new Warehouse();
        warehouse.setName(fieldName.getText());
        Stage stage = (Stage) fieldName.getScene().getWindow();
        stage.close();
    }
}

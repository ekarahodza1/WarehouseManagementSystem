package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.dto.Warehouse;

public class WarehouseController {
    public TextField fieldName;
    public TextField fieldLocation;
    public Warehouse warehouse;

    public WarehouseController() {

    }

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

        if (fieldName.getText().trim().isEmpty() || fieldName.getText().toString().length() <= 1) {
            fieldName.getStyleClass().removeAll("fieldCorrect");
            fieldName.getStyleClass().add("fieldIncorrect");
            Ok = false;
        } else {
            fieldName.getStyleClass().removeAll("fieldIncorrect");
            fieldName.getStyleClass().add("fieldCorrect");
        }

        if (fieldLocation.getText().trim().isEmpty() || fieldName.getText().toString().length() <= 1) {
            fieldLocation.getStyleClass().removeAll("fieldCorrect");
            fieldLocation.getStyleClass().add("fieldIncorrect");
            Ok = false;
        } else {
            fieldLocation.getStyleClass().removeAll("fieldIncorrect");
            fieldLocation.getStyleClass().add("fieldCorrect");
        }

        if (!Ok) return;

        if (warehouse == null) warehouse = new Warehouse();
        warehouse.setName(fieldName.getText());
        warehouse.setLocation(fieldLocation.getText());
        Stage stage = (Stage) fieldName.getScene().getWindow();
        stage.close();
    }
}

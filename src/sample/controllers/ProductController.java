package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.dto.Product;
import sample.dto.Type;
import sample.dto.Warehouse;

import java.util.ArrayList;

public class ProductController {
    public TextField fieldName;
    public ChoiceBox<Type> choiceType;
    public TextField fieldAmount;
    public TextField fieldUnitPrice;
    public TextField fieldPrice;
    public ChoiceBox<Warehouse> choiceWarehouse;
    public DatePicker dateAdded;
    public DatePicker expirationDate;
    public ObservableList<Warehouse> listWarehouse;
    public ObservableList<Type> listType;
    private Product product;


    public ProductController(Product product,  ArrayList<Warehouse> warehouses) {
        this.product = product;
        listWarehouse = FXCollections.observableArrayList(warehouses);
        listType = FXCollections.observableArrayList(Type.values());
    }

    @FXML
    public void initialize() {
        choiceWarehouse.setItems(listWarehouse);
        choiceType.setItems(listType);
        if (product != null) {
            fieldName.setText(product.getName());
            fieldAmount.setText(Integer.toString(product.getAmount()));
            fieldUnitPrice.setText(Double.toString(product.getUnitPrice()));
            fieldPrice.setText(Double.toString(product.getPrice()));
            choiceWarehouse.getSelectionModel().select(product.getWarehouse());
            choiceType.getSelectionModel().select(product.getType());
            dateAdded.setUserData(product.getDateAdded());
            if (product.getExpirationDate() != null) expirationDate.setUserData(product.getExpirationDate());
        }
    }

    public Product getProduct() {
        return product;
    }

    public void clickCancel(ActionEvent actionEvent) {
        product = null;
        Stage stage = (Stage) fieldName.getScene().getWindow();
        stage.close();
    }

    public void clickOk(ActionEvent actionEvent) {
        boolean sveOk = true;

//        if (fieldNaziv.getText().trim().isEmpty()) {
//            fieldNaziv.getStyleClass().removeAll("poljeIspravno");
//            fieldNaziv.getStyleClass().add("poljeNijeIspravno");
//            sveOk = false;
//        } else {
//            fieldNaziv.getStyleClass().removeAll("poljeNijeIspravno");
//            fieldNaziv.getStyleClass().add("poljeIspravno");
//        }
//
//
//        int brojStanovnika = 0;
//        try {
//            brojStanovnika = Integer.parseInt(fieldBrojStanovnika.getText());
//        } catch (NumberFormatException e) {
//            // ...
//        }
//        if (brojStanovnika <= 0) {
//            fieldBrojStanovnika.getStyleClass().removeAll("poljeIspravno");
//            fieldBrojStanovnika.getStyleClass().add("poljeNijeIspravno");
//            sveOk = false;
//        } else {
//            fieldBrojStanovnika.getStyleClass().removeAll("poljeNijeIspravno");
//            fieldBrojStanovnika.getStyleClass().add("poljeIspravno");
//        }
//
//        if (!sveOk) return;
//
//        if (grad == null) grad = new Grad();
//        grad.setNaziv(fieldNaziv.getText());
//        grad.setBrojStanovnika(Integer.parseInt(fieldBrojStanovnika.getText()));
//        grad.setDrzava(choiceDrzava.getValue());
        Stage stage = (Stage) fieldName.getScene().getWindow();
        stage.close();
    }


}

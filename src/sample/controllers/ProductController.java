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
import java.util.Date;

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
        choiceWarehouse.getSelectionModel().selectFirst();
        choiceType.getSelectionModel().selectFirst();
        if (product != null) {
            fieldName.setText(product.getName());
            fieldAmount.setText(Integer.toString(product.getAmount()));
            fieldUnitPrice.setText(Double.toString(product.getUnitPrice()));
            fieldPrice.setText(Double.toString(product.getPrice()));
            choiceWarehouse.getSelectionModel().select(product.getWarehouse().getId());
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
        boolean Ok = true;

        if (fieldName.getText().trim().isEmpty()) {
            fieldName.getStyleClass().removeAll("fieldCorrect");
            fieldName.getStyleClass().add("fieldIncorrect");
            Ok = false;
        } else {
            fieldName.getStyleClass().removeAll("fieldIncorrect");
            fieldName.getStyleClass().add("fieldCorrect");
        }


        int amount = 0;
        try {
            amount = Integer.parseInt(fieldAmount.getText());
        } catch (NumberFormatException e) {
            // ...
        }
        if (amount <= 0) {
            fieldAmount.getStyleClass().removeAll("fieldCorrect");
            fieldAmount.getStyleClass().add("fieldIncorrect");
            Ok = false;
        } else {
            fieldAmount.getStyleClass().removeAll("fieldIncorrect");
            fieldAmount.getStyleClass().add("fieldCorrect");
        }

        double unitPrice = 0;
        try {
            unitPrice = Double.parseDouble(fieldUnitPrice.getText());
        } catch (NumberFormatException e) {
            // ...
        }
        if (unitPrice <= 0) {
            fieldUnitPrice.getStyleClass().removeAll("fieldCorrect");
            fieldUnitPrice.getStyleClass().add("fieldIncorrect");
            Ok = false;
        } else {
            fieldUnitPrice.getStyleClass().removeAll("fieldIncorrect");
            fieldUnitPrice.getStyleClass().add("fieldCorrect");
        }


        double price = 0;
        try {
            price = Double.parseDouble(fieldPrice.getText());
        } catch (NumberFormatException e) {
            // ...
        }
        if (price <= 0) {
            fieldPrice.getStyleClass().removeAll("fieldCorrect");
            fieldPrice.getStyleClass().add("fieldIncorrect");
            Ok = false;
        } else {
            fieldPrice.getStyleClass().removeAll("fieldIncorrect");
            fieldPrice.getStyleClass().add("fieldCorrect");
        }



        if (!Ok) return;

        if (product == null) product = new Product();
        product.setName(fieldName.getText());
        product.setAmount(amount);
        product.setUnitPrice(unitPrice);
        product.setPrice(price);
        product.setType(choiceType.getValue());
        product.setWarehouse(choiceWarehouse.getValue());
        product.setDateAdded((Date) dateAdded.getUserData());
        product.setExpirationDate((Date) dateAdded.getUserData());

        Stage stage = (Stage) fieldName.getScene().getWindow();
        stage.close();
    }


}

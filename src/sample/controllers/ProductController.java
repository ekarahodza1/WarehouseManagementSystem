package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.dto.Product;
import sample.dto.Type;
import sample.dto.Warehouse;
import sample.other.EmptyDateFieldException;

import java.util.ArrayList;
import java.util.Date;

public class ProductController {
    public TextField fieldName;
    public ChoiceBox<Type> choiceType;
    public TextField fieldAmount;
    public TextField fieldUnitPrice;
    public TextField fieldPrice;
    public ChoiceBox<Warehouse> choiceWarehouse;
    public TextField dateAdded;
    public TextField expirationDate;
    public ObservableList<Warehouse> listWarehouse;
    public ObservableList<Type> listType;
    private Product product;


    public ProductController(Product product,  ArrayList<Warehouse> warehouses) {
        this.product = product;
        listWarehouse = FXCollections.observableArrayList(warehouses);
        listType = FXCollections.observableArrayList(Type.values());
    }

    public ProductController(Product p) {
        p = new Product();
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
            dateAdded.setText(product.getDateAdded().toString());
            if (product.getExpirationDate() != null) expirationDate.setText(product.getExpirationDate().toString());
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

    public void clickOk(ActionEvent actionEvent)  {
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


        fieldPrice.setText(String.valueOf(amount * unitPrice));
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
        } else if(amount * unitPrice != price){
            fieldPrice.getStyleClass().removeAll("fieldCorrect");
            fieldPrice.getStyleClass().add("fieldIncorrect");
        }
        else {
            fieldPrice.getStyleClass().removeAll("fieldIncorrect");
            fieldPrice.getStyleClass().add("fieldCorrect");
        }

        Date date1 = null, date2 = null;

        try {
            date1 = java.sql.Date.valueOf(dateAdded.getText());
            dateAdded.getStyleClass().removeAll("fieldIncorrect");
            dateAdded.getStyleClass().add("fieldCorrect");
        }
        catch(Exception e){
            dateAdded.getStyleClass().removeAll("fieldCorrect");
            dateAdded.getStyleClass().add("fieldIncorrect");
            Ok = false;
        }

        if (!expirationDate.getText().matches("")) {
            try {
                date2 = java.sql.Date.valueOf(dateAdded.getText());
                if (date2.before(date1)) {
                    expirationDate.getStyleClass().removeAll("fieldCorrect");
                    expirationDate.getStyleClass().add("fieldIncorrect");
                    dateAdded.getStyleClass().removeAll("fieldCorrect");
                    dateAdded.getStyleClass().add("fieldIncorrect");
                    Ok = false;
                }
                else {
                    expirationDate.getStyleClass().removeAll("fieldIncorrect");
                    expirationDate.getStyleClass().add("fieldCorrect");
                    dateAdded.getStyleClass().removeAll("fieldIncorrect");
                    dateAdded.getStyleClass().add("fieldCorrect");
                }
            } catch (Exception e) {
                expirationDate.getStyleClass().removeAll("fieldCorrect");
                expirationDate.getStyleClass().add("fieldIncorrect");
                Ok = false;
            }
        }





        if (!Ok) return;

        if (product == null) product = new Product();
        product.setName(fieldName.getText());
        product.setAmount(amount);
        product.setUnitPrice(unitPrice);
        product.setPrice(price);
        product.setType(choiceType.getValue());
        product.setWarehouse(choiceWarehouse.getValue());
        product.setDateAdded(java.sql.Date.valueOf(dateAdded.getText()));
         try {
            if (expirationDate.getText().matches("")) throw new EmptyDateFieldException("Empty field");
            product.setExpirationDate(java.sql.Date.valueOf(expirationDate.getText()));
        } catch (EmptyDateFieldException e) {
            e.printStackTrace();
        }


        Stage stage = (Stage) fieldName.getScene().getWindow();
        stage.close();
    }


}

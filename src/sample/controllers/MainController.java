package sample.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.dto.Product;
import sample.dto.Warehouse;
import sample.models.ProductModel;
import sample.models.WarehouseModel;

import java.io.IOException;
import java.util.Optional;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MainController {

    public TableView<Product> tableViewStorage;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colType;
    public TableColumn colAmount;
    public TableColumn colPrice;
    public TableColumn colPriceAll;
    public TableColumn colStorage;
    public TableColumn colDate;
    public TableColumn colDateExp;

    private ProductModel model;
    private WarehouseModel warehouseModel;
    private ObservableList<Product> listProduct;

    public MainController() {
        model = ProductModel.getInstance();
        warehouseModel = WarehouseModel.getInstance();
        listProduct = FXCollections.observableArrayList(model.getProducts());
    }

    @FXML
    public void initialize() {
        tableViewStorage.setItems(listProduct);
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colType.setCellValueFactory(new PropertyValueFactory("type"));
        colAmount.setCellValueFactory(new PropertyValueFactory("amount"));
        colPrice.setCellValueFactory(new PropertyValueFactory("unitPrice"));
        colPriceAll.setCellValueFactory(new PropertyValueFactory("price"));
        colStorage.setCellValueFactory(new PropertyValueFactory("warehouse"));
        colDate.setCellValueFactory(new PropertyValueFactory("dateAdded"));
        colDateExp.setCellValueFactory(new PropertyValueFactory("expirationDate"));
    }

    public void actionAddProduct(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/product.fxml"));
            ProductController productController = new ProductController(); //bila dva argumenta u konstruktoru
            loader.setController(productController);
            root = loader.load();
            stage.setTitle("New product");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                Product product = productController.getProduct();
                if (product != null) {
                    model.addProduct(product);
                    listProduct.setAll(model.getProducts());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void actionAddWarehouse(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/warehouse.fxml"));
            WarehouseController warehouseController = new WarehouseController(); //bila dva argumenta u konstruktoru
            loader.setController(warehouseController);
            root = loader.load();
            stage.setTitle("New warehouse");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                Warehouse warehouse = warehouseController.getWarehouse();
                if (warehouse != null) {
                    warehouseModel.addWarehouse(warehouse);
                    //listProduct.setAll(model.getProducts());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void actionUpdateProduct(ActionEvent actionEvent) {
        Product product = tableViewStorage.getSelectionModel().getSelectedItem();
        if (product == null) return;

        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/product.fxml"));
            ProductController productController = new ProductController();      //imaju dva argumenta
            loader.setController(productController);
            root = loader.load();
            stage.setTitle("Product");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();

            stage.setOnHiding( event -> {
                Product newProduct = productController.getProduct();
                if (newProduct != null) {
                    model.updateProduct(product);
                    listProduct.setAll(model.getProducts());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionDeleteProduct(ActionEvent actionEvent) {
        Product product = tableViewStorage.getSelectionModel().getSelectedItem();
        if (product == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete confirmation");
        alert.setHeaderText("Deleting product " + product.getName());
        alert.setContentText("Are you sure you want to delete product " + product.getName() +"?");
        alert.setResizable(true);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            model.deleteProduct(product);
            listProduct.setAll(model.getProducts());
        }
    }

}

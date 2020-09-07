package sample.controllers;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.dto.Product;
import sample.dto.Warehouse;
import sample.models.ProductModel;
import sample.models.WarehouseModel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
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
    public Button btnExit;

    private ProductModel model;
    private WarehouseModel warehouseModel;
    private ObservableList<Product> listProduct;

    public SimpleStringProperty time;
    public String getLTime() { return time.get(); }
    public SimpleStringProperty lTimeProperty() { return time; }
    public void setLTime(String label) { this.time.set(label); }


    public MainController() {
        model = ProductModel.getInstance();
        warehouseModel = WarehouseModel.getInstance();
        listProduct = FXCollections.observableArrayList(model.getProducts());
        time = new SimpleStringProperty("00:00");
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


        new Thread(() -> {
            try {
                int min = 0;
                int sec = 0;
                while (true) {
                    sec++;
                    if (min == 60) min = 0;
                    if (sec == 60) {
                        sec = 0;
                        min++;
                    }
                    String s = "";
                    if (min < 10) s += "0" + min + ":";
                    else s += min + ":";
                    if (sec < 10) s += "0" + sec;
                    else s += sec;
                    String finalS = s;
                    Platform.runLater(() -> setLTime(finalS));

                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {

            }
        }).start();
    }

    public void actionAddProduct(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/product.fxml"));
            ProductController productController = new ProductController(null, warehouseModel.getAll());
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
            WarehouseController warehouseController = new WarehouseController();
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
            ProductController productController = new ProductController(product, warehouseModel.getAll());
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
                    for(int i = 0; i < listProduct.size(); i++) System.out.println(listProduct.get(i).getName());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("izuzetak");
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

    public void actionDatabase(ActionEvent actionEvent){
        ArrayList<Product> products = new ArrayList<>();
        ArrayList<Warehouse> warehouses = new ArrayList<>();
        products.addAll(model.getProducts());
        warehouses.addAll(warehouseModel.getAll());

        String outputFilePath = "database.txt";

        File file = new File(outputFilePath);
        BufferedWriter bf = null;;
        try{
            bf = new BufferedWriter(new FileWriter(file) );
            bf.write("product");
            bf.newLine();
            bf.newLine();
            bf.write("id  | name           | type        | amount  | unitPrice | price    | warehouse | dateAdded | expirationDate ");
            bf.newLine();
            bf.write("-----------------------------------------------------------------------------------------------------------");
            bf.newLine();
            for(Product p : products){
                String s = ""; s += p.getId();
                while (s.length() < 4) s += " "; s += "| "; s += p.getName();
                while (s.length() <= 20) s += " "; s += "| "; s += p.getType();
                while (s.length() <= 34) s += " "; s += "| "; s += p.getAmount();
                while (s.length() <= 44) s += " "; s += "| "; s += p.getUnitPrice();
                while (s.length() <= 56) s += " "; s += "| "; s += p.getPrice();
                while (s.length() <= 66) s += " "; s += "| "; s += p.getWarehouse().getId();
                while (s.length() <= 78) s += " "; s += "| "; s += p.getDateAddedString();
                while (s.length() <= 91) s += " "; s += "| "; s += p.getExpirationDateString();

                bf.write(s);
                bf.newLine();
                bf.write("-----------------------------------------------------------------------------------------------------------");
                bf.newLine();
            }

            bf.newLine();
            bf.newLine();
            bf.write("warehouse");
            bf.newLine();
            bf.newLine();
            bf.write("id  | name           ");
            bf.newLine();
            bf.write("------------------------");
            bf.newLine();

            for (Warehouse w : warehouses) {
                String s = ""; s += w.getId();
                while (s.length() < 4) s += " "; s += "| "; s += w.getName();
                bf.write(s);
                bf.newLine();
                bf.write("------------------------");
                bf.newLine();
            }

            bf.flush();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                bf.close();
            }catch(Exception e){}
        }

    }



    public void clickExit(ActionEvent actionEvent) {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

}

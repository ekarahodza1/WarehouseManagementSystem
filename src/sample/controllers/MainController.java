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
import org.json.JSONArray;
import org.json.JSONObject;
import sample.dto.Product;
import sample.dto.Type;
import sample.dto.Warehouse;
import sample.models.ProductModel;
import sample.models.WarehouseModel;

import java.util.*;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.util.stream.Collectors;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MainController {

    public TableView<Product> tableViewStorage;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colType;
    public TableColumn colAmount;
    public TableColumn colPrice;
    public TableColumn colPriceAll;
    public TableColumn<Product, String> colStorage;
    public TableColumn colDate;
    public TableColumn colDateExp;
    public Button btnExit;
    public ChoiceBox choiceType;
    public ChoiceBox choiceWarehouse;

    private ProductModel model;
    private WarehouseModel warehouseModel;
    private ObservableList<Product> listProduct;
    public ObservableList<Warehouse> listWarehouse;
    public ObservableList<String> listType;

    public SimpleStringProperty time;
    public String getLTime() { return time.get(); }
    public SimpleStringProperty lTimeProperty() { return time; }
    public void setLTime(String label) { this.time.set(label); }

    public SimpleStringProperty value;
    public String getLValue() { return value.get(); }
    public SimpleStringProperty lValueProperty() { return value; }
    public void setLValue(String label) { this.value.set(label); }


    public MainController() {
        model = ProductModel.getInstance();
        warehouseModel = WarehouseModel.getInstance();
        listProduct = FXCollections.observableArrayList(model.getProducts());
        time = new SimpleStringProperty("00:00");
        value = new SimpleStringProperty("");
        listWarehouse = FXCollections.observableArrayList(warehouseModel.getAll());
        listWarehouse.add(0, new Warehouse(-1, "All"));
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList("All", "Clothes", "Food", "Hygiene", "Electronics", "Construction"));
        listType = FXCollections.observableArrayList(arrayList);
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
        colStorage.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getWarehouse().getName()));
       // colStorage.setCellValueFactory(new PropertyValueFactory("warehouse"));
        colDate.setCellValueFactory(new PropertyValueFactory("dateAdded"));
        colDateExp.setCellValueFactory(new PropertyValueFactory("expirationDate"));
        choiceType.setItems(listType);
        choiceWarehouse.setItems(listWarehouse);
        choiceWarehouse.getSelectionModel().selectFirst();
        choiceType.getSelectionModel().selectFirst();

        double value = 0;
        for (int i = 0; i < model.getProducts().size(); i++) value += model.getProducts().get(i).getPrice();
        setLValue(String.valueOf(value));


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

    public void actionExchangeRate(ActionEvent actionEvent){
        String adr = "http://data.fixer.io/api/latest?access_key=4955d494d233822ce160a499de840387&symbols=BAM,EUR,USD,GBP,AUD";
        try {
            URL url = new URL(adr);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            String json = "", line = null;
            while ((line = in.readLine()) != null)
                json = json + line;

            JSONObject obj = new JSONObject(json);
            Date date = null;
            date = java.sql.Date.valueOf(obj.getString("date"));
            JSONObject object  = obj.getJSONObject("rates");


            double bam = object.getDouble("BAM");
            double eur = object.getDouble("EUR");
            double gbp = object.getDouble("GBP");
            double usd = object.getDouble("USD");
            double aud = object.getDouble("AUD");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Exchange rates");
            alert.setHeaderText("Exchange rates for " + date);
            alert.setContentText("EUR   " + eur + "\nBAM   " + bam + "\nUSD   " + usd + "\nGBP   " + gbp + "\nAUD   " + aud);
            alert.setResizable(true);
            alert.showAndWait();

            in.close();
        } catch(MalformedURLException e) {
            System.out.println("String "+adr+" ne predstavlja validan URL");
        } catch(IOException e) {
            System.out.println("Greška pri kreiranju ulaznog toka");
            System.out.println(e.getMessage());
        } catch(Exception e) {
            System.out.println("Poteškoće sa obradom JSON podataka");
            System.out.println(e.getMessage());
        }

    }

    public void actionTypeChosen(ActionEvent actionEvent){
        List<Product> products = model.getProducts();
        List<Product> result = new ArrayList<>();

        if (choiceType.getValue().toString().matches("All")) {
            listProduct.setAll(products);
        }
        else if (choiceType.getValue().toString().matches("Clothes")){
            result = products.stream().filter(x -> "CLOTHES".equals(x.getType().toString())).collect(Collectors.toList());
            listProduct.setAll(result);
        }
        else if (choiceType.getValue().toString().matches("Food")){
            result = products.stream().filter(x -> "FOOD".equals(x.getType().toString())).collect(Collectors.toList());
            listProduct.setAll(result);
        }
        else if (choiceType.getValue().toString().matches("Hygiene")){
            result = products.stream().filter(x -> "HYGIENE".equals(x.getType().toString())).collect(Collectors.toList());
            listProduct.setAll(result);
        }
        else if (choiceType.getValue().toString().matches("Electronics")){
            result = products.stream().filter(x -> "ELECTRONICS".equals(x.getType().toString())).collect(Collectors.toList());
            listProduct.setAll(result);
        }
        else {
            result = products.stream().filter(x -> "CONSTRUCTION".equals(x.getType().toString())).collect(Collectors.toList());
            listProduct.setAll(result);
        }
    }

    public void actionWarehouseChosen(ActionEvent actionEvent){
        List<Product> products = model.getProducts();
        List<Product> result = new ArrayList<>();

        if (choiceWarehouse.getValue().toString().matches("All")) {
            listProduct.setAll(products);
        }
        else {
            result = products.stream().filter(x -> choiceWarehouse.getValue().toString().equals(x.getWarehouse().getName())).collect(Collectors.toList());
            listProduct.setAll(result);
        }
    }

    public void clickExit(ActionEvent actionEvent) {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

}

package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.dto.Product;
import sample.models.ProductModel;

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
    private ObservableList<Product> listProduct;

    public MainController() {
        model = ProductModel.getInstance();
        listProduct = FXCollections.observableArrayList(model.getProducts());
    }


}

package sample.models;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class ProductModel {
    private static ProductModel instance;
    private Connection conn;

    private PreparedStatement product, getProducts, getProduct, updateProduct, deleteProduct, addProduct, getID;

    public static ProductModel getInstance() {
        if (instance == null) instance = new ProductModel();
        return instance;
    }

    private ProductModel() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            product = conn.prepareStatement("SELECT * FROM product, warehouse WHERE product.warehouse = warehouse.id");
        } catch (SQLException throwables) {
            regenerateDatabase();
            try {
                product = conn.prepareStatement("SELECT * FROM product, warehouse WHERE product.warehouse = warehouse.id");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {

            getProducts = conn.prepareStatement("SELECT * FROM product");
            getProduct = conn.prepareStatement("SELECT * FROM product WHERE id=?");
            updateProduct = conn.prepareStatement("UPDATE product SET name=?, type=?, amount=?, unitPrice=?, " +
                    "price=?, warehouse=?, dateAdded=?, expirationDate=? WHERE id=?");
            deleteProduct = conn.prepareStatement("DELETE FROM product WHERE id=?");
            addProduct = conn.prepareStatement("INSERT INTO product VALUES(?,?,?,?,?,?,?,?,?)");
            getID = conn.prepareStatement("SELECT MAX(id)+1 FROM product");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void regenerateDatabase() {
        Scanner in = null;
        try {
            in = new Scanner(new FileInputStream("database.db.sql"));
            String sqlUpit = "";
            while (in.hasNext()) {
                sqlUpit += in.nextLine();
                if ( sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }




}

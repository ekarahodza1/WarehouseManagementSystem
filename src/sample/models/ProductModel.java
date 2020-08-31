package sample.models;

import sample.dto.Product;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
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


    public void addProduct(Product product){
        try {
            ResultSet rs = getID.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            addProduct.setInt(1, id);
            addProduct.setString(2, product.getName());
            addProduct.setInt(3, product.getIntType());
            addProduct.setInt(4, product.getAmount());
            addProduct.setDouble(5, product.getUnitPrice());
            addProduct.setDouble(6, product.getPrice());
            addProduct.setInt(7, product.getWarehouse().getId());
            addProduct.setString(8, product.getDateAddedString());
            addProduct.setString(9, product.getExpirationDateString());

            addProduct.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateProduct(Product product) {
        try {
            ResultSet rs = getID.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            updateProduct.setInt(1, id);
            updateProduct.setString(2, product.getName());
            updateProduct.setInt(3, product.getIntType());
            updateProduct.setInt(4, product.getAmount());
            updateProduct.setDouble(5, product.getUnitPrice());
            updateProduct.setDouble(6, product.getPrice());
            updateProduct.setInt(7, product.getWarehouse().getId());
            updateProduct.setString(8, product.getDateAddedString());
            updateProduct.setString(9, product.getExpirationDateString());

            updateProduct.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void deleteProduct(Product product) {
        try {
            deleteProduct.setInt(1, product.getId());
            deleteProduct.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Product getProduct(int id){
        try {
            getProduct.setInt(1, id);
            ResultSet rs = getProduct.executeQuery();
            if (!rs.next()) return null;
            return getProductFromResultset(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Product getProductFromResultset(ResultSet rs) {
        try {
            Date expirationDate = null;
            if (rs.getString(9) != null) {
                expirationDate = Date.valueOf(rs.getString(9));
            }

            Date date = null;
            if (rs.getString(8) != null) {
                date = Date.valueOf(rs.getString(8));
            }

            Product p = new Product(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4),
                    rs.getDouble(5), rs.getDouble(6), rs.getInt(7), date, expirationDate);

            return p;
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Product> getProducts() {
        ArrayList<Product> result = new ArrayList();
        try {
            ResultSet rs = getProducts.executeQuery();
            while (rs.next()) {

                Product product = getProductFromResultset(rs);
                result.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


}

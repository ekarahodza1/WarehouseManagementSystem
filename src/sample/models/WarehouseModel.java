package sample.models;

import sample.dto.Product;
import sample.dto.Warehouse;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class WarehouseModel {
    private static WarehouseModel instance;
    private Connection conn;

    private PreparedStatement product, getWarehouse, addWarehouse, getID;;

    public static WarehouseModel getInstance() {
        if (instance == null) instance = new WarehouseModel();
        return instance;
    }

    private WarehouseModel() {
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

            getWarehouse = conn.prepareStatement("SELECT * FROM warehouse WHERE id=?");
            addWarehouse = conn.prepareStatement("INSERT INTO warehouse VALUES(?,?)");
            getID = conn.prepareStatement("SELECT MAX(id)+1 FROM warehouse");

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

    public void addWarehouse(Warehouse warehouse){
        try {
            ResultSet rs = getID.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            addWarehouse.setInt(1, id);
            addWarehouse.setString(2, warehouse.getName());

            addWarehouse.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Warehouse getWarehouse(int id){
        try {
            getWarehouse.setInt(1, id);
            ResultSet rs = getWarehouse.executeQuery();
            if (!rs.next()) return null;
            Warehouse warehouse = new Warehouse(rs.getInt(1), rs.getString(2));
            return warehouse;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Warehouse> getAll() {
        return null;
    }
}

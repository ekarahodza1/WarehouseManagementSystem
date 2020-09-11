package sample.models;

import org.junit.jupiter.api.Test;
import sample.dto.Product;
import sample.dto.Warehouse;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseModelTest {

    @Test
    void addWarehouse() {
        WarehouseModel.removeInstance();
        File dbfile = new File("database.db");
        dbfile.delete();
        WarehouseModel warehouseModel = WarehouseModel.getInstance();

        Warehouse w = new Warehouse();
        w.setName("A2");
        w.setLocation("Travnik");

        warehouseModel.addWarehouse(w);

        ArrayList<Warehouse> list = warehouseModel.getAll();
        assertEquals(11, list.size());
    }

    @Test
    void getWarehouse() {
        WarehouseModel.removeInstance();
        File dbfile = new File("database.db");
        dbfile.delete();
        WarehouseModel warehouseModel = WarehouseModel.getInstance();

        Warehouse w = warehouseModel.getWarehouse(1);
        assertEquals("A1", w.getName());
    }

    @Test
    void getAll() {
        WarehouseModel.removeInstance();
        File dbfile = new File("database.db");
        dbfile.delete();
        WarehouseModel warehouseModel = WarehouseModel.getInstance();
        ArrayList<Warehouse> list = warehouseModel.getAll();

        assertEquals(10, list.size());
    }
}
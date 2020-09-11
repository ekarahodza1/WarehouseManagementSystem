package sample.models;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import sample.dto.Product;
import sample.dto.Type;
import sample.dto.Warehouse;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ProductModelTest {

    @Test
    void regenerateFile() {
        ProductModel.removeInstance();
        File dbfile = new File("database.db");
        dbfile.delete();
        ProductModel productModel = ProductModel.getInstance();
        ArrayList<Product> products = productModel.getProducts();
        assertEquals("Zara pants", products.get(0).getName());
        assertEquals("Oranges", products.get(1).getName());
    }


    @Test
    void addProduct() {

        ProductModel.removeInstance();
        File dbfile = new File("database.db");
        dbfile.delete();
        ProductModel productModel = ProductModel.getInstance();

        Product p = new Product();
        p.setName("Shirt");
        p.setAmount(8);
        p.setUnitPrice(20.0);
        p.setPrice(160.0);
        p.setType(Type.CLOTHES);
        Warehouse w = new Warehouse();
        w.setLocation("Sarajevo");
        w.setName("A1");
        w.setId(1);
        p.setWarehouse(w);
        p.setDateAdded(java.sql.Date.valueOf("2020-05-05"));

        productModel.addProduct(p);
        ArrayList<Product> products = productModel.getProducts();
        assertEquals(products.size(), 13);
    }

    @Test
    void updateProduct() {
        ProductModel.removeInstance();
        File dbfile = new File("database.db");
        dbfile.delete();
        ProductModel productModel = ProductModel.getInstance();

        Product p = productModel.getProduct(2);
        p.setName("promjena");

        productModel.updateProduct(p);
        assertEquals(productModel.getProduct(2).getName(), "promjena");
    }

    @Test
    void deleteProduct() {
        ProductModel.removeInstance();
        File dbfile = new File("database.db");
        dbfile.delete();
        ProductModel productModel = ProductModel.getInstance();
        Product p = productModel.getProduct(2);
        productModel.deleteProduct(p);
        assertNull(productModel.getProduct(2));

    }

    @Test
    @Order(1)
    void getProduct() {
        ProductModel.removeInstance();
        File dbfile = new File("database.db");
        dbfile.delete();
        ProductModel productModel = ProductModel.getInstance();
        Product p = productModel.getProduct(4);
        assertNotNull(p);

    }

    @Test
    @Order(2)
    void getProducts() {
        ProductModel.removeInstance();
        File dbfile = new File("database.db");
        dbfile.delete();
        ProductModel productModel = ProductModel.getInstance();
        ArrayList<Product> list = productModel.getProducts();


        assertEquals(12, list.size());
    }
}
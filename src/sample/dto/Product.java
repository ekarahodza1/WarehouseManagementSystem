package sample.dto;

import java.util.Date;

public class Product {
    private int id;
    private String name;
    private Type type;
    private int amount;
    private double unitPrice;
    private double price;
    private Warehouse warehouse;
    private Date dateAdded;

    public Product(int id, String name, Type type, int amount, double unitPrice, double price, Warehouse warehouse, Date dateAdded) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.unitPrice = unitPrice;
        this.price = price;
        this.warehouse = warehouse;
        this.dateAdded = dateAdded;
    }

    public Product() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
}

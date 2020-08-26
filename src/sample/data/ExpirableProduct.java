package sample.data;

import java.util.Date;

public class ExpirableProduct extends Product {
    private Date expirationDate;

    public ExpirableProduct(int id, String name, Type type, int amount, double unitPrice, double price, Warehouse warehouse,
                            Date dateAdded, Date expirationDate) {
        super(id, name, type, amount, unitPrice, price, warehouse, dateAdded);
        this.expirationDate = expirationDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}

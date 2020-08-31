package sample.dto;

import java.util.Date;

public class ExpirableProduct extends Product {
    private Date expirationDate;


    public ExpirableProduct() {}

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}

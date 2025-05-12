package entity;

import java.math.BigDecimal;

public class Invoice {
    private int id;
    private int customerId;
    private String createAt;
    private BigDecimal totalAmount;

    public Invoice() {
    }

    public Invoice(int id, int customerId, String createAt, BigDecimal totalAmount) {
        this.id = id;
        this.customerId = customerId;
        this.createAt = createAt;
        this.totalAmount = totalAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}

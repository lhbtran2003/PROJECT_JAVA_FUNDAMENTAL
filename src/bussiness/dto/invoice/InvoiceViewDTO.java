package bussiness.dto.invoice;

import java.math.BigDecimal;

public class InvoiceViewDTO {
    private int id;
    private String customerName;
    private String createAt;
    private BigDecimal totalAmount;

    public InvoiceViewDTO(int id, String customerName, String createAt, BigDecimal totalAmount) {
        this.id = id;
        this.customerName = customerName;
        this.createAt = createAt;
        this.totalAmount = totalAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

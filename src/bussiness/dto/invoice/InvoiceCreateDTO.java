package bussiness.dto.invoice;

import java.math.BigDecimal;

public class InvoiceCreateDTO {
    private int customerId;
    private BigDecimal totalPrice;

    public InvoiceCreateDTO() {

    }

    public InvoiceCreateDTO(int customerId, BigDecimal totalPrice) {
        this.customerId = customerId;
        this.totalPrice = totalPrice;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalAmount) {
        this.totalPrice = totalAmount;
    }
}

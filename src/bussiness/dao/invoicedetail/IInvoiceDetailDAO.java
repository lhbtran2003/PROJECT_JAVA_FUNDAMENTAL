package bussiness.dao.invoicedetail;

import bussiness.dto.invoicedetail.InvoiceDetailCreateDTO;

public interface IInvoiceDetailDAO {
    void save(InvoiceDetailCreateDTO invoiceDetail);
}

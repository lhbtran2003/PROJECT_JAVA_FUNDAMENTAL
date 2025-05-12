package bussiness.dao.invoicedetail;

import bussiness.dto.InvoiceDetailCreateDTO;
import entity.InvoiceDetail;

public interface IInvoiceDetailDAO {
    void save(InvoiceDetailCreateDTO invoiceDetail);
}

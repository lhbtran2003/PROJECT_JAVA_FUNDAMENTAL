package bussiness.service.invoicedetail;

import bussiness.dto.InvoiceDetailCreateDTO;
import entity.InvoiceDetail;

public interface IInvoiceDetailService {
    void createInvoiceDetail(InvoiceDetailCreateDTO invoiceDetail);
}

package bussiness.service.invoice;

import bussiness.dto.invoice.InvoiceCreateDTO;
import bussiness.dto.invoicedetail.InvoiceDetailCreateDTO;
import bussiness.dto.invoice.InvoiceViewDTO;
import bussiness.service.IBasicService;
import entity.Invoice;

import java.time.LocalDate;
import java.util.List;

public interface IInvoiceService extends IBasicService<Invoice> {
    void createInvoice(InvoiceCreateDTO invoice, List<InvoiceDetailCreateDTO> list);
    List<InvoiceViewDTO> findByCustomerName(String customerName);
    List<InvoiceViewDTO> findByDate(LocalDate date);
}

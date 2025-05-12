package bussiness.service.invoice;

import bussiness.dto.InvoiceCreateDTO;
import bussiness.dto.InvoiceDetailCreateDTO;
import bussiness.dto.InvoiceViewDTO;
import bussiness.service.IBasicService;
import entity.Invoice;

import java.time.LocalDate;
import java.util.List;

public interface IInvoiceService extends IBasicService<Invoice> {
    void createInvoice(InvoiceCreateDTO invoice, List<InvoiceDetailCreateDTO> list);
    List<InvoiceViewDTO> findByCustomerName(String customerName);
    List<InvoiceViewDTO> findByDate(String date);
}

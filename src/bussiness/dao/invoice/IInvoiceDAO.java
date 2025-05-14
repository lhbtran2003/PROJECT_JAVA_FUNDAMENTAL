package bussiness.dao.invoice;

import bussiness.dao.IBasicDAO;
import bussiness.dto.invoice.InvoiceCreateDTO;
import bussiness.dto.invoice.InvoiceViewDTO;
import entity.Invoice;

import java.time.LocalDate;
import java.util.List;

public interface IInvoiceDAO extends IBasicDAO<Invoice> {
    int saveInvoice(InvoiceCreateDTO invoice);
    List<InvoiceViewDTO> getInvoiceByCustomerName(String customerName);
    List<InvoiceViewDTO> getInvoiceByDate(LocalDate date);
    List<InvoiceViewDTO> executeQueryWithParams(String sql, Object[] params);

}

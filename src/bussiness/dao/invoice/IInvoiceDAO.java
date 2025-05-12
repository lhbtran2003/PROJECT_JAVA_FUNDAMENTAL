package bussiness.dao.invoice;

import bussiness.dao.IBasicDAO;
import bussiness.dto.InvoiceCreateDTO;
import bussiness.dto.InvoiceViewDTO;
import entity.Invoice;

import java.time.LocalDate;
import java.util.List;

public interface IInvoiceDAO extends IBasicDAO<Invoice> {
    int saveInvoice(InvoiceCreateDTO invoice);
    List<InvoiceViewDTO> getInvoiceByCustomerName(String customerName);
    List<InvoiceViewDTO> getInvoiceByDate(LocalDate date);
    List<InvoiceViewDTO> executeQueryWithParams(String sql, Object[] params);

}

package bussiness.service.invoice;

import bussiness.dao.invoice.InvoiceDAOImp;
import bussiness.dao.invoicedetail.InvoiceDetailDAOImp;
import bussiness.dao.product.ProductDAOImp;
import bussiness.dto.invoice.InvoiceCreateDTO;
import bussiness.dto.invoicedetail.InvoiceDetailCreateDTO;
import bussiness.dto.invoice.InvoiceViewDTO;
import entity.Invoice;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class InvoiceServiceImp implements IInvoiceService {
    private final InvoiceDAOImp invoiceDAOImp;
    private static InvoiceServiceImp invoiceServiceImp;
    private final InvoiceDetailDAOImp invoiceDetailDAOImp;
    private final ProductDAOImp productDAOImp;

    public InvoiceServiceImp() {
        invoiceDAOImp = InvoiceDAOImp.getInstance();
        invoiceDetailDAOImp = InvoiceDetailDAOImp.getInstance();
        productDAOImp = ProductDAOImp.getInstance();
    }

    public static InvoiceServiceImp getInstance() {
        if (invoiceServiceImp == null) {
            invoiceServiceImp = new InvoiceServiceImp();
        }
        return invoiceServiceImp;
    }

    @Override
    public void createInvoice(InvoiceCreateDTO invoice, List<InvoiceDetailCreateDTO> detailList) {
        int invoiceId = invoiceDAOImp.saveInvoice(invoice);
        for (InvoiceDetailCreateDTO detail : detailList) {
            detail.setInvoiceId(invoiceId); // bổ sung invoice_id cho từng chi tiết
            invoiceDetailDAOImp.save(detail);  // lưu chi tiết

            // Trừ số lượng tồn kho
            productDAOImp.updateStock(detail.getProductId(), detail.getQuantity());
        }
    }

    @Override
    public List<InvoiceViewDTO> findByCustomerName(String customerName) {
        return invoiceDAOImp.getInvoiceByCustomerName(customerName);
    }

    @Override
    public List<InvoiceViewDTO> findByDate(LocalDate dateInput) {
        return invoiceDAOImp.getInvoiceByDate(dateInput);
    }

    @Override
    public void addMultiple(List<Invoice> list) {

    }

    @Override
    public List<Invoice> getAll() {
        return invoiceDAOImp.findAll();
    }
}

package bussiness.service.invoicedetail;

import bussiness.dao.invoicedetail.InvoiceDetailDAOImp;
import bussiness.dto.invoicedetail.InvoiceDetailCreateDTO;

public class InvoiceDetailServiceImp implements IInvoiceDetailService {
    private final InvoiceDetailDAOImp invoiceDetailDAOImp;
    private static InvoiceDetailServiceImp instance;

    public InvoiceDetailServiceImp() {
        invoiceDetailDAOImp = InvoiceDetailDAOImp.getInstance();
    }

    public static InvoiceDetailServiceImp getInstance() {
        if (instance == null) {
            instance = new InvoiceDetailServiceImp();
        }
        return instance;
    }

    @Override
    public void createInvoiceDetail(InvoiceDetailCreateDTO invoiceDetail) {
        invoiceDetailDAOImp.save(invoiceDetail);
    }
}

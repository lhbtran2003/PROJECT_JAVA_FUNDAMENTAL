package bussiness.dao.invoicedetail;

import bussiness.dto.InvoiceDetailCreateDTO;
import bussiness.service.invoice.InvoiceServiceImp;
import config.DBConnection;
import entity.InvoiceDetail;

import java.security.PublicKey;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class InvoiceDetailDAOImp implements IInvoiceDetailDAO{
    private final String INSERT = "{CALL addInvoiceDetail(?,?,?,?)}";
    private static InvoiceDetailDAOImp instance;

    public static InvoiceDetailDAOImp getInstance() {
        if (instance == null) {
            instance = new InvoiceDetailDAOImp();
        }
        return instance;
    }

    @Override
    public void save(InvoiceDetailCreateDTO invoiceDetail) {
       Connection conn = DBConnection.getConnection();
        try {
            CallableStatement call = conn.prepareCall(INSERT);
            call.setInt(1, invoiceDetail.getInvoiceId());
            call.setInt(2, invoiceDetail.getProductId());
            call.setInt(3, invoiceDetail.getQuantity());
            call.setBigDecimal(4, invoiceDetail.getUnitPrice());
            call.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnection.closeConnection(conn);
        }
    }
}

package bussiness.dao.invoice;

import bussiness.dto.InvoiceCreateDTO;
import bussiness.dto.InvoiceViewDTO;
import config.DBConnection;
import entity.Invoice;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAOImp implements IInvoiceDAO{
    private final String INSERT = "{CALL addInvoice(?,?,?)}";
    private final String FINDALL = "{CALL getAllInvoice()}";
    private final String SEARCHBYCUSTOMERNAME = "{CALL searchInvoiceByCustomerName(?)}";
    private final String SEARCHBYDATE = "{CALL searchInvoiceByDate(?)}";
    private static InvoiceDAOImp instance;

    public static InvoiceDAOImp getInstance() {
        if (instance == null) {
            instance = new InvoiceDAOImp();
        }
        return instance;
    }

    private InvoiceViewDTO mapInvoice(ResultSet rs) throws SQLException {
        return new InvoiceViewDTO(
                rs.getInt("i.id"),
                rs.getString("c.name"),
                rs.getString("i.create_at"),
                rs.getBigDecimal("i.total_amout")
        );
    }

    @Override
    public List<InvoiceViewDTO> executeQueryWithParams(String sql, Object... params) {
        List<InvoiceViewDTO> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             CallableStatement cstmt = con.prepareCall(sql)) {
            for (int i = 0; i < params.length; i++) {
                cstmt.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = cstmt.executeQuery()) {
                while (rs.next()) {
                    InvoiceViewDTO invoice = mapInvoice(rs);
                    list.add(invoice);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<InvoiceViewDTO> getInvoiceByCustomerName(String customerName) {
        return executeQueryWithParams(SEARCHBYCUSTOMERNAME, customerName);
    }

    @Override
    public List<InvoiceViewDTO> getInvoiceByDate(LocalDate date) {
        return executeQueryWithParams(SEARCHBYDATE,date);
    }

    @Override
    public List<Invoice> findAll() {
        List<Invoice> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             CallableStatement cstmt = con.prepareCall(FINDALL);
             ResultSet rs = cstmt.executeQuery()) {
            while (rs.next()) {
                Invoice invoice = new Invoice(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getString("create_at"),
                        rs.getBigDecimal("total_amout")
                );
                list.add(invoice);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public int saveInvoice(InvoiceCreateDTO invoice) {
        Connection conn = DBConnection.getConnection();
        int invoiceId = -1;
        try {
            CallableStatement call = conn.prepareCall(INSERT);
            call.setInt(1, invoice.getCustomerId());
            call.setBigDecimal(2, invoice.getTotalPrice());

            call.executeUpdate();
            invoiceId = call.getInt(3);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnection.closeConnection(conn);
        }
        return invoiceId;
    }
}

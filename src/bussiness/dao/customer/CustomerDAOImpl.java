package bussiness.dao.customer;

import config.DBConnection;
import entity.Customer;
import entity.Product;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements ICustomerDAO{
    private final String FINDALL = "{CALL getAllCustomer()}";
    private final String INSERT = "{CALL AddCustomer(?, ?, ?, ?)}";
    private final String UPDATE = "{CALL updateCustomerById(?, ?, ?, ?, ?)}";
    private final String DELETE = "{CALL deleteCustomerById(?)}";
    private final String FINDBYID = "{CALL getCustomerById(?)}";
    private static CustomerDAOImpl instance;

    public static CustomerDAOImpl getInstance() {
        if (instance == null) {
            instance = new CustomerDAOImpl();
        }
        return instance;
    }

    private Customer mapCustomer(ResultSet rs) throws SQLException {
        return new Customer(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("phone"),
                rs.getString("email"),
                rs.getString("address")
        );
    }

    @Override
    public List<Customer> executeQueryWithParams(String sql, Object... params) {
        List<Customer> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             CallableStatement cstmt = con.prepareCall(sql)) {
            for (int i = 0; i < params.length; i++) {
                cstmt.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = cstmt.executeQuery()) {
                while (rs.next()) {
                    Customer customer = mapCustomer(rs);
                    list.add(customer);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             CallableStatement cstmt = con.prepareCall(FINDALL);
             ResultSet rs = cstmt.executeQuery()) {
            while (rs.next()) {
                Customer customer = mapCustomer(rs);
                list.add(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    @Override
    public void save(Customer customer) {
        Connection con = DBConnection.getConnection();
        CallableStatement call = null;
        try {
            if (findById(customer.getId()) == null) {
                call = con.prepareCall(INSERT);
                call.setString(1, customer.getName());
                call.setString(2, customer.getPhone());
                call.setString(3, customer.getEmail());
                call.setString(4, customer.getAddress());
                call.executeUpdate();
            } else {
                call = con.prepareCall(UPDATE);
                call.setInt(1, customer.getId());
                call.setString(2, customer.getName());
                call.setString(3, customer.getPhone());
                call.setString(4, customer.getEmail());
                call.setString(5, customer.getAddress());
                call.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnection.closeConnection(con);
        }

    }

    @Override
    public void deleteById(Integer id) {
        Connection con = DBConnection.getConnection();
        try {
            CallableStatement call = con.prepareCall(DELETE);
            call.setInt(1, id);
            call.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Customer findById(Integer id) {
      List<Customer> list = executeQueryWithParams(FINDBYID, id);
      if (list.isEmpty()) {
          return null;
      }
      return list.getFirst();
    }
}

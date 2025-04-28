package bussiness.dao.product;

import config.DBConnection;
import entity.Product;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImp implements IProductDAO {
    private final String INSERT = "{CALL AddProduct(?, ?, ?, ?)}";
    private final String FINDALL = "{CALL getAllProduct()}";
    private final String SEARCHBYBRAND = "{CALL searchProductByBrand(?)}";
    private final String FINDBYID = "{CALL getProductById(?)}";
    private final String UPDATE = "{CALL updateProductById(?, ?, ?, ?, ?)}";
    private final String DELETE = "{CALL deleteProductById(?)}";
    private final String SEARCHBYPRICE = "{CALL searchProductByPrice(?, ?)}";
    private final String SEARCHBYSTOCK = "{CALL searchProductByStock(?, ?)}";
    private static ProductDAOImp instance;

    public static ProductDAOImp getInstance() {
        if (instance == null) {
            instance = new ProductDAOImp();
        }
        return instance;
    }

    private Product mapProduct(ResultSet rs) throws SQLException {
        return new Product(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("brand"),
                rs.getDouble("price"),
                rs.getInt("stock")
        );
    }

    private List<Product> executeQueryWithParams(String sql, Object... params) {
        List<Product> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             CallableStatement cstmt = con.prepareCall(sql)) {
            for (int i = 0; i < params.length; i++) {
                cstmt.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = cstmt.executeQuery()) {
                while (rs.next()) {
                    Product product = mapProduct(rs);
                    list.add(product);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    @Override
    public Product findById(Integer id) {
        List<Product> product = executeQueryWithParams(FINDBYID, id);
        if (product.isEmpty()) {
            return null;
        }
        return product.getFirst();
    }

    @Override
    public List<Product> findAll() {
       List<Product> products = executeQueryWithParams(FINDALL, null);
       return products;
    }


    @Override
    public void save(Product product) {
        Connection con = DBConnection.getConnection();
        CallableStatement call = null;
        try {
            if (findById(product.getId()) == null) {
                call = con.prepareCall(INSERT);
                call.setString(1, product.getName());
                call.setString(2, product.getBrand());
                call.setDouble(3, product.getPrice());
                call.setInt(4, product.getStock());
                call.executeUpdate();
            } else {
                call = con.prepareCall(UPDATE);
                call.setInt(1, product.getId());
                call.setString(2, product.getName());
                call.setString(3, product.getBrand());
                call.setDouble(4, product.getPrice());
                call.setInt(5, product.getStock());
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
    public List<Product> findByBrandName(String brandName) {
      return executeQueryWithParams(SEARCHBYBRAND, brandName);
    }

    @Override
    public List<Product> findByPrice(double minPrice, double maxPrice) {
       return executeQueryWithParams(SEARCHBYPRICE, minPrice, maxPrice);
    }

    @Override
    public List<Product> findByStock(int minStock, int maxStock) {
      return executeQueryWithParams(SEARCHBYSTOCK, minStock, maxStock);
    }
}

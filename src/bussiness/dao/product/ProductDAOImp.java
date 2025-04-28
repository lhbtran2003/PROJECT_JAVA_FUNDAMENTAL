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

    @Override
    public Product findById(Integer id) {
        Connection con = DBConnection.getConnection();

        try {
            CallableStatement cstmt = con.prepareCall(FINDBYID);
            cstmt.setInt(1, id);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                return new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnection.closeConnection(con);
        }
        return null;
    }

    @Override
    public List<Product> findAll() {
        List<Product> list = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        try {
            CallableStatement cstmt = con.prepareCall(FINDALL);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                );
                list.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnection.closeConnection(con);
        }
        return list;
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
        Connection con = DBConnection.getConnection();
        List<Product> list = new ArrayList<>();
        try {
            CallableStatement cstmt = con.prepareCall(SEARCHBYBRAND);
            cstmt.setString(1, brandName);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setBrand(rs.getString("brand"));
                product.setPrice(rs.getDouble("price"));
                product.setStock(rs.getInt("stock"));
                list.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnection.closeConnection(con);
        }
        return list;
    }

    @Override
    public List<Product> findByPrice(double minPrice, double maxPrice) {
        List<Product> list = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        try {
            CallableStatement call = con.prepareCall(SEARCHBYPRICE);
            call.setDouble(1, minPrice);
            call.setDouble(2, maxPrice);
            ResultSet rs = call.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                );
                list.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnection.closeConnection(con);
        }
        return list;
    }

    @Override
    public List<Product> findByStock(int minStock, int maxStock) {
        List<Product> list = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        try {
            CallableStatement call = con.prepareCall(SEARCHBYSTOCK);
            call.setInt(1, minStock);
            call.setInt(2, maxStock);
            ResultSet rs = call.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                );
                list.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnection.closeConnection(con);
        }
        return list;
    }
}

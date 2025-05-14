package bussiness.service.product;

import bussiness.dao.product.ProductDAOImp;
import entity.Customer;
import entity.Product;

import java.util.List;

public class ProductServiceImpl implements IProductService {
    private static ProductServiceImpl instance;
    private ProductDAOImp productDAOImp;

    public static ProductServiceImpl getInstance() {
        if (instance == null) {
            instance = new ProductServiceImpl();
        }
        return instance;
    }

    public ProductServiceImpl() {
        productDAOImp = ProductDAOImp.getInstance();
    }

    @Override
    public void addMultiple(List<Product> products) {
        for (Product product : products) {
            productDAOImp.save(product);
        }
    }

    @Override
    public List<Product> getAll() {
        return productDAOImp.findAll();
    }

    @Override
    public Product getById(Integer id) {
        return productDAOImp.findById(id);
    }

    @Override
    public List<Product> getProductsByBrandName(String brandName) {
        return productDAOImp.findByBrandName(brandName);
    }

    @Override
    public void update(Product product) {
        productDAOImp.save(product);
    }

    @Override
    public void deleteById(int id) {
        productDAOImp.deleteById(id);
    }

    @Override
    public List<Product> getProductsByPrice(double minPrice, double maxPrice) {
        return productDAOImp.findByPrice(minPrice, maxPrice);
    }

    @Override
    public List<Product> getProductsByStock(int minStock, int maxStock) {
        return productDAOImp.findByStock(minStock, maxStock);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productDAOImp.findByName(name);
    }

    @Override
    public void updateProductStock(int productId, int newStock) {
        productDAOImp.updateStock(productId, newStock);
    }

    @Override
    public boolean isProductNameExist(String name) {
        List<Product> products = getAll();
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return true; // đã tồn tại spham này trong csdl
            }
        }
        return false;
    }
}

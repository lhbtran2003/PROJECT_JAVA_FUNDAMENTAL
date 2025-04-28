package bussiness.dao.product;

import bussiness.dao.IGenericDAO;
import entity.Product;

import java.util.List;

public interface IProductDAO extends IGenericDAO<Product, Integer> {
    List<Product> findByBrandName(String brandName); // tìm kiếm theo tên hãng
    List<Product> findByPrice(double minPrice, double maxPrice); // tìm kiếm theo khoảng giá
    List<Product> findByStock(int minStock, int maxStock); // tìm kiếm theo khoảng ton kho
}

package bussiness.service.product;

import bussiness.service.IGenericService;
import entity.Product;

import java.util.List;

public interface IProductService extends IGenericService<Product, Integer> {
     List<Product> getProductsByBrandName(String brandName);
     List<Product> getProductsByPrice(double minPrice, double maxPrice);
     List<Product> getProductsByStock(int minStock, int maxStock);
}

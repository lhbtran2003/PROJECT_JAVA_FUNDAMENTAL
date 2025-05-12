package presentation.submenu;

import entity.Customer;
import entity.Product;

import java.util.List;

public class DisplayUIHelper {
    public static void showProductList(List<Product> productList) {
        if (productList.isEmpty()) {
            System.out.println("Không có sản phẩm nào.");
            return;
        }
        System.out.println("--------------------- Danh Sách Sản Phẩm ----------------------");
        System.out.printf("| %-2s | %-20s | %-10s | %-10s | %-5s |\n", "ID", "Name", "Brand", "Price", "Stock");
        System.out.println("---------------------------------------------------------------");

        for (Product product : productList) {
            System.out.printf("| %-2s | %-20s | %-10s | %-10.2f | %-5d |\n",
                    product.getId(),
                    product.getName(),
                    product.getBrand(),
                    product.getPrice(),
                    product.getStock());
        }

        System.out.println("---------------------------------------------------------------");
    }

    public static void showCustomerList(List<Customer> customerList) {
        if (customerList.isEmpty()) {
            System.out.println("😭 Danh sách khách hàng hiện đang trống.");
            return;
        }
        System.out.println("----------------------------- Danh Sách Khách Hàng -----------------------------");
        System.out.printf("| %-5s | %-15s | %-10s | %-20s | %-15s |\n", "ID", "Name", "Phone", "Email", "Address");
        System.out.println("--------------------------------------------------------------------------------");

        for (Customer customer : customerList) {
            System.out.printf("| %-5s | %-15s | %-10s | %-20s | %-15s |\n",
                    customer.getId(),
                    customer.getName(),
                    customer.getPhone(),
                    customer.getEmail(),
                    customer.getAddress());
        }
        System.out.println("--------------------------------------------------------------------------------");
    }
}

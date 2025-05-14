package presentation.submenu;

import bussiness.dto.invoice.InvoiceViewDTO;
import entity.Customer;
import entity.Product;
import static utils.PrintUtils.*;


import java.util.List;

public class DisplayUIHelper {
    public static void showProductList(List<Product> productList) {
        if (productList.isEmpty()) {
            System.out.println(RED_BRIGHT +" ❌ Không có sản phẩm nào.");
            return;
        }
        System.out.println(BLUE_BOLD_BRIGHT + "--------------------------- Danh Sách Sản Phẩm ----------------------------");
        System.out.printf("| %-2s | %-30s | %-15s | %-20s | %-10s |\n", "ID", "Name", "Brand", "Price", "Stock");
        System.out.println("---------------------------------------------------------------------------");

        for (Product product : productList) {
            System.out.printf("| %-2s | %-30s | %-15s | %-20.2f | %-10d |\n",
                    product.getId(),
                    product.getName(),
                    product.getBrand(),
                    product.getPrice(),
                    product.getStock());
        }
        System.out.println("---------------------------------------------------------------------------" + RESET);
    }

    public static void showCustomerList(List<Customer> customerList) {
        if (customerList.isEmpty()) {
            System.out.println(RED_BRIGHT +"😭 Danh sách khách hàng hiện đang trống." + RESET);
            return;
        }
        System.out.println(BLUE_BOLD_BRIGHT +"----------------------------- Danh Sách Khách Hàng -----------------------------");
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
        System.out.println("--------------------------------------------------------------------------------"+ RESET);
    }

    public static void showInvoiceList(List<InvoiceViewDTO> invoices) {
        System.out.println(BLUE_BOLD_BRIGHT+"---------------------DANH SÁCH HÓA ĐƠN PHÙ HỢP -------------------------");
        System.out.printf("| %-5s | %-30s | %-20s | %-25s |\n", "ID", "Tên khách hàng", "Tổng tiền", "Ngày tạo");
        System.out.println("---------------------------------------------------------------------------------------------------------");

        for (InvoiceViewDTO invoice : invoices) {
            System.out.printf("| %-5d | %-30s | %-20.2f | %-25s |\n",
                    invoice.getId(),
                    invoice.getCustomerName(),
                    invoice.getTotalAmount(),
                    invoice.getCreateAt()
            );
        }
        System.out.println("---------------------------------------------------------------------------------------------------------" + RESET);
    }

}

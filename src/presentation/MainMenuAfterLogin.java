package presentation;

import presentation.submenu.CustomerManagementUI;
import presentation.submenu.InvoiceManagementUI;
import presentation.submenu.ProductManagementUI;
import presentation.submenu.RevenueStatisticsUI;

import java.util.Scanner;

public class MainMenuAfterLogin implements IGenericUI {
    private static ProductManagementUI productManagementUI = new ProductManagementUI();
    private static CustomerManagementUI customerManagementUI = new CustomerManagementUI();
    private static InvoiceManagementUI invoiceManagementUI = new InvoiceManagementUI();
    private static RevenueStatisticsUI revenueStatisticsUI = new RevenueStatisticsUI();

    @Override
    public void showMenu() {
        System.out.println("========== MENU CHÍNH ==========");
        System.out.println("1. Quản lí sản phẩm điện thoại");
        System.out.println("2. Quản lí khách hàng");
        System.out.println("3. Quản lí hóa đơn");
        System.out.println("4. Thống kê doanh thu");
        System.out.println("5. Đăng xuất");
        System.out.println("================================");
    }

    public static void runMainMenu() {
        Scanner sc = new Scanner(System.in);
        MainMenuAfterLogin mainMenu = new MainMenuAfterLogin();
        while (true) {
            mainMenu.showMenu();
            System.out.print("Nhập lựa chọn: ");
            byte choice = Byte.parseByte(sc.nextLine());

            switch (choice) {
                case 1:
                    productManagementUI.runPhoneManagement();
                    break;
                case 2:
                    customerManagementUI.runCustomerUI();
                    break;
                case 3:
                    invoiceManagementUI.runInvoiceUI();
                    break;
                case 4:
                    revenueStatisticsUI.runRevenueStatisticsUI();
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ");
                    break;

            }
        }

    }
}

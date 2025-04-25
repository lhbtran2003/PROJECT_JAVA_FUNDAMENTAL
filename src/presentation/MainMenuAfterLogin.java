package presentation;

import java.util.Scanner;

public class MainMenuAfterLogin implements IGenericUI {
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
                    PhoneManagementUI.runPhoneManagement();
                    break;
                case 2:
                    CustomerManagementUI.runCustomerUI();
                    break;
                case 3:
                    InvoiceManagementUI.runInvoiceUI();
                    break;
                case 4:
                    RevenueStatisticsUI.runRevenueStatisticsUI();
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

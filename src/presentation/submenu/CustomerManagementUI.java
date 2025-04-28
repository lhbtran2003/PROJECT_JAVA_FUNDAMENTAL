package presentation.submenu;

import presentation.IGenericUI;
import presentation.MainMenuAfterLogin;

import java.util.Scanner;

public class CustomerManagementUI implements IGenericUI {
    @Override
    public void showMenu() {
        System.out.println("========== QUẢN LÍ KHÁCH HÀNG ==========");
        System.out.println("1. Hiển thị danh sách khách hàng");
        System.out.println("2. Thêm khách hàng moi");
        System.out.println("3. Cập nhat thông tin khách hàng");
        System.out.println("4. Xóa khách hàng theo ID");
        System.out.println("5. Quay lại menu chính");
        System.out.println("=========================================");
    }

    public static void runCustomerUI() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            CustomerManagementUI cust = new CustomerManagementUI();
            cust.showMenu();
            System.out.print("Nhập lựa chọn: ");
            byte choice = Byte.parseByte(sc.nextLine());

            switch (choice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    MainMenuAfterLogin.runMainMenu();
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại");
                     break;
            }
        }
    }
}

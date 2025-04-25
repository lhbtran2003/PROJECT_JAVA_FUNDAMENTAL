package presentation;

import java.util.Scanner;

public class InvoiceManagementUI implements IGenericUI {
    @Override
    public void showMenu() {
        System.out.println("========== QUẢN LÍ HÓA ĐƠN ==========");
        System.out.println("1. Hiển thị danh sách hóa đơn");
        System.out.println("2. Thêm mới hóa đơn");
        System.out.println("3. Tìm kiếm hóa đơn");
        System.out.println("4. Quay lại menu chính");
    }

    public static void showMenuSearching() {
        System.out.println("1. Tìm kiếm theo ten khách hàng");
        System.out.println("2. Tìm kiếm theo ngày/tháng/năm");
        System.out.println("3. Quay lại menu hóa đơn");
    }

    public static void runMenuSearching(Scanner scanner) {
        showMenuSearching();
        System.out.print("===> Nhập lựa chọn tìm kiếm: ");
        byte choice = Byte.parseByte(scanner.nextLine());
        switch (choice) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                runInvoiceUI();
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ");
                break;
        }
    }

    public static void runInvoiceUI() {
        Scanner sc = new Scanner(System.in);
        InvoiceManagementUI invoiceManagementUI = new InvoiceManagementUI();
        while (true) {
            invoiceManagementUI.showMenu();
            System.out.print("Nhập lựa chọn: ");
            byte choice = Byte.parseByte(sc.nextLine());
            switch (choice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    runMenuSearching(sc);
                    break;
                case 4:
                    MainMenuAfterLogin.runMainMenu();
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ");
                    break;
            }
        }
    }
}

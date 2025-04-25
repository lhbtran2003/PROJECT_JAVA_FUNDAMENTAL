package presentation;

import java.util.Scanner;

public class PhoneManagementUI implements IGenericUI {
    @Override
    public void showMenu() {
        System.out.println("========== QUẢN LÍ ĐIỆN THOẠI ==========");
        System.out.println("1. Hiển thị danh sách sản phẩm");
        System.out.println("2. Thêm sản phẩm mới");
        System.out.println("3. Cập nhật thông tin sản phẩm");
        System.out.println("4. Xóa sản phẩm theo ID");
        System.out.println("5. Tìm kiếm theo brand");
        System.out.println("6. Tìm kiếm theo khoảng giá");
        System.out.println("7. Tìm kiếm theo tồn kho");
        System.out.println("8. Quay lại menu chính");
        System.out.println("=========================================");
    }

    public static void runPhoneManagement() {
        Scanner sc = new Scanner(System.in);
        PhoneManagementUI pm = new PhoneManagementUI();
        while (true) {
            pm.showMenu();
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
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    MainMenuAfterLogin.runMainMenu();
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ");
                    break;
            }
        }
    }
}

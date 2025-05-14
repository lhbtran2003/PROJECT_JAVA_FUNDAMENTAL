package presentation;

import presentation.auth.AdminAuthenUI;

import java.util.Scanner;
import static utils.ColorUtils.*;

public class PhoneStoreUI {
    private static void showMenu() {
        System.out.println(WHITE_BOLD_BRIGHT + "==========HỆ THỐNG QUẢN LÍ CỬA HÀNG==========");
        System.out.println("1. Đăng nhập Admin");
        System.out.println("2. Thoát");
        System.out.println("=============================================" + RESET);
    }

    public static void main(String[] args) {
        while (true) {
            showMenu();
            System.out.print(YELLOW_BOLD_BRIGHT + "Nhập lựa chọn: "+ RESET);
            byte choice = Byte.parseByte(new Scanner(System.in).nextLine());

            switch (choice) {
                case 1:
                    if (AdminAuthenUI.adminRegister()) {
                        MainMenuAfterLogin.runMainMenu();
                    }
                    break;
                case 2:
                    return;
                default:
                    System.out.println(RED_BOLD + "🆘 Lựa chọn không có trong Menu của chúng tôi. Vui lòng nhập lại!" + RESET);
                    break;
            }
        }
    }
}

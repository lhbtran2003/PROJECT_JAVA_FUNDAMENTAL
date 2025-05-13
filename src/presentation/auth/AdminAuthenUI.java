package presentation.auth;

import bussiness.dao.auth.AdminAuthenDAO;
import entity.Admin;
import static utils.PrintUtils.*;


import java.util.Scanner;

public class AdminAuthenUI {

    public static boolean adminRegister() {
        Scanner sc = new Scanner(System.in);
        System.out.print(YELLOW_BOLD_BRIGHT + "Nhập username: ");
        AdminAuthenDAO.setUsername(sc.nextLine());
        System.out.print("Nhập mật khẩu: " + RESET);
        AdminAuthenDAO.setPassword(sc.nextLine());

        // xác thực tài khoan ở đây
        Admin admin = AdminAuthenDAO.adminAuthen();
        // nếu đúng thì hiện menu ở đây
        if (admin != null) {
            displayInfoAdmin();
            return true;
        } else {
            System.out.println(RED_BOLD + "🆘 Tên tài khoản hoặc mật khẩu sai. Vui lòng nhap lại" + RESET);
            return false;
        }
    }

    private static void displayInfoAdmin() {
        String username = AdminAuthenDAO.getUsername();
        String password = AdminAuthenDAO.getPassword();

        System.out.println(BLUE_BOLD_BRIGHT +"========== ĐĂNG NHẬP QUẢN TRỊ ==========");
        System.out.println("Tài khoản: " + username);
        System.out.println("Mật khẩu: " + password);
        System.out.println("========================================" + RESET);
    }
}

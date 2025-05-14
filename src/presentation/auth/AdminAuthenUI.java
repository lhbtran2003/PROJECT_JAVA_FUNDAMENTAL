package presentation.auth;

import bussiness.dao.auth.AdminAuthenDAO;
import entity.Admin;
import static validate.InputMethod.*;

import static utils.ColorUtils.*;


import java.util.Scanner;

public class AdminAuthenUI {

    public static boolean adminRegister() {
        Scanner sc = new Scanner(System.in);
        System.out.println(BLUE_BOLD_BRIGHT +"|========================================|");
        System.out.println(BLUE_BOLD_BRIGHT +"|           ĐĂNG NHẬP QUẢN TRỊ           |");
        System.out.println(BLUE_BOLD_BRIGHT +"|========================================|");
        AdminAuthenDAO.setUsername( validateInputNotEmpty(sc, YELLOW_BOLD_BRIGHT + "Nhập username: "));
        AdminAuthenDAO.setPassword(validateInputNotEmpty(sc, YELLOW_BOLD_BRIGHT + "Nhập mật khẩu: " + RESET));

        // xác thực tài khoan ở đây
        Admin admin = AdminAuthenDAO.adminAuthen();

        if (admin != null) {
            System.out.println(GREEN_BOLD_BRIGHT + "✅ Đã đăng nhập thành công"+ RESET);
            return true;
        } else {
            System.out.println(RED_BOLD + "🆘 Tên tài khoản hoặc mật khẩu sai. Vui lòng nhap lại" + RESET);
            return false;
        }
    }

    private static void displayInfoAdmin() {
        String username = AdminAuthenDAO.getUsername();
        String password = AdminAuthenDAO.getPassword();

        System.out.println(BLUE_BOLD_BRIGHT +"|========== ĐĂNG NHẬP QUẢN TRỊ ==========|");
        System.out.println("|Tài khoản: " + username);
        System.out.println("|Mật khẩu: " + password);
        System.out.println("|========================================|" + RESET);
    }
}

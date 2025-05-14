package validate;

import bussiness.service.customer.CustomerServiceImpl;

import java.util.Scanner;

import static utils.ColorUtils.*;


public class CustomerValidator extends InputMethod {
    public static final String ERROR_PHONE_NUMBER = "⚠️ Số điện thoại không hợp lệ. Vui lòng nhập lại.";
    public static final String ERROR_EMAIL = "⚠️ Email không hợp lệ. Vui lòng nhập lại.";
    public static final String ERROR_PHONE_NUMBER_EXISTED = "⚠️ Số điện thoại này đã tồn tại, vui lòng nhập số khác!";
    public static final String ERROR_EMAIL_EXISTED = "⚠️ Email này đã tồn tại, vui lòng nhập email khác!";

    // validate số điện thoại
    public static String validatePhoneNumber(Scanner sc, String title, CustomerServiceImpl customerService) {
        while (true) {
            String input = validateInputNotEmpty(sc, title);

            // Kiểm tra số điện thoại có trùng trong cơ sở dữ liệu không
            if (customerService.isPhoneNumberExist(input)) {
                System.out.println(RED_BOLD_BRIGHT + ERROR_PHONE_NUMBER_EXISTED + RESET);
                continue;
            }

            if (input.matches("^0\\d{9}$")) {
                return input;
            }

            System.out.println(RED_BOLD_BRIGHT + ERROR_PHONE_NUMBER + RESET);
        }
    }


    // validate email
    public static String validateEmail(Scanner sc, String title, CustomerServiceImpl customerService) {
        while (true) {
            String input = validateInputNotEmpty(sc, title);

            if (customerService.isEmailExist(input)) {
                System.out.println(RED_BOLD_BRIGHT + ERROR_EMAIL_EXISTED + RESET);
                continue;
            }

            // Không cho phép bắt đầu/kết thúc bằng . hoặc -.
            // Bắt đầu bằng các ký tự hợp lệ cho tên người dùng
            // Phải có dấu @
            // 	Tên miền chứa ký tự, số, dấu -, dấu .
            // 	Đuôi miền (VD: .com, .vn, .edu) từ 2 ký tự trở lên
            if (input.matches("^(?![.-])[A-Za-z0-9._%+-]+(?<![.-])@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$")) {
                return input;
            }
            System.out.println(RED_BOLD_BRIGHT + ERROR_EMAIL + RESET);
        }
    }
}


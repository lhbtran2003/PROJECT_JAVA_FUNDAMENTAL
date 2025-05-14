package validate;

import java.util.Scanner;
import static utils.PrintUtils.*;


public class InputMethod {
    public static final String ERROR_STRING_EMPTY = "⚠️ Trường này không được để trống. Vui lòng nhập lại.";
    private static final String ERROR_EMAIL = "⚠️ Email không hợp lệ. Vui lòng nhập lại.";
    public static final String ERROR_INTERGER = "⚠️ Vui lòng nhập một số nguyên hợp lệ!";
    public static final String ERROR_INTERGER_POSITIVE = "⚠️ Vui lòng nhập một số nguyên dương lớn hơn hoặc bằng 0";
    public static final String ERROR_DOUBLE = "⚠️ Vui lòng nhập một số thực hợp lệ!";
    public static final String ERROR_DOUBLE_POSTITIVE = "⚠️ Vui lòng nhập một số thực lớn hơn hoặc bằng 0";

    public static String validateInputNotEmpty(Scanner sc, String title) {
        String input;
        while (true) {
            System.out.print(title);
            input = sc.nextLine().trim();
            if (!input.isEmpty()) {
                break;
            }
            System.out.println(RED_BOLD_BRIGHT + ERROR_STRING_EMPTY + RESET);
        }
        return input;
    }


    public static int validateIntInput(Scanner sc, String title) {
        while (true) {
            System.out.print(title);
            String input = sc.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value < 0) {
                    System.out.println(RED_BOLD_BRIGHT + ERROR_INTERGER_POSITIVE + RESET);
                }
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(RED_BOLD_BRIGHT + ERROR_INTERGER + RESET);
            }
        }
    }


    public static double validateDoubleInput (Scanner sc, String title) {
        while (true) {
            System.out.print(title);
            String input = sc.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (value < 0) {
                    System.out.println(RED_BOLD_BRIGHT + ERROR_DOUBLE_POSTITIVE + RESET);
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println(RED_BOLD_BRIGHT + ERROR_DOUBLE + RESET);
            }
        }
    }

    public static boolean inputBoolean (Scanner sc, String title) {
        while (true) {
            System.out.print(title + ": ");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("nam") || input.equalsIgnoreCase("nữ")) {
                return input.equalsIgnoreCase("nam");
            }
        }
    }

    private String validateEmail(Scanner sc) {
        String email;
        while (true) {
            System.out.print("😗 Nhập email: ");
            email = sc.nextLine().trim();
            if (email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                break;
            }
            System.out.println(RED_BOLD_BRIGHT + ERROR_EMAIL + RESET);
        }
        return email;
    }

}

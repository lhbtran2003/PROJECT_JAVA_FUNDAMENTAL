package validate;

import javax.xml.validation.Validator;
import java.util.Scanner;
import static utils.PrintUtils.*;


public class RevenueValidator extends InputMethod {
    public static final String ERROR_MONTH = "⚠️ Tháng phải là số từ 1 đến 12. Vui lòng nhập lại.";
    public static String validateMonthInput(Scanner sc, String title) {
        while (true) {
            System.out.print(title);
            String input = validateInputNotEmpty(sc, title);

            // Regex cho phép nhập số từ 1 đến 12
            if (input.matches("^(0?[1-9]|1[0-2])$")) {
                return input;
            } else {
                System.out.println(RED_BOLD_BRIGHT + ERROR_MONTH + RESET);
            }
        }
    }

}

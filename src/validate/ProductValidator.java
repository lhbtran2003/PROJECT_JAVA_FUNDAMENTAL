package validate;


import bussiness.service.product.ProductServiceImpl;
import static utils.PrintUtils.*;


import java.util.Scanner;

public class ProductValidator extends InputMethod {
    public static final String ERROR_NAME_EXISTED ="⚠️ Email này đã tồn tại, vui lòng nhập email khác!";

    public static String validateName (Scanner sc, String title, ProductServiceImpl service) {
        while (true) {
            System.out.println(title);
            String input = validateInputNotEmpty(sc, title);

            if (service.isProductNameExist(input)) {
                System.out.println(RED_BOLD_BRIGHT + ERROR_NAME_EXISTED + RED_BOLD_BRIGHT);
                continue;
            }
            return input;
        }
    }

}

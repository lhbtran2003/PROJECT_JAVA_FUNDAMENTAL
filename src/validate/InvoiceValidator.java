package validate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import static utils.ColorUtils.*;


public class InvoiceValidator {
    public static final String ERROR_DATE = "❌ Ngày không đúng định dạng (dd/MM/yyyy hoặc dd-MM-yyyy). Vui lòng thử lại.";

    public static LocalDate validateDateInput(String input) {
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try {
            if (input.contains("/")) {
                return LocalDate.parse(input, formatter1);
            } else {
                return LocalDate.parse(input, formatter2);
            }
        } catch (DateTimeParseException e) {
            System.out.println(RED_BOLD_BRIGHT +ERROR_DATE + RESET);
        }
        return null;
    }


}

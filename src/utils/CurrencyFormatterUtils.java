package utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CurrencyFormatterUtils {
    public static String formatCurrency(BigDecimal amount) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("vi", "VN"));
        symbols.setGroupingSeparator('.');
        DecimalFormat formatter = new DecimalFormat("#,###", symbols);
        return formatter.format(amount) + " ₫";
    }

}

package bussiness.service.revenue;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface IRevenueService {
    BigDecimal getTotalRevenueByDate(String dateInput);
    BigDecimal getTotalRevenueByMonth(int month, int year);
    BigDecimal getTotalRevenueByYear(int year);
}

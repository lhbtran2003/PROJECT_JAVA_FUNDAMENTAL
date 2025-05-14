package bussiness.dao.revenue;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface IRevenueDAO {
    BigDecimal getTotalRevenueByDate(LocalDate date);

    BigDecimal getTotalRevenueByMonth(int month, int year);

    BigDecimal getTotalRevenueByYear(int year);
}

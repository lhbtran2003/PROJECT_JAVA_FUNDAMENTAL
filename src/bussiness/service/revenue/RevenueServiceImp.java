package bussiness.service.revenue;

import bussiness.dao.revenue.RevenueDAOImp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class RevenueServiceImp implements IRevenueService{
    private final RevenueDAOImp revenueDAOImp;
    private static RevenueServiceImp instance;

    public RevenueServiceImp() {
        revenueDAOImp = RevenueDAOImp.getInstance();
    }

    public static RevenueServiceImp getInstance() {
        if (instance == null) {
            instance = new RevenueServiceImp();
        }
        return instance;
    }

    @Override
    public BigDecimal getTotalRevenueByDate(LocalDate dateInput) {
        return revenueDAOImp.getTotalRevenueByDate(dateInput);
    }

    @Override
    public BigDecimal getTotalRevenueByMonth(int month, int year) {
        return revenueDAOImp.getTotalRevenueByMonth(month, year);
    }

    @Override
    public BigDecimal getTotalRevenueByYear(int year) {
        return revenueDAOImp.getTotalRevenueByYear(year);
    }
}

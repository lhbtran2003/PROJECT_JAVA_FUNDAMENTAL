package bussiness.dao.revenue;

import config.DBConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.Year;

public class RevenueDAOImp implements IRevenueDAO{
    private final String GETBYDATE = "{CALL getTotalReVenueByDate(?, ?)}";
    private final String GETBYMONTH = "{CALL getTotalReVenueByMonth(?, ?, ?)}";
    private final String GETBYYEAR = "{CALL getTotalReVenueByYear(?, ?, ?)}";
    private static RevenueDAOImp instance;

    public static RevenueDAOImp getInstance() {
        if (instance == null) {
            instance = new RevenueDAOImp();
        }
        return instance;
    }

    @Override
    public BigDecimal getTotalRevenueByYear(int year) {

        Connection conn = DBConnection.getConnection();
        try {
            CallableStatement call = conn.prepareCall(GETBYMONTH);
            call.setInt(1, year);
            call.registerOutParameter(2, Types.DECIMAL);
            call.execute();

            // lấy gia trị đầu ra
            return call.getBigDecimal(2); // csdl đã trả về 0 nếu ko có kết quả

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BigDecimal getTotalRevenueByMonth(int month, int year) {
        Connection conn = DBConnection.getConnection();
        try {
            CallableStatement call = conn.prepareCall(GETBYMONTH);
            call.setInt(1, month);
            call.setInt(2, year);
            call.registerOutParameter(3, Types.DECIMAL);
            call.execute();

            // lấy gia trị đầu ra
            BigDecimal revenue = call.getBigDecimal(3);
            return revenue != null ? revenue : BigDecimal.ZERO; // nếu ko có thì trả về 0

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BigDecimal getTotalRevenueByDate(LocalDate date) {
        Connection conn = DBConnection.getConnection();
        try {
            CallableStatement call = conn.prepareCall(GETBYDATE);
            call.setDate(1, Date.valueOf(date));
            call.registerOutParameter(2, Types.DECIMAL);
            call.execute();

            // lấy gia trị đầu ra
            BigDecimal revenue = call.getBigDecimal(2);
            return revenue != null ? revenue : BigDecimal.ZERO; // nếu ko có thì trả về 0

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

package presentation.submenu;

import bussiness.service.revenue.RevenueServiceImp;
import presentation.IGenericUI;
import presentation.MainMenuAfterLogin;
import validate.InputMethod;

import static utils.ColorUtils.*;
import static utils.CurrencyFormatterUtils.*;
import static validate.InvoiceValidator.*;
import static validate.RevenueValidator.*;
import static validate.InputMethod.*;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class RevenueStatisticsUI implements IGenericUI {
    private final RevenueServiceImp revenueServiceImp;

    public RevenueStatisticsUI() {
        revenueServiceImp = RevenueServiceImp.getInstance();
    }
    @Override
    public void showMenu() {
        System.out.println(WHITE_BOLD_BRIGHT+"========== THỐNG KÊ DOANH THU ==========");
        System.out.println("| 1. Doanh thu theo ngày                |");
        System.out.println("| 2. Doanh thu theo tháng               |");
        System.out.println("| 3. Doanh thu theo năm                 |");
        System.out.println("| 4. Quay lại menu chính                |");
        System.out.println("=========================================" + RESET);
    }

    public void runRevenueStatisticsUI() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            showMenu();
            System.out.print(YELLOW_BOLD_BRIGHT + "❔ Nhập lựa chọn: " + RESET);
            byte choice = Byte.parseByte(sc.nextLine());

            switch (choice) {
                case 1:
                    getTotalByDateInPresentation(sc);
                    break;
                case 2:
                    getTotalByMonthInPresentation(sc);
                    break;
                case 3:
                    getTotalByYearInPresentation(sc);
                    break;
                case 4:
                    MainMenuAfterLogin.runMainMenu();
                    break;
                default:
                    System.out.println(RED_BOLD_BRIGHT + "🆘 Lựa chọn không hợp lệ. Vui lòng nhập lại" + RESET);
                    break;
            }
            pressAndKey();
        }
    }


    private void getTotalByDateInPresentation(Scanner sc) {
        LocalDate date = null;
        while (date == null) {
            System.out.print(YELLOW_BOLD_BRIGHT + "❔ Nhập vào ngày tháng năm đầy đủ nha: " + RESET);
            String dateInput = sc.nextLine();
            date = validateDateInput(dateInput);
        }

        BigDecimal totalRevenue = revenueServiceImp.getTotalRevenueByDate(date);

        if (totalRevenue.compareTo(BigDecimal.ZERO) == 0) {
            System.out.println(RED_BOLD + "❌ Không có doanh thu trong ngày này hoặc ngày không hợp lệ."+ RESET);
        } else {
            String formattedCurrency = formatCurrency(totalRevenue);
            System.out.printf(BLUE_BOLD_BRIGHT + "Tổng doanh thu ngày %s là: %s \n", date, formattedCurrency + RESET);
        }

    }

    private void getTotalByMonthInPresentation (Scanner sc) {
        String monthInput = validateMonthInput(sc, YELLOW_BOLD_BRIGHT + "❔ Nhập tháng cần tra cứu: ");
        int month = Integer.parseInt(monthInput);
        int year = validateIntInput(sc, "❔ Nhập năm cần tra cứu: " + RESET);

        BigDecimal totalVenue = revenueServiceImp.getTotalRevenueByMonth(month, year);
        if (totalVenue.compareTo(BigDecimal.ZERO) == 0) {
            System.out.println(RED_BOLD + "❌ Không có doanh thu trong tháng này hoặc tháng không hợp lệ." + RESET);
        } else {
            String formattedCurrency = formatCurrency(totalVenue);
            System.out.printf(BLUE_BOLD_BRIGHT + "Tổng doanh thu của tháng %s/%d là %s\n", monthInput, year, formattedCurrency + RESET);
        }
    }


    private void getTotalByYearInPresentation (Scanner sc) {
        int yearInput = validateIntInput(sc, YELLOW_BOLD_BRIGHT + "❔Nhập năm cần tra cứu: " + RESET);

        BigDecimal totalVenue = revenueServiceImp.getTotalRevenueByYear(yearInput);

        if (totalVenue.compareTo(BigDecimal.ZERO) == 0) {
            System.out.println(RED_BOLD + "❌ Không có doanh thu trong năm này hoặc năm không hợp lệ." + RESET);
        } else {
            String formattedCurrency = formatCurrency(totalVenue);
            System.out.printf(BLUE_BOLD_BRIGHT + "Tổng doanh thu của năm %d là %s\n", yearInput, formattedCurrency + RESET);
        }
    }
}


package presentation.submenu;

import bussiness.service.revenue.RevenueServiceImp;
import presentation.IGenericUI;
import presentation.MainMenuAfterLogin;
import static utils.PrintUtils.*;


import java.math.BigDecimal;
import java.util.Scanner;

public class RevenueStatisticsUI implements IGenericUI {
    private final RevenueServiceImp revenueServiceImp;

    public RevenueStatisticsUI() {
        revenueServiceImp = RevenueServiceImp.getInstance();
    }
    @Override
    public void showMenu() {
        System.out.println(WHITE_BOLD_BRIGHT +"========== THỐNG KÊ DOANH THU ==========");
        System.out.println("1. Doanh thu theo ngày");
        System.out.println("2. Doanh thu theo tháng");
        System.out.println("3. Doanh thu theo năm");
        System.out.println("4. Quay lại menu chính" + RESET);
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
        }
    }


    private void getTotalByDateInPresentation(Scanner sc) {
        System.out.print(YELLOW_BOLD_BRIGHT + "❔ Nhập vào ngày tháng năm đầy đủ nha: " + RESET);
        String dateInput =sc.nextLine();

        BigDecimal totalRevenue = revenueServiceImp.getTotalRevenueByDate(dateInput);

        if (totalRevenue.compareTo(BigDecimal.ZERO) == 0) {
            System.out.println(RED_BOLD + "❌ Không có doanh thu trong ngày này hoặc ngày không hợp lệ."+ RESET);
        } else {
            System.out.printf(BLUE_BOLD_BRIGHT + "Tổng doanh thu ngày %s là: %,.2f VNĐ\n", dateInput, totalRevenue + RESET);
        }

    }

    private void getTotalByMonthInPresentation (Scanner sc) {
        System.out.print(YELLOW_BOLD_BRIGHT + "❔ Nhập tháng cần tra cứu: ");
        String monthInput = sc.nextLine();
        int month = Integer.parseInt(monthInput);

        System.out.print("❔ Nhập năm cần tra cứu: "+RESET);
        String yearInput = sc.nextLine();
        int year = Integer.parseInt(yearInput);

        BigDecimal totalVenue = revenueServiceImp.getTotalRevenueByMonth(month, year);

        System.out.printf(BLUE_BOLD_BRIGHT + "Tổng doanh thu của tháng %s/%s là %,.2f VNĐ", monthInput, yearInput, totalVenue + RESET);
    }


    private void getTotalByYearInPresentation (Scanner sc) {
        System.out.print(YELLOW_BACKGROUND +"❔Nhập năm cần tra cứu: " + RESET);
        String yearInput = sc.nextLine();
        int year = Integer.parseInt(yearInput);

        BigDecimal totalVenue = revenueServiceImp.getTotalRevenueByYear(year);

        System.out.printf(BLUE_BOLD_BRIGHT + "Tổng doanh thu của năm %s là %,.2f VNĐ", yearInput, totalVenue + RESET);
    }
}

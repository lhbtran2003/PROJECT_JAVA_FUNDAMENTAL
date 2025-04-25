package presentation;

import java.util.Scanner;

public class RevenueStatisticsUI implements IGenericUI {
    @Override
    public void showMenu() {
        System.out.println("========== THỐNG KÊ DOANH THU ==========");
        System.out.println("1. Doanh thu theo ngày");
        System.out.println("2. Doanh thu theo tháng");
        System.out.println("3. Doanh thu theo năm");
        System.out.println("4. Quay lại menu chính");
    }

    public static void runRevenueStatisticsUI() {
        Scanner sc = new Scanner(System.in);
        RevenueStatisticsUI revenueStatisticsUI = new RevenueStatisticsUI();
        while (true) {
            revenueStatisticsUI.showMenu();
            System.out.print("Nhập lựa chọn: ");
            byte choice = Byte.parseByte(sc.nextLine());

            switch (choice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    MainMenuAfterLogin.runMainMenu();
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ");
                    break;
            }
        }
    }
}

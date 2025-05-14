package presentation.submenu;

import bussiness.service.customer.CustomerServiceImpl;
import entity.Customer;
import presentation.IGenericUI;
import presentation.MainMenuAfterLogin;
import static utils.PrintUtils.*;
import static validate.InputMethod.*;
import static validate.CustomerValidator.*;



import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerManagementUI implements IGenericUI {
    private final CustomerServiceImpl customerServiceImpl;

    public CustomerManagementUI() {
        customerServiceImpl = CustomerServiceImpl.getInstance();
    }

    @Override
    public void showMenu() {
        System.out.println(WHITE_BOLD_BRIGHT +"========== QUẢN LÍ KHÁCH HÀNG ==========");
        System.out.println("1. Hiển thị danh sách khách hàng");
        System.out.println("2. Thêm khách hàng moi");
        System.out.println("3. Cập nhat thông tin khách hàng");
        System.out.println("4. Xóa khách hàng theo ID");
        System.out.println("5. Quay lại menu chính");
        System.out.println("========================================="+RESET);
    }


    public void runCustomerUI() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            showMenu();
            System.out.print(YELLOW_BOLD_BRIGHT+ "❔ Nhập lựa chọn: "+RESET);
            byte choice = Byte.parseByte(sc.nextLine());

            switch (choice) {
                case 1:
                    getAllCustomerInPresentation();
                    break;
                case 2:
                    addMultipleInPresentation(sc);
                    break;
                case 3:
                    updateCustomerInPresentation(sc);
                    break;
                case 4:
                    deleteCustomerInPresentation(sc);
                    break;
                case 5:
                    MainMenuAfterLogin.runMainMenu();
                    break;
                default:
                    System.out.println(RED_BOLD_BRIGHT +"🆘 Lựa chọn không hợp lệ. Vui lòng nhập lại"+ RESET);
                    break;
            }
        }
    }

    // thêm mới khách hàng
    private void addMultipleInPresentation(Scanner sc) {
        int count = validateIntInput(sc, YELLOW_BOLD_BRIGHT+ "❔Nhập số lượng khách hàng bạn muốn thêm: ");

        List<Customer> customers = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Customer customer = new Customer();
            System.out.println(YELLOW_UNDERLINED + "👌 Nhập thông tin khách hàng thứ " + (i + 1) + RESET);

            customer.setName(validateInputNotEmpty(sc, YELLOW_BOLD_BRIGHT +"😊 Nhập tên khách hàng: "));

            customer.setPhone(validatePhoneNumber(sc, "😋 Nhập số điện thoại ", customerServiceImpl));

            customer.setEmail(validateEmail(sc, "😗 Nhập email: ", customerServiceImpl));

            customer.setAddress(validateInputNotEmpty(sc, "🤔 Nhập địa chỉ: " + RESET));

            customers.add(customer);
            System.out.println();
        }
        customerServiceImpl.addMultiple(customers);
    }

    // hiển thị danh sách khách hàng
    private void getAllCustomerInPresentation() {
        List<Customer> customers = customerServiceImpl.getAll();
        DisplayUIHelper.showCustomerList(customers);
    }

    // cập nhật thông tin khách hàng bao gồm: tên, sdt, email, địa chỉ
    private void updateCustomerInPresentation(Scanner sc) {
        int id = validateIntInput(sc, YELLOW_BOLD_BRIGHT +"❔ Nhập id của khách hàng muốn sửa: " +RESET);

        Customer customer = customerServiceImpl.getById(id);
        if (customer == null) {
            System.out.println(RED_BOLD+ "❌ Không tìm thấy khách hàng có id = " + id+RESET);
            return;
        }

        while (true) {
            System.out.println(CYAN_BOLD_BRIGHT + "Các thông tin có thể chỉnh sửa: " + RESET);
            System.out.println(CYAN_BRIGHT +"1🥗 Tên khách hàng");
            System.out.println("2🍗 Số điện thoại");
            System.out.println("3🍜 Email");
            System.out.println("4🍶 Địa chỉ");
            System.out.println("5😎 Đã xong, thoát");
            System.out.println("......................" + RESET);
            System.out.print(YELLOW_BOLD_BRIGHT +"ヽ（≧□≦）ノ Bạn chon cái nào: ");
            byte choice = Byte.parseByte(sc.nextLine());

            switch (choice) {
                case 1:
                    customer.setName(validateInputNotEmpty(sc, YELLOW_BOLD_BRIGHT +"➡️ Nhập tên mới cho khách hàng này: " + RESET));
                    break;
                case 2:
                    // cái này phải chỉnh validate sdt lại
                    customer.setPhone(validatePhoneNumber(sc, YELLOW_BOLD_BRIGHT+"➡️ Nhập số điện thoại mới cho khách hàng này: " + RESET, customerServiceImpl));
                    break;
                case 3:
                    customer.setEmail(validateEmail(sc, YELLOW_BOLD_BRIGHT +"➡️ Nhập email mới cho khách hàng này: "+ RESET, customerServiceImpl));
                    break;
                case 4:
                    customer.setAddress(validateInputNotEmpty(sc, YELLOW_BOLD_BRIGHT+ "➡️ Nhập số địa chỉ mới cho khách hàng này: " + RESET));
                    break;
                case 5:
                    System.out.println(GREEN_BOLD_BRIGHT +"🎉 Đã hoàn thành việc chỉnh sửa khách hàng" + RESET);
                    customerServiceImpl.update(customer);
                    return;
                default:
                    System.out.println(RED_BOLD_BRIGHT +"🆘 Bấm lộn rồi bạn êyyyyy, chọn lại đi nhóe ^^"+ RESET);
                    break;
            }
        }
    }

    // xóa khách hàng theo id
    private void deleteCustomerInPresentation (Scanner sc) {
        int id = validateIntInput(sc, YELLOW_BOLD_BRIGHT +"❔ Nhập id của khách hàng muốn xóa: "+ RESET);

        Customer customer = customerServiceImpl.getById(id);
        if (customer == null) {
            System.out.println(RED_BOLD + "❌ Không tìm thấy khách hàng có id = " + id + RESET);
            return;
        }
        System.out.print(PURPLE_BOLD_BRIGHT +"🔆 Có khách hàng trong danh sách, bạn có muốn xóa không (có = 'y', không = 'n'): " + RESET);
        String answer = sc.nextLine();

        if (answer.equals("y")) {
            customerServiceImpl.deleteById(id);
            System.out.println(GREEN_BOLD_BRIGHT + "✅ Đã xóa khách hàng có id = " + id +RESET);
        } else if (answer.equals("n")) {
            System.out.println(GREEN_BOLD_BRIGHT + "❎ Đã hủy thao tác xoa đối với khách hàng có id = "+id + RESET);
        } else {
            System.out.println(RED_BOLD_BRIGHT +"🆘 Lựa chọn không hợp lệ, vui lòng thực hiện lại thao tác từ đầu."+RESET);
        }
    }
}

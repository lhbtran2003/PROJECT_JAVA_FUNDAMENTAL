package presentation.submenu;

import bussiness.service.customer.CustomerServiceImpl;
import entity.Customer;
import entity.Product;
import presentation.IGenericUI;
import presentation.MainMenuAfterLogin;

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
        System.out.println("========== QUẢN LÍ KHÁCH HÀNG ==========");
        System.out.println("1. Hiển thị danh sách khách hàng");
        System.out.println("2. Thêm khách hàng moi");
        System.out.println("3. Cập nhat thông tin khách hàng");
        System.out.println("4. Xóa khách hàng theo ID");
        System.out.println("5. Quay lại menu chính");
        System.out.println("=========================================");
    }

    public void runCustomerUI() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            showMenu();
            System.out.print("Nhập lựa chọn: ");
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
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại");
                    break;
            }
        }
    }

    private void addMultipleInPresentation(Scanner sc) {
        System.out.print("❔Nhập số lượng khách hàng bạn muốn thêm: ");
        int count = Integer.parseInt(sc.nextLine());

        List<Customer> customers = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Customer customer = new Customer();
            System.out.println("👌 Nhập thông tin khách hàng thứ " + (i + 1));
            System.out.print("😊 Nhập tên khách hàng: ");
            customer.setName(sc.nextLine());
            System.out.print("😋 Nhập số điện thoại ");
            customer.setPhone(sc.nextLine());
            System.out.print("😗 Nhập email: ");
            customer.setEmail(sc.nextLine());
            System.out.print("🤔 Nhập địa chỉ: ");
            customer.setAddress(sc.nextLine());
            customers.add(customer);
            System.out.println();
        }

        customerServiceImpl.addMultiple(customers);
    }

    private void getAllCustomerInPresentation() {
        List<Customer> customers = customerServiceImpl.getAll();

        if (customers.isEmpty()) {
            System.out.println("😭 Danh sách khách hàng hiện đang trống.");
            return;
        }
        System.out.println("========== Danh Sách Sản Phẩm ==========");
        for (Customer customer : customers) {
            System.out.println("ID: " + customer.getId() + ", Name: " + customer.getName() +
                    ", Phone: " + customer.getPhone() + ", Email: " + customer.getEmail() +
                    ", Address: " + customer.getAddress());
        }
        System.out.println("========================================");
    }

    private void updateCustomerInPresentation(Scanner sc) {
        System.out.print("Nhập id của khách hàng muốn sửa: ");
        int id = Integer.parseInt(sc.nextLine());

        Customer customer = customerServiceImpl.getById(id);
        if (customer == null) {
            System.out.println("❌ Không tìm thấy khách hàng có id = " + id);
            return;
        }

        while (true) {
            System.out.println("Các thông tin có thể chỉnh sửa: ");
            System.out.println("1🥗 Tên khách hàng");
            System.out.println("2🍗 Số điện thoại");
            System.out.println("3🍜 Email");
            System.out.println("4🍶 Địa chỉ");
            System.out.println("5😎 Đã xong, thoát");
            System.out.println("......................");
            System.out.print("ヽ（≧□≦）ノ Bạn chon cái nào: ");
            byte choice = Byte.parseByte(sc.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Nhập tên mới cho sản phẩm này: ");
                    customer.setName(sc.nextLine());
                    break;
                case 2:
                    System.out.print("Nhập số điện thoại mới cho khách hàng này: ");
                    customer.setPhone(sc.nextLine());
                    break;
                case 3:
                    System.out.print("Nhập email mới cho khách hàng này: ");
                    customer.setEmail(sc.nextLine());
                    break;
                case 4:
                    System.out.print("Nhập số địa chỉ mới cho khách hàng này: ");
                    customer.setAddress(sc.nextLine());
                    break;
                case 5:
                    System.out.println("🎉 Đã hoàn thành việc chỉnh sửa khách hàng!");
                    customerServiceImpl.update(customer);
                    return;
                default:
                    System.out.println("Bấm lộn rồi bạn êyyyyy, chọn lại đi nhóe ^^");
                    break;
            }
        }
    }

    private void deleteCustomerInPresentation (Scanner sc) {
        System.out.print("Nhập id của sản phẩm muốn xóa: ");
        int id = Integer.parseInt(sc.nextLine());

        Customer customer = customerServiceImpl.getById(id);
        if (customer == null) {
            System.out.println("❌ Không tìm thấy khách hàng có id = " + id);
            return;
        }
        System.out.print("🔆 Có khách hàng trong danh sách, bạn có muốn xóa không (có = 'y', không = 'n'): ");
        String answer = sc.nextLine();

        if (answer.equals("y")) {
            customerServiceImpl.deleteById(id);
            System.out.println("✅ Đã xóa khách hàng có id = " + id);
        } else if (answer.equals("n")) {
            System.out.println("❎ Đã hủy thao tác xoa đối với khách hàng có id = "+id);
        } else {
            System.out.println("😭Lựa chọn không hợp lệ, vui lòng thực hiện lại thao tác từ đầu.");
        }
    }
}

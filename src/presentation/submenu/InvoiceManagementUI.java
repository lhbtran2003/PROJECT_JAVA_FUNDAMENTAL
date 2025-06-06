package presentation.submenu;

import bussiness.dto.invoice.InvoiceCreateDTO;
import bussiness.dto.invoicedetail.InvoiceDetailCreateDTO;
import bussiness.dto.invoice.InvoiceViewDTO;
import bussiness.service.customer.CustomerServiceImpl;
import bussiness.service.invoice.InvoiceServiceImp;
import bussiness.service.product.ProductServiceImpl;
import entity.Customer;
import entity.Invoice;
import entity.Product;
import presentation.IGenericUI;
import presentation.MainMenuAfterLogin;
import static validate.InputMethod.*;
import static validate.InvoiceValidator.*;


import static utils.ColorUtils.*;
import static utils.DateTimeFormatterUtils.*;
import static utils.CurrencyFormatterUtils.*;


import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.*;

public class InvoiceManagementUI implements IGenericUI {
    private final InvoiceServiceImp invoiceService;
    private final CustomerServiceImpl customerService;
    private final ProductServiceImpl productService;

    public InvoiceManagementUI() {
        invoiceService = InvoiceServiceImp.getInstance();
        customerService = CustomerServiceImpl.getInstance();
        productService = ProductServiceImpl.getInstance();
    }

    @Override
    public void showMenu() {
        System.out.println(WHITE_BOLD_BRIGHT +"|========== QUẢN LÍ HÓA ĐƠN ==========|");
        System.out.println("| 1. Hiển thị danh sách hóa đơn       |");
        System.out.println("| 2. Thêm mới hóa đơn                 |");
        System.out.println("| 3. Tìm kiếm hóa đơn                 |");
        System.out.println("| 4. Quay lại menu chính              |" + RESET);
        System.out.println("|=====================================|");
    }

    public void showMenuSearching() {
        System.out.println(WHITE_BOLD_BRIGHT +"|========== MENU TÌM KIẾM HÓA ĐƠN ==========|");
        System.out.println("|1. Tìm kiếm theo ten khách hàng            |");
        System.out.println("|2. Tìm kiếm theo ngày/tháng/năm            |");
        System.out.println("|3. Quay lại menu hóa đơn                   |");
        System.out.println("|===========================================|"+ RESET);
    }

    public void runMenuSearching(Scanner sc) {
        showMenuSearching();
        System.out.print(YELLOW_BOLD_BRIGHT + "❔ Nhập lựa chọn tìm kiếm: " + RESET);
        byte choice = Byte.parseByte(sc.nextLine());
        switch (choice) {
            case 1:
                getInvoiceByCustomerName(sc);
                break;
            case 2:
                getInvoiceByDate(sc);
                break;
            case 3:
                runInvoiceUI();
                break;
            default:
                System.out.println(RED_BOLD_BRIGHT + "🆘 Lựa chọn không hợp lệ. Vui lòng nhập lại." + RESET);
                break;
        }
        pressAndKey();
    }

    public void runInvoiceUI() {
        Scanner sc = new Scanner(System.in);
        InvoiceManagementUI invoiceManagementUI = new InvoiceManagementUI();
        while (true) {
            invoiceManagementUI.showMenu();
            System.out.print(YELLOW_BOLD_BRIGHT + "❔ Nhập lựa chọn: " + RESET);
            byte choice = Byte.parseByte(sc.nextLine());
            switch (choice) {
                case 1:
                    getAllInvoicesInPresentation();
                    break;
                case 2:
                    createInvoiceInPresentation(sc);
                    break;
                case 3:
                    runMenuSearching(sc);
                    break;
                case 4:
                    MainMenuAfterLogin.runMainMenu();
                    break;
                default:
                    System.out.println(RED_BOLD_BRIGHT + "🆘 Lựa chọn không hợp lệ. Vui lòng nhap lại" + RESET);
                    break;
            }
            pressAndKey();

        }
    }

    // hiển thị danh sách tất cả hóa đơn
    private void getAllInvoicesInPresentation() {
        List<Invoice> list = invoiceService.getAll();
        if (list.isEmpty()) {
            System.out.println(RED_BOLD + "🆘 Danh sách hóa đơn hiện trang trống!" + RESET);
            return;
        }


        System.out.println(BLUE_BOLD_BRIGHT + "------------------------ Danh sách hóa đơn --------------------------");
        System.out.printf("| %-2s | %-15s | %-20s | %-20s |\n", "ID", "Tên khách hàng", "Thời gian tạo", "Tổng tiền");
        System.out.println("|-------------------------------------------------------------------|");
        for (Invoice invoice : list) {

            String customerName = customerService.getById(invoice.getCustomerId()).getName();
            String formattedDate = formatDateTime(invoice.getCreateAt());
            String formattedCurrency = formatCurrency(invoice.getTotalAmount());

            System.out.printf("| %-2s | %-15s | %-20s | %-20s |\n", invoice.getId(), customerName, formattedDate, formattedCurrency);
        }
        System.out.println("-----------------------------------------------------------------------" + RESET);
    }


    // tim kiếm khách hàng theo tên, nếu ko có thì tạo mới luôn
    private int handleCustomerInput(Scanner sc) {
        String name = validateInputNotEmpty(sc, YELLOW_BOLD_BRIGHT + "❔ Nhập tên khách hàng: " + RESET);
        List<Customer> customerList = customerService.getCustomerByName(name);

        if (customerList == null || customerList.isEmpty()) {
            System.out.println(RED_BOLD_BRIGHT + "🆘 Khách hàng chưa có trong danh sách, sẽ tiến hành tạo mới." + RESET);
            Customer newCustomer = new Customer();
            newCustomer.setName(name);
            System.out.print(YELLOW_BOLD_BRIGHT + "😋 Nhập số điện thoại: ");
            newCustomer.setPhone(sc.nextLine());
            System.out.print("😗 Nhập email: ");
            newCustomer.setEmail(sc.nextLine());
            newCustomer.setAddress(validateInputNotEmpty(sc, "🤔 Nhập địa chỉ: " + RESET));

            List<Customer> toAdd = new ArrayList<>();
            toAdd.add(newCustomer);
            customerService.addMultiple(toAdd);

            return customerService.getCustomerByName(name).getFirst().getId();
        } else {
            DisplayUIHelper.showCustomerList(customerList);
            return validateIntInput(sc, YELLOW_BOLD_BRIGHT + "❔ Nhập ID khách hàng muốn tạo hóa đơn: " + RESET);
        }
    }


    // tìm kiếm spham theo tên, ko có sẽ hiện cảnh báo ko có
    private Map<Product, Integer> handleProductSelection(Scanner sc) {
        List<Product> productList = productService.getAll();
        DisplayUIHelper.showProductList(productList);

        Map<Product, Integer> selectedProducts = new HashMap<>();
        while (true) {
            int id = validateIntInput(sc, YELLOW_BOLD_BRIGHT + "❔ Nhập ID sản phẩm muốn mua (nhập số 0 để dừng): ");

            if (id == 0) {
                break;
            }

            Product product = productService.getById(id);
            if (product == null) {
                System.out.println(RED_BOLD + "❌ Sản phẩm không tồn tại." + RESET);
                continue;
            }

            int quantity = validateIntInput(sc, YELLOW_BOLD_BRIGHT + "❔ Nhập số lượng bạn muốn mua: " + RESET);

            if (quantity > product.getStock()) {
                System.out.println(RED_BRIGHT + "⚠️ Số lượng tồn kho không đủ.");
            } else {
                if (selectedProducts.containsKey(product)) {
                    selectedProducts.put(product, selectedProducts.get(product) + quantity);
                } else {
                    selectedProducts.put(product, quantity);
                }
            }
        }
        return selectedProducts;
    }

    // tính tổng giá trị hoa đơn dựa trên số lượng spham và giá của mỗi spham
    private BigDecimal calculateTotalPrice(Map<Product, Integer> selectedProducts) {
        return BigDecimal.valueOf(selectedProducts.entrySet()
                .stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum());
    }

    // tạo mới hóa đơn
    private void createInvoiceInPresentation(Scanner sc) {
        int customerId = handleCustomerInput(sc);

        Map<Product, Integer> selectedProducts = handleProductSelection(sc);

        BigDecimal totalPrice = calculateTotalPrice(selectedProducts);

        if (totalPrice.compareTo(BigDecimal.ZERO) == 0) {
            System.out.println(RED_BOLD_BRIGHT + "🆘 Tổng giá trị hóa đơn bằng 0. Không thể tạo hóa đơn!" + RESET);
            return;
        }

        InvoiceCreateDTO invoiceCreateDTO = new InvoiceCreateDTO();
        invoiceCreateDTO.setCustomerId(customerId);
        invoiceCreateDTO.setTotalPrice(totalPrice);

        List<InvoiceDetailCreateDTO> detailList = new ArrayList<>();


        for (Map.Entry<Product, Integer> entry : selectedProducts.entrySet()) {
            // thứ tự lần lượt: invoice_id, product_id, quantity, unit_price
            InvoiceDetailCreateDTO invoiceDetail = new InvoiceDetailCreateDTO();

            invoiceDetail.setProductId(entry.getKey().getId());
            invoiceDetail.setQuantity(entry.getValue());
            invoiceDetail.setUnitPrice(BigDecimal.valueOf(entry.getKey().getPrice()));

            detailList.add(invoiceDetail);

        }
        invoiceService.createInvoice(invoiceCreateDTO, detailList);
        System.out.println(GREEN_BOLD_BRIGHT+ "✅ Đã tạo thành công hóa đơn" + RESET);
    }

    // tìm kiếm hoa đơn theo tên khách hàng
    private void getInvoiceByCustomerName(Scanner sc) {
        String customerName = validateInputNotEmpty(sc, YELLOW_BOLD_BRIGHT + "❔ Nhập ten khách hàng cần tra cứu hóa đơn: " + RESET);

        List<InvoiceViewDTO> listInvoiceSuitable = invoiceService.findByCustomerName(customerName);

        if (listInvoiceSuitable.isEmpty()) {
            System.out.println("Không tìm thấy hóa đơn nào cho khách hàng có tên: " + customerName);
            return;
        }

        DisplayUIHelper.showInvoiceList(listInvoiceSuitable);
    }

    // tìm kiếm hoa đơn theo ngày tháng năm đầy đủ
    private void getInvoiceByDate(Scanner sc) {
        LocalDate date = null;
        while (date == null) {
            System.out.print(YELLOW_BOLD_BRIGHT + "❔ Nhập ngày cần tra cứu hóa đơn (dd/MM/yyyy hoặc dd-MM-yyyy): " + RESET);
            String input = sc.nextLine().trim();
            date = validateDateInput(input);
        }

        List<InvoiceViewDTO> invoices = invoiceService.findByDate(date);

        if (invoices.isEmpty()) {
            System.out.println(RED_BOLD + "❌ Không tìm thấy hóa đơn nào vào ngày " + date + RESET);
            return;
        }

        DisplayUIHelper.showInvoiceList(invoices);
    }
}



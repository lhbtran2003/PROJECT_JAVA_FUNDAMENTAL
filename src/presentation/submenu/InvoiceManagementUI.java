package presentation.submenu;

import bussiness.dto.InvoiceCreateDTO;
import bussiness.dto.InvoiceDetailCreateDTO;
import bussiness.dto.InvoiceViewDTO;
import bussiness.service.customer.CustomerServiceImpl;
import bussiness.service.invoice.InvoiceServiceImp;
import bussiness.service.invoicedetail.InvoiceDetailServiceImp;
import bussiness.service.product.ProductServiceImpl;
import entity.Customer;
import entity.Invoice;
import entity.Product;
import presentation.IGenericUI;
import presentation.MainMenuAfterLogin;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class InvoiceManagementUI implements IGenericUI {
    private final InvoiceServiceImp invoiceService;
    private final CustomerServiceImpl customerService;
    private final ProductServiceImpl productService;
    private final InvoiceDetailServiceImp invoiceDetailService;

    public InvoiceManagementUI() {
        invoiceService = InvoiceServiceImp.getInstance();
        customerService = CustomerServiceImpl.getInstance();
        productService = ProductServiceImpl.getInstance();
        invoiceDetailService = InvoiceDetailServiceImp.getInstance();
    }

    @Override
    public void showMenu() {
        System.out.println("========== QUẢN LÍ HÓA ĐƠN ==========");
        System.out.println("1. Hiển thị danh sách hóa đơn");
        System.out.println("2. Thêm mới hóa đơn");
        System.out.println("3. Tìm kiếm hóa đơn");
        System.out.println("4. Quay lại menu chính");
    }

    public void showMenuSearching() {
        System.out.println("1. Tìm kiếm theo ten khách hàng");
        System.out.println("2. Tìm kiếm theo ngày/tháng/năm");
        System.out.println("3. Quay lại menu hóa đơn");
    }

    public void runMenuSearching(Scanner sc) {
        showMenuSearching();
        System.out.print("===> Nhập lựa chọn tìm kiếm: ");
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
                System.out.println("Lựa chọn không hợp lệ");
                break;
        }
    }

    public void runInvoiceUI() {
        Scanner sc = new Scanner(System.in);
        InvoiceManagementUI invoiceManagementUI = new InvoiceManagementUI();
        while (true) {
            invoiceManagementUI.showMenu();
            System.out.print("Nhập lựa chọn: ");
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
                    System.out.println("Lựa chọn không hợp lệ");
                    break;
            }
        }
    }

    private void getAllInvoicesInPresentation() {
        List<Invoice> list = invoiceService.getAll();
        if (list.isEmpty()) {
            System.out.println("Danh sách hóa đơn hiện trang trống!");
            return;
        }

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        System.out.println("------------------------ Danh sách hóa đơn ---------------------------");
        System.out.printf("| %-2s | %-15s | %-20s | %-20s |\n", "ID", "Tên khách hàng", "Thời gian tạo", "Tổng tiền");
        for (Invoice invoice : list) {

            String customerName = customerService.getById(invoice.getCustomerId()).getName();
            LocalDateTime dateTime = LocalDateTime.parse(invoice.getCreateAt(), inputFormatter);
            String formattedDate = dateTime.format(outputFormatter);

            System.out.printf("| %-2s | %-15s | %-20s | %-20s |\n", invoice.getId(), customerName, formattedDate, invoice.getTotalAmount());
        }
        System.out.println("--------------------------------------------------------------------");
    }

    private int handleCustomerInput(Scanner sc) {
        System.out.print("Nhập tên khách hàng: ");
        String name = sc.nextLine();
        List<Customer> customerList = customerService.getCustomerByName(name);

        if (customerList == null || customerList.isEmpty()) {
            System.out.println("Khách hàng chưa có trong danh sách, sẽ tiến hành tạo mới.");
            Customer newCustomer = new Customer();
            newCustomer.setName(name);
            System.out.print("😋 Nhập số điện thoại: ");
            newCustomer.setPhone(sc.nextLine());
            System.out.print("😗 Nhập email: ");
            newCustomer.setEmail(sc.nextLine());
            System.out.print("🤔 Nhập địa chỉ: ");
            newCustomer.setAddress(sc.nextLine());

            List<Customer> toAdd = new ArrayList<>();
            toAdd.add(newCustomer);
            customerService.addMultiple(toAdd);

            return customerService.getCustomerByName(name).getFirst().getId();
        } else {
            DisplayUIHelper.showCustomerList(customerList);
            System.out.print("Nhập ID khách hàng muốn tạo hóa đơn: ");
            return Integer.parseInt(sc.nextLine());
        }
    }


    private Map<Product, Integer> handleProductSelection(Scanner sc) {
        List<Product> productList = productService.getAll();
        DisplayUIHelper.showProductList(productList);

        Map<Product, Integer> selectedProducts = new HashMap<>();
        while (true) {
            System.out.print("Nhập ID sản phẩm muốn mua (0 để dừng): ");
            int id = Integer.parseInt(sc.nextLine());
            if (id == 0) break;

            Product product = productService.getById(id);
            if (product == null) {
                System.out.println("❌ Sản phẩm không tồn tại.");
                continue;
            }

            System.out.print("Nhập số lượng bạn muốn mua: ");
            int quantity = Integer.parseInt(sc.nextLine());
            if (quantity > product.getStock()) {
                System.out.println("⚠️ Số lượng tồn kho không đủ.");
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

    // tính tổng giá
    private BigDecimal calculateTotalPrice(Map<Product, Integer> selectedProducts) {
        return BigDecimal.valueOf(selectedProducts.entrySet()
                .stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum());
    }

    private void createInvoiceInPresentation(Scanner sc) {
        int customerId = handleCustomerInput(sc);

        Map<Product, Integer> selectedProducts = handleProductSelection(sc);

        BigDecimal totalPrice = calculateTotalPrice(selectedProducts);

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
    }

    private void getInvoiceByCustomerName(Scanner sc) {
        System.out.print("Nhập ten khách hàng cần tra cứu hóa đơn: ");
        String customerName = sc.nextLine();

        List<InvoiceViewDTO> listInvoiceSuitable = invoiceService.findByCustomerName(customerName);

        if (listInvoiceSuitable.isEmpty()) {
            System.out.println("Không tìm thấy hóa đơn nào cho khách hàng có tên: " + customerName);
            return;
        }

        System.out.println("---------------------DANH SÁCH HÓA ĐƠN PHÙ HỢP -------------------------");
        System.out.printf("| %-5s | %-20s | %-15s | %-20s |\n", "ID", "Tên khách hàng", "Tổng tiền", "Ngày tạo");
        System.out.println("------------------------------------------------------------------------");

        for (InvoiceViewDTO invoice : listInvoiceSuitable) {
            System.out.printf("| %-5d | %-20s | %-15.2f | %-20s |\n",
                    invoice.getId(),
                    invoice.getCustomerName(),
                    invoice.getTotalAmount(),
                    invoice.getCreateAt()
            );
        }
        System.out.println("------------------------------------------------------------------------");
    }

    private void getInvoiceByDate(Scanner sc) {
        System.out.print("Nhập ngày cần tra cứu hóa đơn (dd/MM/yyyy hoặc dd-MM-yyyy): ");
        String inputDate = sc.nextLine();

        List<InvoiceViewDTO> invoices = invoiceService.findByDate(inputDate);

        if (invoices.isEmpty()) {
            System.out.println("Không tìm thấy hóa đơn nào vào ngày " + inputDate);
            return;
        }

        System.out.println("---------------------DANH SÁCH HÓA ĐƠN PHÙ HỢP -------------------------");
        System.out.printf("| %-5s | %-20s | %-15s | %-20s |\n", "ID", "Tên khách hàng", "Tổng tiền", "Ngày tạo");
        System.out.println("------------------------------------------------------------------------");
        for (InvoiceViewDTO invoice : invoices) {
            System.out.printf("| %-5d | %-20s | %-15.2f | %-20s |\n",
                    invoice.getId(),
                    invoice.getCustomerName(),
                    invoice.getTotalAmount(),
                    invoice.getCreateAt()
            );
        }
        System.out.println("------------------------------------------------------------------------");
    }
}



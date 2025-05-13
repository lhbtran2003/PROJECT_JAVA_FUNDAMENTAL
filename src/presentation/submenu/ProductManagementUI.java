package presentation.submenu;

import bussiness.service.product.IProductService;
import bussiness.service.product.ProductServiceImpl;
import entity.Product;
import presentation.IGenericUI;
import presentation.MainMenuAfterLogin;
import static utils.PrintUtils.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductManagementUI implements IGenericUI {
    private final IProductService productServiceImpl;

    public ProductManagementUI() {
        productServiceImpl = ProductServiceImpl.getInstance();
    }

    @Override
    public void showMenu() {
        System.out.println(WHITE_BOLD_BRIGHT + "========== QUẢN LÍ ĐIỆN THOẠI ==========");
        System.out.println("1. Hiển thị danh sách sản phẩm");
        System.out.println("2. Thêm sản phẩm mới");
        System.out.println("3. Cập nhật thông tin sản phẩm");
        System.out.println("4. Xóa sản phẩm theo ID");
        System.out.println("5. Tìm kiếm theo brand");
        System.out.println("6. Tìm kiếm theo khoảng giá");
        System.out.println("7. Tìm kiếm theo tồn kho");
        System.out.println("8. Quay lại menu chính");
        System.out.println("========================================="+ RESET);
    }

    public void runPhoneManagement() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            showMenu();
            System.out.print(YELLOW_BOLD_BRIGHT +"❔ Nhập lựa chọn: " + RESET);
            byte choice = Byte.parseByte(sc.nextLine());

            switch (choice) {
                case 1:
                    getAllProductsInPresentation();
                    break;
                case 2:
                    addMultipleProductsInPresentation(sc);
                    break;
                case 3:
                    updateProductInPresentation(sc);
                    break;
                case 4:
                    deleteProductInPresentation(sc);
                    break;
                case 5:
                    getProductsByBrandNameInPresentation(sc);
                    break;
                case 6:
                    getProductByPriceInPresentation(sc);
                    break;
                case 7:
                    getProductByStockInPresentation(sc);
                    break;
                case 8:
                    MainMenuAfterLogin.runMainMenu();
                    return;
                default:
                    System.out.println(RED_BOLD_BRIGHT +"😭Lựa chọn không hợp lệ"+ RESET);
                    break;
            }
        }
    }

    private void addMultipleProductsInPresentation(Scanner sc) {
        System.out.print(YELLOW_BOLD_BRIGHT +"❔ Nhập số lượng sản phẩm bạn muốn thêm: ");
        int count = Integer.parseInt(sc.nextLine());

        List<Product> products = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Product product = new Product();
            System.out.println("👌 Nhập thông tin sản phẩm thứ " + (i + 1));
            System.out.print("😊 Nhập tên sản phẩm: ");
            product.setName(sc.nextLine());
            System.out.print("😋 Nhập tên nhãn hàng: ");
            product.setBrand(sc.nextLine());
            System.out.print("😗 Nhập giá bán: ");
            product.setPrice(Double.parseDouble(sc.nextLine()));
            System.out.print("🤔 Nhập số lượng tồn kho: "+ RESET);
            product.setStock(Integer.parseInt(sc.nextLine()));
            products.add(product);
            System.out.println();
        }
        productServiceImpl.addMultiple(products);
    }

    private void getAllProductsInPresentation() {
        List<Product> products = productServiceImpl.getAll();
        DisplayUIHelper.showProductList(products);
    }

    private void getProductsByBrandNameInPresentation(Scanner sc) {
        System.out.print(YELLOW_BOLD_BRIGHT + "❔ Nhập tên brand cần tìm: "+ RESET);
        String brand = sc.nextLine();

        List<Product> products = productServiceImpl.getProductsByBrandName(brand);

        if (products.isEmpty()) {
            System.out.println(RED_BOLD +"❌ Không tìm thấy sản phẩm nào theo brand \"" + brand + "\"." + RESET);
            return;
        }
        DisplayUIHelper.showProductList(products);
    }

    // chỉnh sửa sản phẩm theo id
    private void updateProductInPresentation(Scanner sc) {
        System.out.print(YELLOW_BOLD_BRIGHT + "❔ Nhập id của sản phẩm muốn sửa: " + RESET);
        int id = Integer.parseInt(sc.nextLine());

        Product product = productServiceImpl.getById(id);
        if (product == null) {
            System.out.println(RED_BOLD +"❌ Không tìm thấy sản phẩm có id = " + id + RESET);
            return;
        }

        while (true) {
            System.out.println(CYAN_BOLD_BRIGHT + "Các thông tin có thể chỉnh sửa: "+ RESET);
            System.out.println(CYAN_BRIGHT + "1🥗 Tên sản phẩm");
            System.out.println("2🍗 Tên nhãn hàng");
            System.out.println("3🍜 Gía bán");
            System.out.println("4🍶 Số lượng tồn kho");
            System.out.println("5😎 Đã xong, thoát");
            System.out.println("......................" + RESET);
            System.out.print(YELLOW_BOLD_BRIGHT + "ヽ（≧□≦）ノ Bạn chon cái nào: ");
            byte choice = Byte.parseByte(sc.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("😋 Nhập tên mới cho sản phẩm này: ");
                    product.setName(sc.nextLine());
                    break;
                case 2:
                    System.out.print("😊 Nhập tên nhãn hàng mới cho sản phẩm này: ");
                    product.setBrand(sc.nextLine());
                    break;
                case 3:
                    System.out.print("🫡 Nhập giá mới cho sản phẩm này: ");
                    product.setPrice(Double.parseDouble(sc.nextLine()));
                    break;
                case 4:
                    System.out.print("🥰 Nhập số lượng tồn kho mới cho sản phẩm này: " + RESET);
                    product.setStock(Integer.parseInt(sc.nextLine()));
                    break;
                case 5:
                    System.out.println(GREEN_BOLD_BRIGHT + "🎉 Đã hoàn thành việc chỉnh sửa sản phẩm!" + RESET);
                    productServiceImpl.update(product);
                    return;
                default:
                    System.out.println(RED_BOLD_BRIGHT + "🆘 Bấm lộn rồi bạn êyyyyy, chọn lại đi nhóe ^^" + RESET);
                    break;
            }
        }
    }

    private void deleteProductInPresentation(Scanner sc) {
        System.out.print(YELLOW_BOLD_BRIGHT + "❔ Nhập id của sản phẩm muốn xóa: " + RESET);
        int id = Integer.parseInt(sc.nextLine());

        Product product = productServiceImpl.getById(id);
        if (product == null) {
            System.out.println(RED_BOLD + "❌ Không tìm thấy sản phẩm có id = " + id+ RESET);
            return;
        }
        System.out.print(PURPLE_BOLD_BRIGHT + "🔆 Có sản phẩm trong kho, bạn có muốn xóa nó không (có = 'y', không = 'n'): " + RESET);
        String answer = sc.nextLine();

        if (answer.equals("y")) {
            productServiceImpl.deleteById(id);
            System.out.println(GREEN_BOLD_BRIGHT +"✅ Đã xóa sản pham có id = " + id+ RESET);
        } else if (answer.equals("n")) {
            System.out.println(GREEN_BOLD_BRIGHT + "❎ Đã hủy thao tác xoa đối với sản phẩm có id = " + id + RESET);
        } else {
            System.out.println(RED_BOLD_BRIGHT + "😭Lựa chọn không hợp lệ, vui lòng thực hiện lại thao tác từ đầu." + RESET);
        }
    }

    private void getProductByPriceInPresentation(Scanner sc) {
        System.out.print(YELLOW_BOLD_BRIGHT + "❔ Nhập giá thấp nhất: ");
        double minPrice = Double.parseDouble(sc.nextLine());
        System.out.print("❔ Nhập giá cao nhất: " + RESET);
        double maxPrice = Double.parseDouble(sc.nextLine());
        List<Product> list = productServiceImpl.getProductsByPrice(minPrice, maxPrice);

        if (list.isEmpty()) {
            System.out.println(RED_BOLD + "❌ Không có sản phẩm trong khoảng giá này!"+ RESET);
            return;
        }

        DisplayUIHelper.showProductList(list);
    }

    private void getProductByStockInPresentation(Scanner sc) {
        System.out.print(YELLOW_BOLD_BRIGHT + "❔ Nhập so lượng tồn kho ít nhất: ");
        int minStock = Integer.parseInt(sc.nextLine());
        System.out.print("❔ Nhập so lượng tồn kho nhiều nhất: " + RESET);
        int maxStock = Integer.parseInt(sc.nextLine());
        List<Product> list = productServiceImpl.getProductsByStock(minStock, maxStock);

        if (list.isEmpty()) {
            System.out.println(RED_BOLD +"❌ Không có sản phẩm trong khoảng số lượng tồn kho này!"+RESET);
            return;
        }
        DisplayUIHelper.showProductList(list);
    }
}

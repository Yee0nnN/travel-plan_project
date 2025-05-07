
package travelplan.category;

import travelplan.common.CommonControllerInterface;

import java.util.List;
import java.util.Scanner;

public class CategoryController implements CommonControllerInterface {

    private final CategoryService service;

    public CategoryController() {
        this.service = new CategoryService();
    }

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @Override
    public String execute() {
        Scanner sc = new Scanner(System.in);
        while (true) {
        	System.out.println();
        	System.out.println(" ======================================");
        	System.out.println("             [ '카테고리'메뉴 ]");
        	System.out.println(" ──────────────────────────────────────");
            System.out.println(" 1. 전체 카테고리 보기");
            System.out.println(" 2. 새 카테고리 등록");
            System.out.println(" 3. 종료");
            System.out.println(" ──────────────────────────────────────");
            System.out.print(" [ 작업 번호 ]를 입력하세요: ");
            String input = sc.nextLine();
            switch (input) {
                case "1": showAll(); break;
                case "2": insert(); break;
                case "3": return "end";
                default: System.out.println(" [*] 잘못된 입력입니다.");
            }
        }
    }

    public void showAll() {
        List<CategoryDTO> list = service.getAllCategories();
        CategoryView.display(list);
    }

    public void insert() {
        String name = CategoryView.getNewCategoryName();
        int result = service.addCategory(CategoryDTO.builder().CATEGORY_NAME(name).build());
        CategoryView.display(result > 0 ? "등록 완료!!" : "등록 실패");
    }
}

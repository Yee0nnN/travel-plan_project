
package travelplan.category;

import java.util.List;
import java.util.Scanner;

public class CategoryView {
    static Scanner sc = new Scanner(System.in);

    public static void display(List<CategoryDTO> list) {
    	System.out.println();
    	System.out.println(" ======================================");
    	System.out.println("             [ '카테고리'목록 ]");
    	System.out.println(" ──────────────────────────────────────");
        for (CategoryDTO dto : list) {
            System.out.println(dto.getCATEGORY_ID() + ". " + dto.getCATEGORY_NAME());
        }
    }

    public static void display(String message) {
        System.out.println(" [*] " + message);
    }

    public static String getNewCategoryName() {
        System.out.print(" <> 새 카테고리 이름 입력: ");
        return sc.nextLine();
    }
}

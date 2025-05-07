
package travelplan.checklist;

import java.util.List;
import java.util.Scanner;

public class ChecklistView {
    private static final Scanner sc = new Scanner(System.in);

    public static void menuDisplay() {
    	System.out.println();
    	System.out.println(" ======================================");
    	System.out.println("             [ '체크리스트'메뉴 ]");
    	System.out.println(" ──────────────────────────────────────");
        System.out.println(" 1. 여행 체크리스트 조회");
        System.out.println(" 2. 체크리스트 항목 추가");
        System.out.println(" 3. 체크리스트 완료 여부 수정");
        System.out.println(" 4. 체크리스트 항목명 수정");
        System.out.println(" 5. 체크리스트 항목 삭제");
        System.out.println(" 6. 종료");
        System.out.println(" ──────────────────────────────────────");
        System.out.print(" [ 작업 번호 ]를 입력하세요: ");
    }

    public static void display(String message) {
        System.out.println("[*] " + message);
    }

    public static void display(List<ChecklistDTO> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("[*] 등록된 체크리스트 항목이 없습니다.");
            return;
        }
    	System.out.println();
    	System.out.println(" \n\n======================================");
    	System.out.println("            [ '체크리스트' ]");
    	System.out.println(" ──────────────────────────────────────");
        for (ChecklistDTO dto : list) {
            String status = "Y".equals(dto.getCHECKLIST_ISCOMPLETED()) ? "O" : "X";
            System.out.printf(" • 항목명: %s | 완료 여부: %s%n", dto.getCHECKLIST_ITEMNAME(), status);
        }
        System.out.println(" ──────────────────────────────────────\n\n");
    }

    public static String getChecklistNameInput(String label) {
        System.out.print(" <> " + label + "체크리스트 항목명 입력: ");
        return sc.nextLine().trim();
    }

    public static String getChecklistItemName() {
    	System.out.println();
        System.out.print(" <> 새 체크리스트 항목명 입력(default=N): ");
        return sc.nextLine().trim();
    }

    public static String getChecklistStatusInput() {
        System.out.print(" <> 완료 여부 입력 (Y/N): ");
        return sc.nextLine().trim();
    }
}


package travelplan.schedule;

import travelplan.category.CategoryDTO;
import travelplan.common.SessionClass;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ScheduleView {

    private static final Scanner scanner = new Scanner(System.in);

    public static void display(String message) {
        System.out.println("[*] " + message);
    }

    public static void menuDisplay() {
       	System.out.println();
    	System.out.println(" ======================================");
    	System.out.println("             [ '여행 일정'메뉴 ]");
    	System.out.println(" ──────────────────────────────────────");
        System.out.println(" 1. 여행 일정 만들기");
        System.out.println(" 2. 여행 일정 보기");
        System.out.println(" 3. 여행 일정 수정하기");
        System.out.println(" 4. 여행 일정 삭제하기");
        System.out.println(" 5. 뒤로가기");
        System.out.println(" ──────────────────────────────────────");
        System.out.print(" [ 작업 번호 ]를 입력하세요: ");
    }

    public static ScheduleDTO getScheduleInput() {
        List<LocalDate> dateRange = SessionClass.getInstance().getTravelDateRange();
        if (dateRange == null || dateRange.isEmpty()) {
            System.out.println(" [*] 여행 날짜 범위를 설정할 수 없습니다.");
            return null;
        }

        System.out.println(" <> 여행 날짜를 선택하세요: ");
        for (int i = 0; i < dateRange.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, dateRange.get(i));
        }

        System.out.print(" <> 번호 입력: ");
        String dateInput = scanner.nextLine().trim();
        if (dateInput.isEmpty()) {
            System.out.println("<> 날짜 입력: ");
            return null;
        }

        int index = Integer.parseInt(dateInput) - 1;
        if (index < 0 || index >= dateRange.size()) {
            System.out.println(" [*] 올바른 번호를 선택해주세요.");
            return null;
        }

        LocalDate selectedDate = dateRange.get(index);
        Date travelDate = Date.valueOf(selectedDate);

        String place = "";
        while (place.isEmpty()) {
            System.out.print(" <> 여행 장소: ");
            place = scanner.nextLine().trim();
            if (place.isEmpty()) System.out.println("장소를 입력해주세요.");
        }

        System.out.println(" <> 카테고리 선택: ");
        List<CategoryDTO> categories = SessionClass.getInstance().getCategories();
        for (CategoryDTO cat : categories) {
            System.out.printf("%d. %s\n", cat.getCATEGORY_ID(), cat.getCATEGORY_NAME());
        }

        int categoryId = 0;
        while (categoryId <= 0) {
            System.out.print(" <> 카테고리 번호: ");
            String catInput = scanner.nextLine().trim();
            if (catInput.isEmpty()) {
                System.out.println(" <> 카테고리 입력: ");
                continue;
            }
            try {
                categoryId = Integer.parseInt(catInput);
            } catch (NumberFormatException e) {
                System.out.println("올바른 숫자를 입력해주세요.");
            }
        }

        System.out.print(" <> 메모: ");
        String memo = scanner.nextLine().trim();

        return ScheduleDTO.builder()
                .SCHEDULE_DATE(travelDate)
                .SCHEDULE_PLACE(place)
                .CATEGORY_ID(categoryId)
                .SCHEDULE_MEMO(memo)
                .build();
    }

    public static String selectViewType() {
       	System.out.println();
    	System.out.println(" ======================================");
    	System.out.println("             [ '여행 일정'보기 ]");
    	System.out.println(" ──────────────────────────────────────");
        System.out.println(" 1. 날짜별 보기");
        System.out.println(" 2. 카테고리별 보기");
        System.out.println(" ──────────────────────────────────────");
        System.out.print(" [ 작업 번호 ]를 입력하세요: ");
        return scanner.nextLine().trim();
    }

    public static Date getDateFromRange(List<LocalDate> dateRange) {
        if (dateRange == null || dateRange.isEmpty()) return null;
        System.out.println();
        System.out.println(" ──────────────────────────────────────");
        System.out.println(" <> 날짜를 선택하세요: ");
        for (int i = 0; i < dateRange.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, dateRange.get(i));
        }
        System.out.println();
        System.out.println(" ──────────────────────────────────────");
        System.out.print(" <> 번호: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;
        return Date.valueOf(dateRange.get(index));
    }

    public static void display(List<ScheduleDTO> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("[*] 일정이 없습니다.");
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            ScheduleDTO dto = list.get(i);
            System.out.printf("%d. %s - %s\n", i + 1, dto.getSCHEDULE_PLACE(), dto.getSCHEDULE_MEMO());
        }
    }

    public static int selectScheduleIndex(List<ScheduleDTO> list) {
      	System.out.println();
    	System.out.println(" ======================================");
    	System.out.println("             [ 여행 일정 목록 ]");
    	System.out.println(" ──────────────────────────────────────");
        display(list);
        System.out.print(" <> 수정할 일정 번호: ");
        return Integer.parseInt(scanner.nextLine()) - 1;
    }

    public static String selectUpdateType() {
      	System.out.println();
    	System.out.println(" ======================================");
    	System.out.println("           [ 수정할 항목 선택 ]");
    	System.out.println(" ──────────────────────────────────────");
        System.out.println(" 1. 날짜");
        System.out.println(" 2. 장소");
        System.out.println(" 3. 카테고리");
        System.out.println(" 4. 메모");
        System.out.println(" ──────────────────────────────────────");
        System.out.print(" [ 작업 번호 ]를 입력하세요: ");
        return scanner.nextLine();
    }

    public static void showResult(int result) {
        System.out.println(result > 0 ? "[*] 작업 완료!!" : "[*] 작업 실패");
    }
}

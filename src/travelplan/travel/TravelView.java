
package travelplan.travel;

import java.sql.Date;
import java.util.Scanner;

public class TravelView {

    public static void display(String message) {
        System.out.println("[*] " + message);
    }

    public static void display(TravelDTO travel) {
        System.out.printf(" <> 여행 이름: %s%n", travel.getTRAVEL_NAME());
        System.out.printf(" <> 지역: %s%n", travel.getTRAVEL_LOCATION());
        System.out.printf(" <> 기간: %s ~ %s%n", travel.getTRAVEL_STARTDATE(), travel.getTRAVEL_ENDDATE());
    }

    public static void display(java.util.List<TravelDTO> list) {
        if (list == null || list.isEmpty()) {
            display("등록된 여행이 없습니다.");
            return;
        }

        System.out.println();
    	System.out.println(" ==============================================================================================");
    	System.out.println("                                  --*--[ '여행 목록' ]--*--      ");
    	System.out.println(" ──────────────────────────────────────────────────────────────────────────────────────────────");
        for (TravelDTO dto : list) {
            System.out.printf("• [여행 이름]: %s", dto.getTRAVEL_NAME());
            System.out.printf("  [여행 국가]: %s", dto.getTRAVEL_LOCATION());
            System.out.printf("  [가는 날짜]: %s", dto.getTRAVEL_STARTDATE());
            System.out.printf("  [오는 날짜]: %s", dto.getTRAVEL_ENDDATE());
            System.out.println("\n──────────────────────────────────────────────────────────────────────────────────────────────");
        }
    }

    public static TravelDTO getTravelInput() {
        Scanner sc = new Scanner(System.in);

        System.out.print(" <> 여행 이름: ");
        String name = sc.nextLine();

        System.out.print(" <> 여행 국가: ");
        String location = sc.nextLine();

        Date startDate = null;
        while (startDate == null) {
            System.out.print(" <> 시작일 (yyyy-mm-dd): ");
            try {
                startDate = Date.valueOf(sc.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println(" [*] 날짜 형식이 잘못되었습니다.(yyyy-mm-dd)");
            }
        }

        Date endDate = null;
        while (endDate == null) {
            System.out.print(" <> 종료일 (yyyy-mm-dd): ");
            try {
                endDate = Date.valueOf(sc.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println(" [*] 날짜 형식이 잘못되었습니다.(yyyy-mm-dd)");
            }
        }

        return TravelDTO.builder()
                .TRAVEL_NAME(name)
                .TRAVEL_LOCATION(location)
                .TRAVEL_STARTDATE(startDate)
                .TRAVEL_ENDDATE(endDate)
                .build();
    }

    public static String getTravelNameInput() {
        System.out.print(" <> 조회할 여행 이름: ");
        return new Scanner(System.in).nextLine().trim();
    }

    public static void menuDisplay() {
    	System.out.println();
    	System.out.println(" ======================================");
    	System.out.println("             [ '여행한걸음' ]");
    	System.out.println(" ──────────────────────────────────────");
        System.out.println(" 1. 여행 등록");
        System.out.println(" 2. 여행 목록");
        System.out.println(" 3. 여행 일정 보기");
        System.out.println(" 4. 여행 체크리스트 보기");
        System.out.println(" 5. 여행 수정");
        System.out.println(" 6. 여행 삭제");
        System.out.println(" 7. 종료");
        System.out.println(" ──────────────────────────────────────");
        System.out.print(" [ 작업 번호 ]를 입력하세요: ");
    }
}

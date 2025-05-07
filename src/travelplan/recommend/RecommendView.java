
package travelplan.recommend;

import java.util.List;

public class RecommendView {

    public static void display(String message) {
        System.out.println(" [*] " + message);
    }

    public static void displayWithMonth(List<RecommendDTO> list) {
        if (list == null || list.isEmpty()) {
            System.out.println(" [*] 추천 여행지가 없습니다.");
            return;
        }


    	System.out.println();
    	System.out.println(" ==============================================================================================");
    	System.out.println("                                  --*--[ '오늘의 추천지' ]--*--      ");
    	System.out.println(" ──────────────────────────────────────────────────────────────────────────────────────────────");
        for (RecommendDTO dto : list) {
            String month = dto.getRECOMMEND_DATE() + "월";
            System.out.printf("• [여행 이름]: %s (%s)", dto.getRECOMMEND_TRAVELNAME(), month);
            System.out.printf("  [장소]: %s", dto.getRECOMMEND_PLACE());
            System.out.printf("  [지역]: %s", dto.getRECOMMEND_TRAVELLOCATION());
            System.out.printf("  [카테고리]: %s", dto.getRECOMMEND_CATEGORY());
            System.out.println("\n──────────────────────────────────────────────────────────────────────────────────────────────");
        }
    }
}

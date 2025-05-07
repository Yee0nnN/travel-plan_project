
package travelplan.recommend;

import travelplan.common.SessionClass;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class RecommendController {

    private final RecommendService recommendService = new RecommendService();
    private final Scanner sc = new Scanner(System.in);
    private final String[] categoryOptions = {
        "카페", "식당", "관광", "액티비티", "축제", "자연", "역사/문화", "사진명소", "힐링", "체험", "야경"
    };

    public void showRandomRecommendations() {
        List<RecommendDTO> list = recommendService.getRandomRecommendations(3);
        RecommendView.displayWithMonth(list);

        while (true) {
            System.out.print(" <> 등록할 여행 이름 입력 (또는 Enter 건너뛰기): ");
            String input = sc.nextLine().trim();
            if (input.isEmpty()) break;

            RecommendDTO match = list.stream()
                .filter(r -> r.getRECOMMEND_TRAVELNAME().equals(input))
                .findFirst()
                .orElse(null);

            if (match != null) {
                int month = match.getRECOMMEND_DATE();
                LocalDate start = LocalDate.of(LocalDate.now().getYear(), month, 1);
                LocalDate end = start.plusDays(2);
                recommendService.registerAsTravel(match, start, end, SessionClass.getInstance().getUserId());
                System.out.println(" [*] 추천 여행이 등록되었습니다!");
                break;
            } else {
                System.out.println(" [*] 해당 이름의 추천이 존재하지 않습니다. 다시 입력해주세요.");
            }
        }
    }

    public void manageRecommendations() {
        while (true) {
        	System.out.println();
        	System.out.println(" ======================================");
        	System.out.println("             [ '추천 여행지 관리' ]");
        	System.out.println(" ──────────────────────────────────────");
            System.out.println(" 1. 전체 추천 보기");
            System.out.println(" 2. 추천 추가");
            System.out.println(" 3. 추천 수정");
            System.out.println(" 4. 추천 삭제");
            System.out.println(" 5. 뒤로가기");
            System.out.println(" ──────────────────────────────────────");
            System.out.print(" [ 작업 번호 ]를 입력하세요: ");
            String input = sc.nextLine();
            switch (input) {
                case "1" -> RecommendView.displayWithMonth(recommendService.getAll());
                case "2" -> addRecommend();
                case "3" -> updateRecommendByName();
                case "4" -> deleteRecommendByName();
                case "5" -> { return; }
                default -> System.out.println(" [*] 잘못된 입력입니다.");
            }
        }
    }

    private void showCategoryOptions() {
        for (int i = 0; i < categoryOptions.length; i++) {
            System.out.printf("%d. %s%n", i + 1, categoryOptions[i]);
        }
    }

    private String selectCategory() {
        showCategoryOptions();
        System.out.print(" <> 카테고리 번호 선택: ");
        int idx = Integer.parseInt(sc.nextLine()) - 1;
        return (idx >= 0 && idx < categoryOptions.length) ? categoryOptions[idx] : "기타";
    }

    private void addRecommend() {
        try {
            System.out.print(" <> 여행 국가: ");
            String location = sc.nextLine();
            System.out.print(" <> 여행 이름: ");
            String name = sc.nextLine();
            System.out.print(" <> 추천 월 (숫자): ");
            int month = Integer.parseInt(sc.nextLine());
            System.out.print(" <> 추천 장소: ");
            String place = sc.nextLine();
            System.out.println(" <> 카테고리를 선택하세요:");
            String category = selectCategory();

            RecommendDTO dto = RecommendDTO.builder()
                .RECOMMEND_DATE(month)
                .RECOMMEND_PLACE(place)
                .RECOMMEND_CATEGORY(category)
                .RECOMMEND_TRAVELNAME(name)
                .RECOMMEND_TRAVELLOCATION(location)
                .ADMIN_ID(SessionClass.getInstance().getUserId())
                .build();

            int result = recommendService.insert(dto);
            System.out.println(result > 0 ? "추가 완료!!" : "추가 실패");
        } catch (Exception e) {
            System.out.println(" [*] 입력 오류: " + e.getMessage());
        }
    }

    private void updateRecommendByName() {
        System.out.print(" <> 수정할 추천 여행 이름 입력: ");
        String name = sc.nextLine();
        RecommendDTO dto = recommendService.getByName(name);

        if (dto == null) {
            System.out.println(" [*] 해당 이름의 추천이 존재하지 않습니다.");
            return;
        }

    	System.out.println();
    	System.out.println(" ======================================");
    	System.out.println("             [ 수정할 항목 선택 ]");
    	System.out.println(" ──────────────────────────────────────");
        System.out.println(" 1. 여행 이름");
        System.out.println(" 2. 추천 장소");
        System.out.println(" 3. 카테고리");
        System.out.println(" 4. 추천 월");
        System.out.println(" 5. 여행 지역");
        System.out.println(" ──────────────────────────────────────");
        System.out.print(" [ 작업 번호 ]를 입력하세요: ");
        String choice = sc.nextLine();

        switch (choice) {
            case "1" -> {
                System.out.print(" <> 새로운 여행 이름 입력: ");
                dto.setRECOMMEND_TRAVELNAME(sc.nextLine());
            }
            case "2" -> {
                System.out.print(" <> 새로운 장소 입력: ");
                dto.setRECOMMEND_PLACE(sc.nextLine());
            }
            case "3" -> {
                System.out.println(" <> 새로운 카테고리 선택:");
                dto.setRECOMMEND_CATEGORY(selectCategory());
            }
            case "4" -> {
                System.out.print(" <> 새로운 월 입력 (숫자): ");
                dto.setRECOMMEND_DATE(Integer.parseInt(sc.nextLine()));
            }
            case "5" -> {
                System.out.print(" <> 새로운 국가 입력: ");
                dto.setRECOMMEND_TRAVELLOCATION(sc.nextLine());
            }
            default -> {
                System.out.println(" [*] 잘못된 입력입니다.");
                return;
            }
        }

        int result = recommendService.update(dto);
        System.out.println(result > 0 ? "수정 완료!!" : "수정 실패");
    }

    private void deleteRecommendByName() {
        System.out.print(" <> 삭제할 추천 여행 이름 입력: ");
        String name = sc.nextLine();
        RecommendDTO dto = recommendService.getByName(name);

        if (dto == null) {
            System.out.println(" [*] 해당 이름의 추천이 존재하지 않습니다.");
            return;
        }

        int result = recommendService.delete(dto.getRECOMMEND_ID());
        System.out.println(result > 0 ? "삭제 완료!!" : "삭제 실패");
    }
}

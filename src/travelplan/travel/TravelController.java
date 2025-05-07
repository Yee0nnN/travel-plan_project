
package travelplan.travel;

import travelplan.common.CommonControllerInterface;
import travelplan.common.SessionClass;
import travelplan.recommend.RecommendController;
import travelplan.schedule.ScheduleController;

import java.util.Scanner;
import java.sql.Date;
import java.util.List;

public class TravelController implements CommonControllerInterface {

    private final TravelService travelService = new TravelService();
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String execute() {
        new RecommendController().showRandomRecommendations();
        while (true) {
            TravelView.menuDisplay();
            String input = scanner.nextLine();
            switch (input) {
                case "1" -> f_register();
                case "2" -> f_list();
                case "3" -> f_detail();
                case "4" -> f_checklist();
                case "5" -> f_update();
                case "6" -> f_delete();
                case "7" -> { return "end"; }
                default -> TravelView.display("잘못된 입력입니다.");
            }
        }
    }

    private void f_register() {
        TravelDTO dto = TravelView.getTravelInput();
        dto.setUSER_ID(SessionClass.getInstance().getUserId());
        int result = travelService.insertTravel(dto);
        TravelView.display(result > 0 ? "여행 등록 완료!!" : "여행 등록 실패");
    }

    private void f_list() {
        TravelView.display(travelService.selectAllTravel(SessionClass.getInstance().getUserId()));
    }

    private void f_detail() {
        String name = TravelView.getTravelNameInput();
        TravelDTO travel = travelService.selectTravelByName(name, SessionClass.getInstance().getUserId());
        if (travel != null) {
            SessionClass.getInstance().setTravelId(travel.getTRAVEL_ID());
            SessionClass.getInstance().setTravelDateRange(travel.getTRAVEL_STARTDATE(), travel.getTRAVEL_ENDDATE());
            TravelView.display(travel);
            new ScheduleController().execute();
        } else {
            TravelView.display("해당 이름의 여행이 없습니다.");
        }
    }

    private void f_checklist() {
        String name = TravelView.getTravelNameInput();
        TravelDTO travel = travelService.selectTravelByName(name, SessionClass.getInstance().getUserId());
        if (travel != null) {
            SessionClass.getInstance().setTravelId(travel.getTRAVEL_ID());
            new travelplan.checklist.ChecklistController().execute();
        } else {
            TravelView.display("해당 이름의 여행이 없습니다.");
        }
    }

    private void f_update() {
        String name = TravelView.getTravelNameInput();
        TravelDTO origin = travelService.selectTravelByName(name, SessionClass.getInstance().getUserId());
        if (origin == null) {
            TravelView.display("해당 여행이 존재하지 않습니다.");
            return;
        }

       	System.out.println();
    	System.out.println(" ======================================");
    	System.out.println("            [ 수정할 항목 선택 ]");
    	System.out.println(" ──────────────────────────────────────");
        System.out.println(" 1. 여행 이름");
        System.out.println(" 2. 여행 지역");
        System.out.println(" 3. 여행 시작일");
        System.out.println(" 4. 여행 종료일");
        System.out.println(" ──────────────────────────────────────");
        System.out.print(" [ 작업 번호 ]를 입력하세요: ");
        String option = scanner.nextLine();

        String updatedName = origin.getTRAVEL_NAME();
        String updatedLocation = origin.getTRAVEL_LOCATION();
        Date updatedStart = origin.getTRAVEL_STARTDATE();
        Date updatedEnd = origin.getTRAVEL_ENDDATE();

        switch (option) {
            case "1" -> {
                System.out.print(" <> 새로운 여행 이름: ");
                updatedName = scanner.nextLine();
            }
            case "2" -> {
                System.out.print(" <> 새로운 지역 입력: ");
                updatedLocation = scanner.nextLine();
            }
            case "3" -> {
                System.out.print(" <> 새로운 시작일 입력 (yyyy-mm-dd): ");
                try {
                    updatedStart = Date.valueOf(scanner.nextLine());
                } catch (Exception e) {
                    TravelView.display("날짜 형식이 잘못되었습니다.");
                    return;
                }
            }
            case "4" -> {
                System.out.print(" <> 새 종료일 입력 (yyyy-mm-dd): ");
                try {
                    updatedEnd = Date.valueOf(scanner.nextLine());
                } catch (Exception e) {
                    TravelView.display("날짜 형식이 잘못되었습니다.");
                    return;
                }
            }
            default -> {
                TravelView.display("잘못된 선택입니다.");
                return;
            }
        }

        TravelDTO updated = TravelDTO.builder()
                .TRAVEL_ID(origin.getTRAVEL_ID())
                .USER_ID(origin.getUSER_ID())
                .TRAVEL_NAME(updatedName)
                .TRAVEL_LOCATION(updatedLocation)
                .TRAVEL_STARTDATE(updatedStart)
                .TRAVEL_ENDDATE(updatedEnd)
                .build();

        int result = travelService.updateTravel(updated);
        TravelView.display(result > 0 ? "수정 완료!!" : "수정 실패");
    }

    private void f_delete() {
        String name = TravelView.getTravelNameInput();
        TravelDTO target = travelService.selectTravelByName(name, SessionClass.getInstance().getUserId());
        if (target == null) {
            TravelView.display("해당 이름의 여행이 없습니다.");
            return;
        }
        int result = travelService.deleteTravel(target.getTRAVEL_ID());
        TravelView.display(result > 0 ? "삭제 완료!!" : "삭제 실패");
    }
}

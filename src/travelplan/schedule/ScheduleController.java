
package travelplan.schedule;

import travelplan.category.CategoryDTO;
import travelplan.common.CommonControllerInterface;
import travelplan.common.SessionClass;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ScheduleController implements CommonControllerInterface {

    private final ScheduleService scheduleService = new ScheduleService();
    private final Scanner scanner = new Scanner(System.in);
    private final int travelId = SessionClass.getInstance().getTravelId();
    private final List<CategoryDTO> categories = SessionClass.getInstance().getCategories();

    @Override
    public String execute() {
        while (true) {
            ScheduleView.menuDisplay();
            String job = scanner.nextLine();
            switch (job) {
                case "1" -> addSchedule();
                case "2" -> viewSchedule();
                case "3" -> updateSchedule();
                case "4" -> deleteSchedule();
                case "5" -> { return "end"; }
                default -> System.out.println(" [*] 잘못된 입력입니다.");
            }
        }
    }

    private void addSchedule() {
        ScheduleDTO dto = ScheduleView.getScheduleInput();
        if (dto == null) return;
        dto.setTRAVEL_ID(travelId);
        int result = scheduleService.insertSchedule(dto);
        ScheduleView.showResult(result);
    }

    private void viewSchedule() {
        String type = ScheduleView.selectViewType();
        if ("1".equals(type)) {
            Date date = ScheduleView.getDateFromRange(SessionClass.getInstance().getTravelDateRange());
            List<ScheduleDTO> list = scheduleService.selectScheduleByDate(date, travelId);
            if (list.isEmpty()) {
                ScheduleView.display("해당 날짜에 등록된 일정이 없습니다.");
            } else {
                ScheduleView.display(list);
            }
        } else {
        	System.out.println();
        	System.out.println(" ──────────────────────────────────────");
            for (CategoryDTO cat : categories) {
                List<ScheduleDTO> list = scheduleService.selectScheduleByCategory(cat.getCATEGORY_ID(), travelId);
                if (!list.isEmpty()) {
                	System.out.println();
                    System.out.println("[" + cat.getCATEGORY_NAME() + "]");
                    ScheduleView.display(list);
                }
            }
        }
    }

    private void updateSchedule() {
        Date date = ScheduleView.getDateFromRange(SessionClass.getInstance().getTravelDateRange());
        List<ScheduleDTO> list = scheduleService.selectScheduleByDate(date, travelId);
        if (list.isEmpty()) {
            ScheduleView.display("해당 날짜에 수정할 일정이 없습니다.");
            return;
        }
        int index = ScheduleView.selectScheduleIndex(list);
        ScheduleDTO selected = list.get(index);
        String type = ScheduleView.selectUpdateType();
        ScheduleDTO updated = scheduleService.updateFieldByType(selected, type);
        int result = scheduleService.updateSchedule(updated);
        ScheduleView.showResult(result);
    }

    private void deleteSchedule() {
        Date date = ScheduleView.getDateFromRange(SessionClass.getInstance().getTravelDateRange());
        List<ScheduleDTO> list = scheduleService.selectScheduleByDate(date, travelId);
        if (list.isEmpty()) {
            ScheduleView.display("해당 날짜에 삭제할 일정이 없습니다.");
            return;
        }
        int index = ScheduleView.selectScheduleIndex(list);
        int result = scheduleService.deleteSchedule(list.get(index).getSCHEDULE_ID(), travelId);
        ScheduleView.showResult(result);
    }
}

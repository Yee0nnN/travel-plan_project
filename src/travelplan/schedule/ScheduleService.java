
package travelplan.schedule;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import travelplan.category.CategoryDTO;
import travelplan.common.SessionClass;

public class ScheduleService {

    private final ScheduleDAO dao = new ScheduleDAO();
    private static final Scanner sc = new Scanner(System.in);

    public int insertSchedule(ScheduleDTO dto) {
        return dao.insertSchedule(dto);
    }

    public List<ScheduleDTO> selectScheduleByDate(Date date, int travelId) {
        return dao.selectScheduleByDate(date, travelId);
    }

    public List<ScheduleDTO> selectScheduleByCategory(int categoryId, int travelId) {
        return dao.selectScheduleByCategory(categoryId, travelId);
    }

    public int updateSchedule(ScheduleDTO dto) {
        return dao.updateSchedule(dto);
    }

    public int deleteSchedule(int scheduleId, int travelId) {
        return dao.deleteSchedule(scheduleId, travelId);
    }

    public ScheduleDTO updateFieldByType(ScheduleDTO dto, String type) {
        ScheduleDTO.ScheduleDTOBuilder builder = ScheduleDTO.builder()
                .SCHEDULE_ID(dto.getSCHEDULE_ID())
                .TRAVEL_ID(dto.getTRAVEL_ID())
                .SCHEDULE_DATE(dto.getSCHEDULE_DATE())
                .SCHEDULE_PLACE(dto.getSCHEDULE_PLACE())
                .CATEGORY_ID(dto.getCATEGORY_ID())
                .SCHEDULE_MEMO(dto.getSCHEDULE_MEMO());

        switch (type) {
            case "1" -> {
                List<LocalDate> dateRange = SessionClass.getInstance().getTravelDateRange();
                System.out.println(" <> 여행 날짜를 선택하세요: ");
                for (int i = 0; i < dateRange.size(); i++) {
                    System.out.printf("%d. %s%n", i + 1, dateRange.get(i));
                }
                System.out.print(" <> 날짜 번호 입력: ");
                int idx = Integer.parseInt(sc.nextLine()) - 1;
                builder.SCHEDULE_DATE(Date.valueOf(dateRange.get(idx)));
            }
            case "2" -> {
                System.out.print(" <> 변경할 장소 입력: ");
                builder.SCHEDULE_PLACE(sc.nextLine());
            }
            case "3" -> {
                List<CategoryDTO> categories = SessionClass.getInstance().getCategories();
                System.out.println(" <> 카테고리를 선택하세요: ");
                for (CategoryDTO cat : categories) {
                    System.out.printf("%d. %s%n", cat.getCATEGORY_ID(), cat.getCATEGORY_NAME());
                }
                System.out.print(" <> 변경할 카테고리 번호 입력: ");
                builder.CATEGORY_ID(Integer.parseInt(sc.nextLine()));
            }
            case "4" -> {
                System.out.print(" <> 변경할 메모 입력: ");
                builder.SCHEDULE_MEMO(sc.nextLine());
            }
        }
        return builder.build();
    }
}

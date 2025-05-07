
package travelplan.common;

import travelplan.admin.AdminController;
import travelplan.category.CategoryController;
import travelplan.checklist.ChecklistController;
import travelplan.schedule.ScheduleController;
import travelplan.travel.TravelController;
import travelplan.user.UserController;

public class ControllerFactory {
    public static CommonControllerInterface make(String type) {
        return switch (type) {
            case "admin" -> new AdminController();
            case "user" -> new UserController();
            case "travel" -> new TravelController();
            case "schedule" -> new ScheduleController();
            case "checklist" -> new ChecklistController();
            case "category" -> new CategoryController();
            default -> throw new IllegalArgumentException("지원하지 않는 컨트롤러: " + type);
        };
    }
}

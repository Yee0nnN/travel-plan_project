
package travelplan.schedule;

import lombok.Builder;
import lombok.Data;
import java.sql.Date;

@Data
@Builder
public class ScheduleDTO {
    private int SCHEDULE_ID;
    private int TRAVEL_ID;
    private Date SCHEDULE_DATE;
    private String SCHEDULE_PLACE;
    private String SCHEDULE_MEMO;
    private int CATEGORY_ID;
}

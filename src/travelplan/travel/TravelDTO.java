
package travelplan.travel;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class TravelDTO {
    private int TRAVEL_ID;
    private String USER_ID;
    private String TRAVEL_NAME;
    private String TRAVEL_LOCATION;
    private Date TRAVEL_STARTDATE;
    private Date TRAVEL_ENDDATE;
}

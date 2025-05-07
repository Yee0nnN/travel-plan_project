
package travelplan.checklist;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChecklistDTO {
    private int CHECKLIST_ID;
    private int TRAVEL_ID;
    private String CHECKLIST_ITEMNAME;
    private String CHECKLIST_ISCOMPLETED;
}

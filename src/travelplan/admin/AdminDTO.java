
package travelplan.admin;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminDTO {
    private String ADMIN_ID;
    private String ADMIN_PASSWORD;
    private String ADMIN_NAME;
    private String ADMIN_EMAIL;
}

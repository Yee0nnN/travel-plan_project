
package travelplan.category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDTO {
    private int CATEGORY_ID;
    private String CATEGORY_NAME;
}


package travelplan.recommend;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecommendDTO {
    private int RECOMMEND_ID;
    private int RECOMMEND_DATE;
    private String RECOMMEND_PLACE;
    private String RECOMMEND_CATEGORY;
    private String RECOMMEND_TRAVELNAME;
    private String RECOMMEND_TRAVELLOCATION;
    private String ADMIN_ID;
}

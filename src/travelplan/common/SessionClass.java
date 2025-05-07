
package travelplan.common;

import travelplan.category.CategoryDTO;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class SessionClass {

    private static SessionClass instance;
    private String userId;
    private String userName;
    private int travelId;
    private List<LocalDate> travelDateRange;
    private List<CategoryDTO> categories;

    public static SessionClass getInstance() {
        if (instance == null) instance = new SessionClass();
        return instance;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public int getTravelId() { return travelId; }
    public void setTravelId(int travelId) { this.travelId = travelId; }

    public List<LocalDate> getTravelDateRange() { return travelDateRange; }
    public void setTravelDateRange(Date start, Date end) {
        this.travelDateRange = start.toLocalDate().datesUntil(end.toLocalDate().plusDays(1)).toList();
    }

    public List<CategoryDTO> getCategories() { return categories; }
    public void setCategories(List<CategoryDTO> categories) { this.categories = categories; }
}

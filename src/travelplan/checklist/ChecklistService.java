
package travelplan.checklist;

import java.util.List;

public class ChecklistService {

    private final ChecklistDAO dao = new ChecklistDAO();

    public List<ChecklistDTO> getByTravelId(int travelId) {
        return dao.selectByTravelId(travelId);
    }

    public ChecklistDTO getItemByName(String name, int travelId) {
        return dao.selectByName(name, travelId);
    }

    public int addItem(int travelId, String name) {
        return dao.insert(travelId, name);
    }

    public int updateItem(ChecklistDTO dto) {
        return dao.update(dto);
    }

    public int deleteItem(int checklistId) {
        return dao.delete(checklistId);
    }
}

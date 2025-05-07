
package travelplan.checklist;

import travelplan.common.CommonControllerInterface;
import travelplan.common.SessionClass;

import java.util.List;
import java.util.Scanner;

public class ChecklistController implements CommonControllerInterface {

    private final ChecklistService service = new ChecklistService();
    private final Scanner sc = new Scanner(System.in);
    private final int travelId = SessionClass.getInstance().getTravelId();

    @Override
    public String execute() {
        while (true) {
            ChecklistView.menuDisplay();
            String input = sc.nextLine();
            switch (input) {
                case "1" -> view();
                case "2" -> add();
                case "3" -> updateStatusByName();
                case "4" -> updateNameByName();
                case "5" -> deleteByName();
                case "6" -> { return "end"; }
                default -> ChecklistView.display("잘못된 입력입니다.");
            }
        }
    }

    private void view() {
        List<ChecklistDTO> list = service.getByTravelId(travelId);
        ChecklistView.display(list);
    }

    private void add() {
        String name = ChecklistView.getChecklistItemName();
        int result = service.addItem(travelId, name);
        ChecklistView.display(result > 0 ? "추가 완료!!" : "추가 실패");
    }

    private void updateStatusByName() {
        List<ChecklistDTO> list = service.getByTravelId(travelId);
        ChecklistView.display(list);
        String name;
        ChecklistDTO item;
        while (true) {
            name = ChecklistView.getChecklistNameInput("상태를 수정할");
            item = service.getItemByName(name, travelId);
            if (item != null) break;
            ChecklistView.display("해당 항목이 존재하지 않습니다. 다시 입력해주세요.");
        }
        String newStatus = ChecklistView.getChecklistStatusInput();
        item.setCHECKLIST_ISCOMPLETED(newStatus);
        int result = service.updateItem(item);
        ChecklistView.display(result > 0 ? "상태 수정 완료!!" : "수정 실패");
    }

    private void updateNameByName() {
        List<ChecklistDTO> list = service.getByTravelId(travelId);
        ChecklistView.display(list);
        String name;
        ChecklistDTO item;
        while (true) {
            name = ChecklistView.getChecklistNameInput("이름을 수정할");
            item = service.getItemByName(name, travelId);
            if (item != null) break;
            ChecklistView.display("해당 항목이 존재하지 않습니다. 다시 입력해주세요.");
        }
        String newName = ChecklistView.getChecklistItemName();
        item.setCHECKLIST_ITEMNAME(newName);
        int result = service.updateItem(item);
        ChecklistView.display(result > 0 ? "이름 수정 완료!!" : "수정 실패");
    }

    private void deleteByName() {
        List<ChecklistDTO> list = service.getByTravelId(travelId);
        ChecklistView.display(list);
        String name;
        ChecklistDTO item;
        while (true) {
            name = ChecklistView.getChecklistNameInput("삭제할");
            item = service.getItemByName(name, travelId);
            if (item != null) break;
            ChecklistView.display("해당 항목이 존재하지 않습니다. 다시 입력해주세요.");
        }
        int result = service.deleteItem(item.getCHECKLIST_ID());
        ChecklistView.display(result > 0 ? "삭제 완료!!" : "삭제 실패");
    }
}


package travelplan.admin;

import java.util.List;
import java.util.Scanner;

import travelplan.common.CommonControllerInterface;
import travelplan.common.SessionClass;
import travelplan.recommend.RecommendController;
import travelplan.travel.TravelDTO;
import travelplan.travel.TravelService;
import travelplan.travel.TravelView;

public class AdminController implements CommonControllerInterface {

    private final AdminService service = new AdminService();
    private final TravelService travelService = new TravelService();
    private final RecommendController recommendController = new RecommendController();
    private final Scanner sc = new Scanner(System.in);

    @Override
    public String execute() {
        if (SessionClass.getInstance().getUserId() == null) {
            AdminView.display("로그인이 필요합니다.");
            return "unauthorized";
        }

        while (true) {
            AdminView.menuDisplay();
            String input = sc.nextLine();
            switch (input) {
                case "1" -> showUsers();
                case "2" -> manageRecommendations();
                case "3" -> {
                    AdminView.display("관리자 메뉴 종료");
                    return "end";
                }
                default -> AdminView.display("잘못된 입력입니다.");
            }
        }
    }

    public String admin_setting() {
        while (true) {
            AdminView.loginMenu();
            String job = sc.nextLine();
            switch (job) {
                case "1":
                    if (login()) return SessionClass.getInstance().getUserId();
                    break;  // 로그인 실패 시 다음 메뉴로 반복
                case "2":
                    register();
                    break;
                case "3":
                    return null;
                default:
                    AdminView.display("잘못된 입력입니다.");
            }
        }
    }

    private boolean login() {
        String id = AdminView.input(" <> 아이디: ");
        String pw = AdminView.input(" <> 비밀번호: ");
        AdminDTO admin = service.login(id, pw);
        if (admin != null) {
            SessionClass.getInstance().setUserId(admin.getADMIN_ID());
            AdminView.display(admin.getADMIN_NAME() + "님 로그인 성공!");
            return true;
        }
        AdminView.display("로그인 실패");
        return false;
    }

    private void register() {
        String id;
        while (true) {
            id = AdminView.input(" <> 아이디: ");
            if (service.exists(id)) {
                AdminView.display("이미 존재하는 ID입니다.");
            } else break;
        }

        String pw = AdminView.input(" <> 비밀번호: ");
        String name = AdminView.input(" <> 이름: ");
        String email;
        while (true) {
            email = AdminView.input(" <> 이메일: ");
            if (!email.contains("@")) {
                AdminView.display("올바른 이메일 형식이 아닙니다. '@'를 포함해주세요.");
            } else {
                break;
            }
        }

        AdminDTO dto = AdminDTO.builder()
            .ADMIN_ID(id)
            .ADMIN_PASSWORD(pw)
            .ADMIN_NAME(name)
            .ADMIN_EMAIL(email)
            .build();

        AdminView.display(service.register(dto) > 0 ? "가입 성공!!" : "가입 실패");
    }

    private void showUsers() {
        List<TravelDTO> list = travelService.selectAllTravel();
        TravelView.display(list);
    }

    private void manageRecommendations() {
        recommendController.manageRecommendations();
    }
}

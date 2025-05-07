
package travelplan.common;

import travelplan.admin.AdminController;
import travelplan.category.CategoryDAO;
import travelplan.category.CategoryService;
import travelplan.checklist.ChecklistController;
import travelplan.schedule.ScheduleController;
import travelplan.travel.TravelController;
import travelplan.user.UserController;
import travelplan.user.UserView;
import travelplan.category.CategoryDTO;

import java.util.List;
import java.util.Scanner;

public class FrontController {
    static Scanner sc = new Scanner(System.in);
    static boolean userLoggedIn = false;

    public static void main(String[] args) {
        menuLoop();

        SessionClass session = SessionClass.getInstance();
        String userId = session.getUserId();

        if (userId != null && userLoggedIn) {
            CategoryService categoryService = new CategoryService(new CategoryDAO());
            session.setCategories(categoryService.getAllCategories());

            TravelController travelController = new TravelController();
            while (true) {
                String result = travelController.execute();
                switch (result) {
                    case "schedule" -> new ScheduleController().execute();
                    case "checklist" -> new ChecklistController().execute();
                    case "end" -> {
                        System.out.println(" [*] 종료합니다.");
                        return;
                    }
                }
            }
        } else {
            System.out.println(" [*] 로그인하지 않아 프로그램을 종료합니다.");
        }

        sc.close();
    }

    public static void menuLoop() {
        boolean stop = false;
        while (!stop) {
            menuDisplay();
            String input = sc.nextLine();
            switch (input) {
                case "0" -> {
                    AdminController admin = new AdminController();
                    String result = admin.admin_setting();
                    if (result != null) {
                        String execResult = admin.execute();
                        if ("end".equals(execResult)) {
                            continue;
                        }
                    }
                }
                case "1" -> {
                    UserController userController = new UserController();
                    userController.execute();
                    if (SessionClass.getInstance().getUserId() != null) {
                        userLoggedIn = true;
                        return;  // 로그인 성공하면 menuLoop 탈출
                    }
                }
                case "2" -> stop = true;
                default -> UserView.display("올바른 메뉴를 선택하세요.");
            }
        }
    }

    public static void menuDisplay() {
    	System.out.println();
    	System.out.println(" ======================================");
        System.out.println("    [ '여행한걸음'에 오신 것을 환영합니다! ]");
        System.out.println(" ──────────────────────────────────────");
        System.out.println(" 0. 관리자 계정으로로그인");
        System.out.println(" 1. 사용자 계정으로 로그인");
        System.out.println(" 2. 종료");
        System.out.println(" ──────────────────────────────────────");
        System.out.print(" [ 작업 번호 ]를 입력하세요: ");
    }
}

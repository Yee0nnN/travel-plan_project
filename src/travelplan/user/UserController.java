
package travelplan.user;

import java.util.Scanner;
import travelplan.common.CommonControllerInterface;
import travelplan.common.SessionClass;

public class UserController implements CommonControllerInterface {

    private final Scanner sc = new Scanner(System.in);
    private final UserService service = new UserService();

    @Override
    public String execute() {
        while (true) {
            UserView.menuDisplay();
            String input = sc.nextLine();
            switch (input) {
                case "1":
                    if (f_login()) return "login";
                    break;
                case "2":
                    f_join();
                    break;
                case "3":
                    return null;
                default:
                    UserView.display("잘못된 입력입니다.");
            }
        }
    }

    public void f_join() {
        String id;
        while (true) {
            id = UserView.input(" <> 아이디 입력: ");
            if (service.isIdExist(id)) {
                UserView.display("이미 존재하는 아이디입니다.");
            } else break;
        }

        String pw = UserView.input(" <> 비밀번호: ");
        String name = UserView.input(" <> 이름: ");
        String email;
        while (true) {
            email = UserView.input(" <> 이메일: ");
            if (!email.contains("@")) {
                UserView.display("이메일에는 '@'가 포함되어야 합니다. 다시 입력해주세요.");
            } else {
                break;
            }
        }

        UserDTO dto = UserDTO.builder()
            .USER_ID(id)
            .USER_PASSWORD(pw)
            .USER_NAME(name)
            .USER_EMAIL(email)
            .build();

        int result = service.insert(dto);
        UserView.display(result > 0 ? "회원가입 성공!" : "회원가입 실패!");
    }

    private boolean f_login() {
        String id = UserView.input(" <> 아이디: ");
        String pw = UserView.input(" <> 비밀번호: ");
        UserDTO user = service.login(id, pw);
        if (user != null) {
            SessionClass.getInstance().setUserId(user.getUSER_ID());
            SessionClass.getInstance().setUserName(user.getUSER_NAME());
            UserView.display(user.getUSER_NAME() + "님 로그인 성공!!");
            return true;
        } else {
            UserView.display("로그인 실패");
            return false;
        }
    }
}

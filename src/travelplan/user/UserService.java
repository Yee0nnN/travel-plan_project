
package travelplan.user;

import java.util.List;

public class UserService {

    private final UserDAO dao = new UserDAO();

    public boolean isIdExist(String id) {
        return dao.selectById(id) != null;
    }

    public int insert(UserDTO dto) {
        return dao.insert(dto);
    }

    public UserDTO login(String id, String pw) {
        return dao.login(id, pw);
    }
}


package travelplan.admin;

public class AdminService {

    private final AdminDAO dao = new AdminDAO();

    public boolean exists(String id) {
        return dao.selectById(id) != null;
    }

    public int register(AdminDTO dto) {
        return dao.insert(dto);
    }

    public AdminDTO login(String id, String pw) {
        return dao.selectByLogin(id, pw);
    }
}

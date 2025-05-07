
package travelplan.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import travelplan.common.DBUtil;

public class UserDAO {

    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    private static final String SELECT_BY_ID = "SELECT * FROM USERS WHERE USER_ID = ?";
    private static final String LOGIN_SQL = "SELECT * FROM USERS WHERE USER_ID = ? AND USER_PASSWORD = ?";
    private static final String INSERT_SQL = "INSERT INTO USERS (USER_ID, USER_PASSWORD, USER_NAME, USER_EMAIL) VALUES (?, ?, ?, ?)";

    public UserDTO selectById(String id) {
        UserDTO dto = null;
        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(SELECT_BY_ID);
            pst.setString(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                dto = fromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbDisconnect(conn, pst, rs);
        }
        return dto;
    }

    public UserDTO login(String id, String pw) {
        UserDTO dto = null;
        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(LOGIN_SQL);
            pst.setString(1, id);
            pst.setString(2, pw);
            rs = pst.executeQuery();
            if (rs.next()) {
                dto = fromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbDisconnect(conn, pst, rs);
        }
        return dto;
    }

    public int insert(UserDTO dto) {
        int result = 0;
        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(INSERT_SQL);
            pst.setString(1, dto.getUSER_ID());
            pst.setString(2, dto.getUSER_PASSWORD());
            pst.setString(3, dto.getUSER_NAME());
            pst.setString(4, dto.getUSER_EMAIL());
            result = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbDisconnect(conn, pst, rs);
        }
        return result;
    }

    private UserDTO fromResultSet(ResultSet rs) throws Exception {
        return UserDTO.builder()
                .USER_ID(rs.getString("USER_ID"))
                .USER_PASSWORD(rs.getString("USER_PASSWORD"))
                .USER_NAME(rs.getString("USER_NAME"))
                .USER_EMAIL(rs.getString("USER_EMAIL"))
                .build();
    }
}

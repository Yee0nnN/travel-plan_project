
package travelplan.admin;

import java.sql.*;
import travelplan.common.DBUtil;

public class AdminDAO {

    private static final String SELECT_BY_ID_SQL = "SELECT * FROM ADMINS WHERE ADMIN_ID = ?";
    private static final String INSERT_SQL = "INSERT INTO ADMINS VALUES (?, ?, ?, ?)";
    private static final String LOGIN_SQL = "SELECT * FROM ADMINS WHERE ADMIN_ID = ? AND ADMIN_PASSWORD = ?";

    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    public AdminDTO selectById(String id) {
        AdminDTO dto = null;
        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(SELECT_BY_ID_SQL);
            pst.setString(1, id);
            rs = pst.executeQuery();
            if (rs.next()) dto = fromResultSet(rs);
        } catch (Exception e) { e.printStackTrace(); }
        finally { DBUtil.dbDisconnect(conn, pst, rs); }
        return dto;
    }

    public int insert(AdminDTO dto) {
        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(INSERT_SQL);
            pst.setString(1, dto.getADMIN_ID());
            pst.setString(2, dto.getADMIN_PASSWORD());
            pst.setString(3, dto.getADMIN_NAME());
            pst.setString(4, dto.getADMIN_EMAIL());
            return pst.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); return 0; }
        finally { DBUtil.dbDisconnect(conn, pst, rs); }
    }

    public AdminDTO selectByLogin(String id, String pw) {
        AdminDTO dto = null;
        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(LOGIN_SQL);
            pst.setString(1, id);
            pst.setString(2, pw);
            rs = pst.executeQuery();
            if (rs.next()) dto = fromResultSet(rs);
        } catch (Exception e) { e.printStackTrace(); }
        finally { DBUtil.dbDisconnect(conn, pst, rs); }
        return dto;
    }

    private AdminDTO fromResultSet(ResultSet rs) throws SQLException {
        return AdminDTO.builder()
                .ADMIN_ID(rs.getString("ADMIN_ID"))
                .ADMIN_PASSWORD(rs.getString("ADMIN_PASSWORD"))
                .ADMIN_NAME(rs.getString("ADMIN_NAME"))
                .ADMIN_EMAIL(rs.getString("ADMIN_EMAIL"))
                .build();
    }
}

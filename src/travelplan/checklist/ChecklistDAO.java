
package travelplan.checklist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import travelplan.common.DBUtil;

public class ChecklistDAO {

    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    private static final String SELECT_ALL = "SELECT * FROM TRAVEL_CHECKLISTS WHERE TRAVEL_ID = ?";
    private static final String SELECT_BY_NAME = "SELECT * FROM TRAVEL_CHECKLISTS WHERE CHECKLIST_ITEMNAME = ? AND TRAVEL_ID = ?";
    private static final String INSERT = "INSERT INTO TRAVEL_CHECKLISTS VALUES (CHECKLIST_SEQ.NEXTVAL, ?, ?, 'N')";
    private static final String UPDATE = "UPDATE TRAVEL_CHECKLISTS SET CHECKLIST_ITEMNAME = ?, CHECKLIST_ISCOMPLETED = ? WHERE CHECKLIST_ID = ?";
    private static final String DELETE = "DELETE FROM TRAVEL_CHECKLISTS WHERE CHECKLIST_ID = ?";

    public List<ChecklistDTO> selectByTravelId(int travelId) {
        List<ChecklistDTO> list = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(SELECT_ALL);
            pst.setInt(1, travelId);
            rs = pst.executeQuery();
            while (rs.next()) {
                list.add(fromResultSet(rs));
            }
        } catch (Exception e) { e.printStackTrace(); }
        finally { DBUtil.dbDisconnect(conn, pst, rs); }
        return list;
    }

    public ChecklistDTO selectByName(String name, int travelId) {
        ChecklistDTO dto = null;
        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(SELECT_BY_NAME);
            pst.setString(1, name);
            pst.setInt(2, travelId);
            rs = pst.executeQuery();
            if (rs.next()) {
                dto = fromResultSet(rs);
            }
        } catch (Exception e) { e.printStackTrace(); }
        finally { DBUtil.dbDisconnect(conn, pst, rs); }
        return dto;
    }

    public int insert(int travelId, String name) {
        int result = 0;
        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(INSERT);
            pst.setInt(1, travelId);
            pst.setString(2, name);
            result = pst.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        finally { DBUtil.dbDisconnect(conn, pst, rs); }
        return result;
    }

    public int update(ChecklistDTO dto) {
        int result = 0;
        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(UPDATE);
            pst.setString(1, dto.getCHECKLIST_ITEMNAME());
            pst.setString(2, dto.getCHECKLIST_ISCOMPLETED());
            pst.setInt(3, dto.getCHECKLIST_ID());
            result = pst.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        finally { DBUtil.dbDisconnect(conn, pst, rs); }
        return result;
    }

    public int delete(int checklistId) {
        int result = 0;
        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(DELETE);
            pst.setInt(1, checklistId);
            result = pst.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        finally { DBUtil.dbDisconnect(conn, pst, rs); }
        return result;
    }

    private ChecklistDTO fromResultSet(ResultSet rs) throws SQLException {
        return ChecklistDTO.builder()
            .CHECKLIST_ID(rs.getInt("CHECKLIST_ID"))
            .TRAVEL_ID(rs.getInt("TRAVEL_ID"))
            .CHECKLIST_ITEMNAME(rs.getString("CHECKLIST_ITEMNAME"))
            .CHECKLIST_ISCOMPLETED(rs.getString("CHECKLIST_ISCOMPLETED"))
            .build();
    }
}

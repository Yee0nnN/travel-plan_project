
package travelplan.schedule;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import travelplan.common.DBUtil;

public class ScheduleDAO {

    private Connection conn;
    private PreparedStatement pst;
    private ResultSet rs;

    private void connect(String sql) throws SQLException {
        conn = DBUtil.getConnection();
        pst = conn.prepareStatement(sql);
    }

    public int insertSchedule(ScheduleDTO dto) {
        try {
            connect("INSERT INTO TRAVEL_SCHEDULES (SCHEDULE_ID, SCHEDULE_DATE, SCHEDULE_PLACE, CATEGORY_ID, SCHEDULE_MEMO, TRAVEL_ID) " +
                    "VALUES (SCHEDULE_SEQ.NEXTVAL, ?, ?, ?, ?, ?)");
            pst.setDate(1, dto.getSCHEDULE_DATE());
            pst.setString(2, dto.getSCHEDULE_PLACE());
            pst.setInt(3, dto.getCATEGORY_ID());
            pst.setString(4, dto.getSCHEDULE_MEMO());
            pst.setInt(5, dto.getTRAVEL_ID());
            return pst.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); return 0; }
        finally { DBUtil.dbDisconnect(conn, pst, rs); }
    }

    public List<ScheduleDTO> selectScheduleByDate(Date date, int travelId) {
        List<ScheduleDTO> list = new ArrayList<>();
        try {
            connect("SELECT * FROM TRAVEL_SCHEDULES WHERE SCHEDULE_DATE = ? AND TRAVEL_ID = ?");
            pst.setDate(1, date);
            pst.setInt(2, travelId);
            rs = pst.executeQuery();
            while (rs.next()) list.add(fromResultSet(rs));
        } catch (Exception e) { e.printStackTrace(); }
        finally { DBUtil.dbDisconnect(conn, pst, rs); }
        return list;
    }

    public List<ScheduleDTO> selectScheduleByCategory(int categoryId, int travelId) {
        List<ScheduleDTO> list = new ArrayList<>();
        try {
            connect("SELECT * FROM TRAVEL_SCHEDULES WHERE CATEGORY_ID = ? AND TRAVEL_ID = ?");
            pst.setInt(1, categoryId);
            pst.setInt(2, travelId);
            rs = pst.executeQuery();
            while (rs.next()) list.add(fromResultSet(rs));
        } catch (Exception e) { e.printStackTrace(); }
        finally { DBUtil.dbDisconnect(conn, pst, rs); }
        return list;
    }

    public int updateSchedule(ScheduleDTO dto) {
        try {
            connect("UPDATE TRAVEL_SCHEDULES SET SCHEDULE_DATE=?, SCHEDULE_PLACE=?, CATEGORY_ID=?, SCHEDULE_MEMO=? WHERE SCHEDULE_ID=?");
            pst.setDate(1, dto.getSCHEDULE_DATE());
            pst.setString(2, dto.getSCHEDULE_PLACE());
            pst.setInt(3, dto.getCATEGORY_ID());
            pst.setString(4, dto.getSCHEDULE_MEMO());
            pst.setInt(5, dto.getSCHEDULE_ID());
            return pst.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); return 0; }
        finally { DBUtil.dbDisconnect(conn, pst, rs); }
    }

    public int deleteSchedule(int scheduleId, int travelId) {
        try {
            connect("DELETE FROM TRAVEL_SCHEDULES WHERE SCHEDULE_ID=? AND TRAVEL_ID=?");
            pst.setInt(1, scheduleId);
            pst.setInt(2, travelId);
            return pst.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); return 0; }
        finally { DBUtil.dbDisconnect(conn, pst, rs); }
    }

    private ScheduleDTO fromResultSet(ResultSet rs) throws SQLException {
        return ScheduleDTO.builder()
                .SCHEDULE_ID(rs.getInt("SCHEDULE_ID"))
                .TRAVEL_ID(rs.getInt("TRAVEL_ID"))
                .SCHEDULE_DATE(rs.getDate("SCHEDULE_DATE"))
                .SCHEDULE_PLACE(rs.getString("SCHEDULE_PLACE"))
                .SCHEDULE_MEMO(rs.getString("SCHEDULE_MEMO"))
                .CATEGORY_ID(rs.getInt("CATEGORY_ID"))
                .build();
    }
}

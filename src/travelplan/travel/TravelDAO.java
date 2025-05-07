
package travelplan.travel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import travelplan.common.DBUtil;

public class TravelDAO {
    private static final String INSERT_SQL = "INSERT INTO TRAVELS VALUES (TRAVEL_SEQ.NEXTVAL, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_BY_USER_SQL = "SELECT * FROM TRAVELS WHERE USER_ID = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM TRAVELS";
    private static final String SELECT_BY_NAME_SQL = "SELECT * FROM TRAVELS WHERE TRAVEL_NAME = ? AND USER_ID = ?";
    private static final String UPDATE_SQL = "UPDATE TRAVELS SET TRAVEL_NAME=?, TRAVEL_LOCATION=?, TRAVEL_STARTDATE=?, TRAVEL_ENDDATE=? WHERE TRAVEL_ID=?";
    private static final String DELETE_SQL = "DELETE FROM TRAVELS WHERE TRAVEL_ID = ?";

    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    public int insertTravel(TravelDTO dto) {
        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(INSERT_SQL);
            pst.setString(1, dto.getUSER_ID());
            pst.setString(2, dto.getTRAVEL_NAME());
            pst.setString(3, dto.getTRAVEL_LOCATION());
            pst.setDate(4, dto.getTRAVEL_STARTDATE());
            pst.setDate(5, dto.getTRAVEL_ENDDATE());
            return pst.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); return 0; }
        finally { DBUtil.dbDisconnect(conn, pst, rs); }
    }

    public List<TravelDTO> selectAllTravel(String userId) {
        List<TravelDTO> list = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(SELECT_ALL_BY_USER_SQL);
            pst.setString(1, userId);
            rs = pst.executeQuery();
            while (rs.next()) list.add(fromResultSet(rs));
        } catch (Exception e) { e.printStackTrace(); }
        finally { DBUtil.dbDisconnect(conn, pst, rs); }
        return list;
    }

    public List<TravelDTO> selectAllTravel() {
        List<TravelDTO> list = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(SELECT_ALL_SQL);
            rs = pst.executeQuery();
            while (rs.next()) list.add(fromResultSet(rs));
        } catch (Exception e) { e.printStackTrace(); }
        finally { DBUtil.dbDisconnect(conn, pst, rs); }
        return list;
    }

    public TravelDTO selectTravelByName(String name, String userId) {
        TravelDTO dto = null;
        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(SELECT_BY_NAME_SQL);
            pst.setString(1, name);
            pst.setString(2, userId);
            rs = pst.executeQuery();
            if (rs.next()) dto = fromResultSet(rs);
        } catch (Exception e) { e.printStackTrace(); }
        finally { DBUtil.dbDisconnect(conn, pst, rs); }
        return dto;
    }

    public int updateTravel(TravelDTO dto) {
        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(UPDATE_SQL);
            pst.setString(1, dto.getTRAVEL_NAME());
            pst.setString(2, dto.getTRAVEL_LOCATION());
            pst.setDate(3, dto.getTRAVEL_STARTDATE());
            pst.setDate(4, dto.getTRAVEL_ENDDATE());
            pst.setInt(5, dto.getTRAVEL_ID());
            return pst.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); return 0; }
        finally { DBUtil.dbDisconnect(conn, pst, rs); }
    }

    public int deleteTravel(int travelId) {
        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(DELETE_SQL);
            pst.setInt(1, travelId);
            return pst.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); return 0; }
        finally { DBUtil.dbDisconnect(conn, pst, rs); }
    }

    private TravelDTO fromResultSet(ResultSet rs) throws SQLException {
        return TravelDTO.builder()
                .TRAVEL_ID(rs.getInt("TRAVEL_ID"))
                .USER_ID(rs.getString("USER_ID"))
                .TRAVEL_NAME(rs.getString("TRAVEL_NAME"))
                .TRAVEL_LOCATION(rs.getString("TRAVEL_LOCATION"))
                .TRAVEL_STARTDATE(rs.getDate("TRAVEL_STARTDATE"))
                .TRAVEL_ENDDATE(rs.getDate("TRAVEL_ENDDATE"))
                .build();
    }
}

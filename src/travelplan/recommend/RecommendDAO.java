package travelplan.recommend;

import travelplan.common.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecommendDAO {
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    public List<RecommendDTO> getRandomRecommends(int count) {
        List<RecommendDTO> list = selectAll();
        Collections.shuffle(list);
        return list.subList(0, Math.min(count, list.size()));
    }

    public List<RecommendDTO> selectAll() {
        List<RecommendDTO> list = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement("SELECT * FROM RECOMMEND");
            rs = pst.executeQuery();
            while (rs.next()) list.add(fromResultSet(rs));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbDisconnect(conn, pst, rs);
        }
        return list;
    }

    public RecommendDTO selectById(int id) {
        RecommendDTO dto = null;
        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement("SELECT * FROM RECOMMEND WHERE RECOMMEND_ID = ?");
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) dto = fromResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbDisconnect(conn, pst, rs);
        }
        return dto;
    }

    public int insert(RecommendDTO dto) {
        int result = 0;
        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement("INSERT INTO RECOMMEND VALUES (RECOMMEND_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?)");
            pst.setInt(1, dto.getRECOMMEND_DATE());
            pst.setString(2, dto.getRECOMMEND_PLACE());
            pst.setString(3, dto.getRECOMMEND_CATEGORY());
            pst.setString(4, dto.getRECOMMEND_TRAVELNAME());
            pst.setString(5, dto.getRECOMMEND_TRAVELLOCATION());
            pst.setString(6, dto.getADMIN_ID());
            result = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbDisconnect(conn, pst, rs);
        }
        return result;
    }

    public int update(RecommendDTO dto) {
        int result = 0;
        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(
                "UPDATE RECOMMEND SET RECOMMEND_DATE=?, RECOMMEND_PLACE=?, RECOMMEND_CATEGORY=?, RECOMMEND_TRAVELNAME=?, RECOMMEND_TRAVELLOCATION=?, ADMIN_ID=? WHERE RECOMMEND_ID=?"
            );
            pst.setInt(1, dto.getRECOMMEND_DATE());
            pst.setString(2, dto.getRECOMMEND_PLACE());
            pst.setString(3, dto.getRECOMMEND_CATEGORY());
            pst.setString(4, dto.getRECOMMEND_TRAVELNAME());
            pst.setString(5, dto.getRECOMMEND_TRAVELLOCATION());
            pst.setString(6, dto.getADMIN_ID());
            pst.setInt(7, dto.getRECOMMEND_ID());
            result = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbDisconnect(conn, pst, rs);
        }
        return result;
    }

    public int delete(int id) {
        int result = 0;
        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement("DELETE FROM RECOMMEND WHERE RECOMMEND_ID = ?");
            pst.setInt(1, id);
            result = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbDisconnect(conn, pst, rs);
        }
        return result;
    }

    private RecommendDTO fromResultSet(ResultSet rs) throws SQLException {
        return RecommendDTO.builder()
                .RECOMMEND_ID(rs.getInt("RECOMMEND_ID"))
                .RECOMMEND_DATE(rs.getInt("RECOMMEND_DATE"))
                .RECOMMEND_PLACE(rs.getString("RECOMMEND_PLACE"))
                .RECOMMEND_CATEGORY(rs.getString("RECOMMEND_CATEGORY"))
                .RECOMMEND_TRAVELNAME(rs.getString("RECOMMEND_TRAVELNAME"))
                .RECOMMEND_TRAVELLOCATION(rs.getString("RECOMMEND_TRAVELLOCATION"))
                .ADMIN_ID(rs.getString("ADMIN_ID"))
                .build();
    }
    public RecommendDTO selectByName(String name) {
        RecommendDTO dto = null;
        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement("SELECT * FROM RECOMMEND WHERE RECOMMEND_TRAVELNAME = ?");
            pst.setString(1, name);
            rs = pst.executeQuery();
            if (rs.next()) dto = fromResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbDisconnect(conn, pst, rs);
        }
        return dto;
    }

}

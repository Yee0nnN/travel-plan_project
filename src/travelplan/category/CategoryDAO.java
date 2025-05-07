
package travelplan.category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import travelplan.common.DBUtil;

public class CategoryDAO {

    private static final String SELECT_SQL = "SELECT * FROM TRAVEL_CATEGORIES";
    private static final String INSERT_SQL = "INSERT INTO TRAVEL_CATEGORIES VALUES (CATEGORY_SEQ.NEXTVAL, ?)";

    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    public List<CategoryDTO> selectAll() {
        List<CategoryDTO> list = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(SELECT_SQL);
            rs = pst.executeQuery();
            while (rs.next()) {
                list.add(CategoryDTO.builder()
                    .CATEGORY_ID(rs.getInt("CATEGORY_ID"))
                    .CATEGORY_NAME(rs.getString("CATEGORY_NAME"))
                    .build());
            }
        } catch (Exception e) { e.printStackTrace(); }
        finally { DBUtil.dbDisconnect(conn, pst, rs); }
        return list;
    }

    public int insert(CategoryDTO dto) {
        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(INSERT_SQL);
            pst.setString(1, dto.getCATEGORY_NAME());
            return pst.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); return 0; }
        finally { DBUtil.dbDisconnect(conn, pst, rs); }
    }
}

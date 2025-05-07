package travelplan.recommend;

import travelplan.travel.TravelDTO;
import travelplan.travel.TravelService;

import java.time.LocalDate;
import java.util.List;

public class RecommendService {

    private final RecommendDAO recommendDAO = new RecommendDAO();
    private final TravelService travelService = new TravelService();

    public List<RecommendDTO> getRandomRecommendations(int count) {
        return recommendDAO.getRandomRecommends(count);
    }

    public List<RecommendDTO> getAll() {
        return recommendDAO.selectAll();
    }

    public RecommendDTO getById(int id) {
        return recommendDAO.selectById(id);
    }
    
    public RecommendDTO getByName(String name) {
        return recommendDAO.selectByName(name);
    }


    public int insert(RecommendDTO dto) {
        return recommendDAO.insert(dto);
    }

    public int update(RecommendDTO dto) {
        return recommendDAO.update(dto);
    }

    public int delete(int id) {
        return recommendDAO.delete(id);
    }

    public void registerAsTravel(RecommendDTO r, LocalDate start, LocalDate end, String userId) {
        TravelDTO dto = TravelDTO.builder()
                .TRAVEL_NAME(r.getRECOMMEND_TRAVELNAME())
                .TRAVEL_LOCATION(r.getRECOMMEND_TRAVELLOCATION())
                .TRAVEL_STARTDATE(java.sql.Date.valueOf(start))
                .TRAVEL_ENDDATE(java.sql.Date.valueOf(end))
                .USER_ID(userId)
                .build();
        travelService.insertTravel(dto);
    }
}

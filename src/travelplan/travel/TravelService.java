package travelplan.travel;

import java.util.List;

public class TravelService {

    private final TravelDAO dao = new TravelDAO();

    public int insertTravel(TravelDTO dto) {
        return dao.insertTravel(dto);
    }

    public List<TravelDTO> selectAllTravel(String userId) {
        return dao.selectAllTravel(userId);
    }

    public List<TravelDTO> selectAllTravel() {
        return dao.selectAllTravel();
    }

    public TravelDTO selectTravelByName(String name, String userId) {
        return dao.selectTravelByName(name, userId);
    }

    public int updateTravel(TravelDTO dto) {
        return dao.updateTravel(dto);
    }

    public int deleteTravel(int travelId) {
        return dao.deleteTravel(travelId);
    }
}

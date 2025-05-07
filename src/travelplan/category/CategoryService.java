
package travelplan.category;

import java.util.List;

public class CategoryService {
    private final CategoryDAO dao;

    public CategoryService() {
        this.dao = new CategoryDAO();
    }

    public CategoryService(CategoryDAO dao) {
        this.dao = dao;
    }

    public List<CategoryDTO> getAllCategories() {
        return dao.selectAll();
    }

    public int addCategory(CategoryDTO dto) {
        return dao.insert(dto);
    }
}

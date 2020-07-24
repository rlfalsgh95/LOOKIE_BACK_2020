package kr.or.connect.service.category;

import kr.or.connect.dto.category.CategoryDetail;

import java.util.List;

public interface CategoryService {
    List<CategoryDetail> selectAllCategory();
    int getCategoryCount();
}

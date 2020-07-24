package kr.or.connect.service.category.impl;

import kr.or.connect.dao.category.CategoryDao;
import kr.or.connect.dto.category.CategoryDetail;
import kr.or.connect.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryDao categoryDao;

    @Override
    public int getCategoryCount() {
        return categoryDao.getCategoryCount();
    }

    @Override
    public List<CategoryDetail> selectAllCategory() {
        return categoryDao.selectAllCategory();
    }
}

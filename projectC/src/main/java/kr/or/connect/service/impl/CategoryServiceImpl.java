package kr.or.connect.service.impl;

import kr.or.connect.dao.CategoryDao;
import kr.or.connect.dto.Category;
import kr.or.connect.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryDao categoryDao;

    @Override
    @Transactional
    public int getCount() {
        return categoryDao.getCount();
    }

    @Override
    @Transactional
    public List<Category> selectAll() {
        return categoryDao.selectAll();
    }
}

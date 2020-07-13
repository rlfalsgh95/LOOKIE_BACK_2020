package kr.or.connect.service;

import kr.or.connect.dto.Category;

import java.util.List;

public interface CategoryService {
    List<Category> selectAllCategory();
    int getCount();
}

package kr.or.connect.service;

import kr.or.connect.dto.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> selectAll();
    public int getCount();
}

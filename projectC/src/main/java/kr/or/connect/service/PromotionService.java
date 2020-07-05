package kr.or.connect.service;

import kr.or.connect.dto.Promotion;

import java.util.List;

public interface PromotionService {
    public List<Promotion> selectAll();
    public int getCount();
}

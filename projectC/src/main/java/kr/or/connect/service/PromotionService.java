package kr.or.connect.service;

import kr.or.connect.dto.Promotion;

import java.util.List;

public interface PromotionService {
    List<Promotion> selectAll();
    int getCount();
}

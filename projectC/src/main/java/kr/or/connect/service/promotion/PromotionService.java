package kr.or.connect.service.promotion;

import kr.or.connect.dto.PromotionDetail;

import java.util.List;

public interface PromotionService {
    List<PromotionDetail> selectAllPromotionDetail();
    int getPromotionCount();
}

package kr.or.connect.service.promotion.impl;

import kr.or.connect.dao.promotion.PromotionDao;
import kr.or.connect.dto.PromotionDetail;
import kr.or.connect.service.promotion.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    PromotionDao promotionDao;

    @Override
    public List<PromotionDetail> selectAllPromotionDetail() {
        return promotionDao.selectAllPromotionDetail();
    }

    @Override
    public int getPromotionCount() {
        return promotionDao.getPromotionCount();
    }
}

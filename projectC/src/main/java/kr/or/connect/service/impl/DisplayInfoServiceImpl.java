package kr.or.connect.service.impl;

import kr.or.connect.dao.DisplayInfoDao;
import kr.or.connect.dto.DisplayInfo;
import kr.or.connect.service.DisplayInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DisplayInfoServiceImpl implements DisplayInfoService {
    @Autowired
    DisplayInfoDao displayInfoDao;

    @Override
    @Transactional
    public int getTotalCount() {    // display_info 테이블의 모든 row의 개수를 반환
        return displayInfoDao.getTotalCount();
    }

    @Override
    @Transactional
    public int getCountByCategoryId(int categoryId){
        return displayInfoDao.getCountByCategoryId(categoryId);
    }

    @Override
    public DisplayInfo selectDisplayInfoById(int displayId) {
        return displayInfoDao.selectDisplayInfoById(displayId);
    }

    @Override
    @Transactional
    public List<DisplayInfo> selectDisplayInfos(int start, int categoryId) { //
        int totalCount = getTotalCount();

        return displayInfoDao.selectDisplayInfos(start, totalCount, categoryId);
    }
}

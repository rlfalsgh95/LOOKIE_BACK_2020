package kr.or.connect.service.display.impl;

import kr.or.connect.dao.display.DisplayInfoDao;
import kr.or.connect.dto.DisplayDetailInfo;
import kr.or.connect.service.display.DisplayInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DisplayInfoServiceImpl implements DisplayInfoService {
    @Autowired
    DisplayInfoDao displayInfoDao;

    @Override
    public int getDisplayInfoCount() {    // display_info 테이블의 모든 row의 개수를 반환
        return displayInfoDao.getDisplayInfoCount();
    }

    @Override
    public int getDisplayInfoCountByCategoryId(int categoryId){
        return displayInfoDao.getDisplayInfoCountByCategoryId(categoryId);
    }

    @Override
    public DisplayDetailInfo selectDisplayInfoByDisplayId(int displayId) {
        return displayInfoDao.selectDisplayInfoByDisplayId(displayId);
    }

    @Override
    public List<DisplayDetailInfo> selectDisplayDetailInfosByCategoryId(int start, int categoryId) {
        int limit = getDisplayInfoCount();   // start부터 마지막행까지 조회하기위해 Limit절의 limit을 테이블의 총 행의 수로 지정하였음.

        return displayInfoDao.selectDisplayDetailInfosByCategoryId(start, limit, categoryId);
    }
}

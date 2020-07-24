package kr.or.connect.service.display;

import kr.or.connect.dto.display.DisplayDetailInfo;

import java.util.List;

public interface DisplayInfoService {
    int getDisplayInfoCount();
    int getDisplayInfoCountByCategoryId(int categoryId);

    List<DisplayDetailInfo> selectDisplayDetailInfosByCategoryId(int start, int categoryId);
    DisplayDetailInfo selectDisplayInfoByDisplayId(int displayId);
}

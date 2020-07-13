package kr.or.connect.service;

import kr.or.connect.dto.DisplayInfo;

import java.util.List;

public interface DisplayInfoService {
    int getTotalCount();
    int getCountByCategoryId(int categoryId);
    List<DisplayInfo> selectDisplayInfos(int start, int categoryId);
    DisplayInfo selectDisplayInfoById(int displayId);
}

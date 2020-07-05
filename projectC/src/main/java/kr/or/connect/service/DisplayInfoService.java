package kr.or.connect.service;

import kr.or.connect.dto.DisplayInfo;

import java.util.List;

public interface DisplayInfoService {
    public int getTotalCount();
    public int getCountByCategoryId(int categoryId);
    public List<DisplayInfo> selectDisplayInfos(int start, int categoryId);
    public DisplayInfo selectDisplayInfoById(int displayId);
}

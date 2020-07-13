package kr.or.connect.service;

import kr.or.connect.dto.DisplayInfoImage;

import java.util.List;

public interface DisplayInfoImageService {
    List<DisplayInfoImage> selectDisplayInfoImagesByDisplayInfoId(int displayInfoId);
}

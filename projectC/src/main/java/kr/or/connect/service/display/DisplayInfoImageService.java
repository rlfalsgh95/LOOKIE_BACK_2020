package kr.or.connect.service.display;

import kr.or.connect.dto.display.DisplayInfoImage;

import java.util.List;

public interface DisplayInfoImageService {
    List<DisplayInfoImage> selectDisplayInfoImagesByDisplayInfoId(int displayInfoId);
}

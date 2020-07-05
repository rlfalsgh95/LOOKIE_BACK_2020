package kr.or.connect.service.impl;

import kr.or.connect.dao.DisplayInfoImageDao;
import kr.or.connect.dto.DisplayInfoImage;
import kr.or.connect.service.DisplayInfoImageService;
import kr.or.connect.service.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DisplayInfoImageServiceImpl implements DisplayInfoImageService {
    @Autowired
    DisplayInfoImageDao displayInfoImageDao;

    @Autowired
    FileInfoService fileInfoService;

    @Override
    public List<DisplayInfoImage> selectDisplayInfoImagesByDisplayInfoId(int displayInfoId) {
        List<DisplayInfoImage> displayInfoImageList = new ArrayList<>();

        List<Map<String, Object>> displayInfoimageInfos = displayInfoImageDao.selectDisplayInfoImageInfosByDisplayInfoId(displayInfoId);

        for (Map<String, Object> displayInfoimageInfo: displayInfoimageInfos){
            int displayInfoImageId = (int)displayInfoimageInfo.get("id");
            int fileId = (int)displayInfoimageInfo.get("file_id");

            DisplayInfoImage displayInfoImage = (DisplayInfoImage)fileInfoService.selectByFileId(fileId, DisplayInfoImage.class);

            displayInfoImage.setId(displayInfoImageId);
            displayInfoImage.setDisplayInfoId(displayInfoId);
            displayInfoImage.setFileId(fileId);
            displayInfoImageList.add(displayInfoImage);
        }

        return displayInfoImageList;
    }
}

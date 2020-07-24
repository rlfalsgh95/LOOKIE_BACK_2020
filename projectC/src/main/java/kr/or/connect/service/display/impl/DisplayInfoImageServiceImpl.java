package kr.or.connect.service.display.impl;

import kr.or.connect.dao.display.DisplayInfoImageDao;
import kr.or.connect.dao.file.FileInfoDao;
import kr.or.connect.dto.display.DisplayInfoImage;
import kr.or.connect.service.display.DisplayInfoImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class DisplayInfoImageServiceImpl implements DisplayInfoImageService {
    @Autowired
    DisplayInfoImageDao displayInfoImageDao;

    @Autowired
    FileInfoDao fileInfoDao;

    @Override
    public List<DisplayInfoImage> selectDisplayInfoImagesByDisplayInfoId(int displayInfoId) {
        List<DisplayInfoImage> displayInfoImageList = new ArrayList<>();

        List<Map<String, Object>> displayInfoimageInfos = displayInfoImageDao.selectDisplayInfoImageInfosByDisplayInfoId(displayInfoId);

        for (Map<String, Object> displayInfoimageInfo: displayInfoimageInfos){
            int displayInfoImageId = (int)displayInfoimageInfo.get("id");
            int fileId = (int)displayInfoimageInfo.get("file_id");

            DisplayInfoImage displayInfoImage = (DisplayInfoImage)fileInfoDao.selectByFileId(fileId, DisplayInfoImage.class);

            displayInfoImage.setId(displayInfoImageId);
            displayInfoImage.setDisplayInfoId(displayInfoId);
            displayInfoImage.setFileId(fileId);
            displayInfoImageList.add(displayInfoImage);
        }

        return displayInfoImageList;
    }
}

package kr.or.connect.service.impl;

import kr.or.connect.dao.FileInfoDao;
import kr.or.connect.service.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FileInfoServiceImpl implements FileInfoService {
    @Autowired
    FileInfoDao fileInfoDao;

    @Override
    public Object selectByFileId(int fileId, Class<?> requiredType) {
        return fileInfoDao.selectByFileId(fileId, requiredType);
    }
}

package kr.or.connect.service.file.impl;

import kr.or.connect.dao.file.FileInfoDao;
import kr.or.connect.service.file.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FileInfoServiceImpl implements FileInfoService {
    @Autowired
    FileInfoDao fileInfoDao;

    @Override
    public Object selectByFileId(int fileId, Class<?> requiredType) {
        return fileInfoDao.selectByFileId(fileId, requiredType);
    }
}

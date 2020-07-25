package kr.or.connect.service.file.impl;

import kr.or.connect.dao.file.FileInfoDao;
import kr.or.connect.dto.file.FileInfo;
import kr.or.connect.service.file.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FileInfoServiceImpl implements FileInfoService {
    @Autowired
    FileInfoDao fileInfoDao;

    @Override
    @Transactional(readOnly = true)
    public Object selectFileInfoByFileId(int fileId, Class<?> requiredType) {
        return fileInfoDao.selectFileInfoByFileId(fileId, requiredType);
    }

    @Override
    @Transactional(readOnly = false)
    public int insertFileInfo(FileInfo fileInfo) {
        return fileInfoDao.insertFileInfo(fileInfo);
    }
}

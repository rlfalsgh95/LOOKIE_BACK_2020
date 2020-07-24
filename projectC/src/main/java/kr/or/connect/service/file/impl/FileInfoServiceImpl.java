package kr.or.connect.service.file.impl;

import kr.or.connect.dao.file.FileInfoDao;
import kr.or.connect.dto.reservation.ReservationUserCommentImageDetail;
import kr.or.connect.dto.file.FileInfo;
import kr.or.connect.service.file.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FileInfoServiceImpl implements FileInfoService {
    @Autowired
    FileInfoDao fileInfoDao;

    @Override
    @Transactional(readOnly = true)
    public Object selectByFileId(int fileId, Class<?> requiredType) {
        try{
            return fileInfoDao.selectByFileId(fileId, requiredType);
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public String selectSaveFileNameByFileid(int fileId) {
        try{
            return fileInfoDao.selectSaveFileNameByFileid(fileId);
        }catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public int insertFileInfo(FileInfo fileInfo) {
        return fileInfoDao.insertFileInfo(fileInfo);
    }
}

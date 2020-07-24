package kr.or.connect.service.file;

import kr.or.connect.dto.file.FileInfo;

public interface FileInfoService {
    Object selectByFileId(int fileId, Class<?> requiredType);
    String selectSaveFileNameByFileid(int fileId);
    int insertFileInfo(FileInfo file);
}

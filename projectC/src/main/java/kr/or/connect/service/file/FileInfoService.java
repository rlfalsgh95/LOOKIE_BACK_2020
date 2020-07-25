package kr.or.connect.service.file;

import kr.or.connect.dto.file.FileInfo;

public interface FileInfoService {
    Object selectFileInfoByFileId(int fileId, Class<?> requiredType);
    int insertFileInfo(FileInfo file);
}

package kr.or.connect.service.file;

public interface FileInfoService {
    Object selectByFileId(int fileId, Class<?> requiredType);
}

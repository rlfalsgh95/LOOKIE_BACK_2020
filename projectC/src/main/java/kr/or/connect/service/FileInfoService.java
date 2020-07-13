package kr.or.connect.service;

public interface FileInfoService {
    Object selectByFileId(int fileId, Class<?> requiredType);
}

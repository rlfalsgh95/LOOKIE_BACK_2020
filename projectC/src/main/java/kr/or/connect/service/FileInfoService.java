package kr.or.connect.service;

public interface FileInfoService {
    public Object selectByFileId(int fileId, Class<?> requiredType);
}

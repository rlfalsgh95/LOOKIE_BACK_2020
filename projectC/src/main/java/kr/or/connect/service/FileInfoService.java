package kr.or.connect.service;

import kr.or.connect.dto.FileInfo;

import java.util.List;

public interface FileInfoService {
    public Object selectByFileId(int fileId, Class<?> requiredType);
}

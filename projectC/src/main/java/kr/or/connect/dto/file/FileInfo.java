package kr.or.connect.dto.file;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class FileInfo {
    private int id;
    private String fileName;
    private String saveFileName;
    private String contentType;
    private int deleteFlag;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")   // JSON 응답값의 형식을 지정
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")   // JSON 응답값의 형식을 지정
    private Date modifyDate;

    public FileInfo(){
    }

    public FileInfo(String fileName, String saveFileName, String contentType) {
        this.fileName = fileName;
        this.saveFileName = saveFileName;
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "fileName='" + fileName + '\'' +
                ", saveFileName='" + saveFileName + '\'' +
                ", contentType='" + contentType + '\'' +
                ", deleteFlag=" + deleteFlag +
                ", createDate=" + createDate +
                ", modifyDate=" + modifyDate +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSaveFileName() {
        return saveFileName;
    }

    public void setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}

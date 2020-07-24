package kr.or.connect.dto.display;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class DisplayInfoImage {
    private int id;
    private int displayInfoId;
    private int fileId;

    private String fileName;
    private String saveFileName;
    private String contentType;
    private int deleteFlag;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date modifyDate;

    @Override
    public String toString() {
        return "DisplayInfoImage{" +
                "id=" + id +
                ", displayInfoId=" + displayInfoId +
                ", fileId=" + fileId +
                ", fileName='" + fileName + '\'' +
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

    public int getDisplayInfoId() {
        return displayInfoId;
    }

    public void setDisplayInfoId(int displayInfoId) {
        this.displayInfoId = displayInfoId;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
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

package kr.or.connect.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ReservationUserCommentImageDetail {
    private int id;
    private int reservationInfoId;
    private int reservationUserCommentId;
    private int fileId;
    private String fileName;
    private String saveFileName;
    private String contentType;
    private int deleteFlag;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/seoul")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date modifyDate;

    @Override
    public String toString() {
        return "FileDetailInfo{" +
                "id=" + id +
                ", reservationInfoId=" + reservationInfoId +
                ", reservationUserCommentId=" + reservationUserCommentId +
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

    public int getReservationInfoId() {
        return reservationInfoId;
    }

    public void setReservationInfoId(int reservationInfoId) {
        this.reservationInfoId = reservationInfoId;
    }

    public int getReservationUserCommentId() {
        return reservationUserCommentId;
    }

    public void setReservationUserCommentId(int reservationUserCommentId) {
        this.reservationUserCommentId = reservationUserCommentId;
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

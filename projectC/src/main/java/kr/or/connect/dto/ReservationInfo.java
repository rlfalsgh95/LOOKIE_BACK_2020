package kr.or.connect.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ReservationInfo {
    private int id;
    private int productId;
    private int displayInfoId;
    private int cancelFlag;
    private int userId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date reservationDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date modifyDate;

    public ReservationInfo(){}

    public ReservationInfo(int productId, int displayInfoId, Date reservationDate, int userId){
        this.productId = productId;
        this.displayInfoId = displayInfoId;
        this.reservationDate = reservationDate;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ReservationInfo{" +
                "id=" + id +
                ", productId=" + productId +
                ", displayInfoId=" + displayInfoId +
                ", cancelFlag=" + cancelFlag +
                ", userId=" + userId +
                ", reservationDate=" + reservationDate +
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

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getDisplayInfoId() {
        return displayInfoId;
    }

    public void setDisplayInfoId(int displayInfoId) {
        this.displayInfoId = displayInfoId;
    }

    public int getCancelFlag() {
        return cancelFlag;
    }

    public void setCancelFlag(int cancelFlag) {
        this.cancelFlag = cancelFlag;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
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

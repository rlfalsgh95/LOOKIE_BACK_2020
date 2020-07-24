package kr.or.connect.dto.reservation;

import java.util.Date;

public class ReservationUserComment {
    private int id;
    private int productId;
    private int reservationInfoId;
    private int userId;
    private int score;
    private String comment;
    private Date createDate;
    private Date modifyDate;

    public ReservationUserComment(int productId, int reservationInfoId, int userId, int score, String comment) {
        this.productId = productId;
        this.reservationInfoId = reservationInfoId;
        this.userId = userId;
        this.score = score;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "ReservationUserComment{" +
                "id=" + id +
                ", productId=" + productId +
                ", reservation_info_id=" + reservationInfoId +
                ", userId=" + userId +
                ", score=" + score +
                ", comment='" + comment + '\'' +
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

    public int getReservationInfoId() {
        return reservationInfoId;
    }

    public void setReservationInfoId(int reservationInfoId) {
        this.reservationInfoId = reservationInfoId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

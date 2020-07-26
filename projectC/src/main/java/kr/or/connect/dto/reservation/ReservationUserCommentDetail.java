package kr.or.connect.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationUserCommentDetail {
    private int id;
    private int productId;
    private int reservationInfoId;
    private int score;
    private int userId;
    private String comment;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date modifyDate;

    private List<ReservationUserCommentImageDetail> reservationUserCommentImages = new ArrayList<>();  // default value는 빈 리스트 []

    public ReservationUserCommentDetail(){}

    public ReservationUserCommentDetail(int productId, int reservationInfoId, int score, int userId, String comment) {
        this.productId = productId;
        this.reservationInfoId = reservationInfoId;
        this.score = score;
        this.userId = userId;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "ReservationUserComment{" +
                "id=" + id +
                ", productId=" + productId +
                ", reservationInfoId=" + reservationInfoId +
                ", score=" + score +
                ", comment='" + comment + '\'' +
                ", createDate=" + createDate +
                ", modifyDate=" + modifyDate +
                ", reservationUserCommentImages=" + reservationUserCommentImages +
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public List<ReservationUserCommentImageDetail> getReservationUserCommentImages() {
        return reservationUserCommentImages;
    }

    public void setReservationUserCommentImages(List<ReservationUserCommentImageDetail> reservationUserCommentImages) {
        this.reservationUserCommentImages = reservationUserCommentImages;
    }
}

package kr.or.connect.dto.reservation;

public class ReservationUserCommentImage {
    private int id;
    private int reservationInfoId;
    private int reservationUserCommentId;
    private int fileId;

    public ReservationUserCommentImage() {
    }

    public ReservationUserCommentImage(int reservationInfoId, int reservationUserCommentId, int fileId) {
        this.reservationInfoId = reservationInfoId;
        this.reservationUserCommentId = reservationUserCommentId;
        this.fileId = fileId;
    }

    @Override
    public String toString() {
        return "ReservationUserCommentImage{" +
                "id=" + id +
                ", reservationInfoId=" + reservationInfoId +
                ", reservationUserCommentId=" + reservationUserCommentId +
                ", fileId=" + fileId +
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
}

package kr.or.connect.service.reservation;

import kr.or.connect.dto.file.FileInfo;
import kr.or.connect.dto.reservation.ReservationUserComment;
import kr.or.connect.dto.reservation.ReservationUserCommentDetail;
import kr.or.connect.dto.reservation.ReservationUserCommentImageDetail;

import java.util.List;

public interface ReservationUserCommentService {
    Integer getScoreAvgByProductId(int productId);
    int getUserCommentsCount();
    int getCommentCountByProductId(int productId);

    List<ReservationUserCommentDetail> selectUserComments(int start);
    List<ReservationUserCommentDetail> selectUserCommentsByProductId(int productId, int start);
    List<ReservationUserCommentImageDetail> selectUserCommentImages(ReservationUserCommentDetail comment);
    List<ReservationUserCommentDetail> setUserCommentImagesOfComments(List<ReservationUserCommentDetail> comments);

    int insertUserCommentWithFile(ReservationUserComment reservationUserCommentDetail, FileInfo fileInfo);
    int insertUserComment(ReservationUserComment reservationUserCommentDetail);
}

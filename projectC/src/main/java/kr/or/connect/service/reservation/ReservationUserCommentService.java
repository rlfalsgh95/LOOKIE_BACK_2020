package kr.or.connect.service.reservation;

import kr.or.connect.dto.ReservationUserComment;

import java.util.List;

public interface ReservationUserCommentService {
    Integer getScoreAvgByProductId(int productId);
    int getUserCommentsCount();
    int getCommentCountByProductId(int productId);
    List<ReservationUserComment> selectUserComments(int start);
    List<ReservationUserComment> selectUserCommentsByProductId(int productId, int start);
}

package kr.or.connect.service;

import kr.or.connect.dto.ReservationUserComment;

import java.util.List;

public interface ReservationUserCommentService {
    Integer getScore(int productId);
    int getTotalCount();
    int getCountByProductId(int productId);
    List<ReservationUserComment> selectComments(int start);
    List<ReservationUserComment> selectCommentsByProductId(int productId,int start);
}

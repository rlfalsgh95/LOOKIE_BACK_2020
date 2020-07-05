package kr.or.connect.service;

import kr.or.connect.dto.ReservationUserComment;

import java.util.List;

public interface ReservationUserCommentService {
    public int getScore(int productId);
    public int getTotalCount();
    public List<ReservationUserComment> selectAll(int start);
    public List<ReservationUserComment> selectByProductId(int productId,int start);
}

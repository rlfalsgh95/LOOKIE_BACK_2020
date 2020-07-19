package kr.or.connect.service.reservation.impl;

import kr.or.connect.dao.reservation.ReservationUserCommentDao;
import kr.or.connect.dto.ReservationUserComment;
import kr.or.connect.service.reservation.ReservationUserCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ReservationUserCommentServiceImpl implements ReservationUserCommentService {
    @Autowired
    ReservationUserCommentDao reservationUserCommentDao;

    @Override
    public int getUserCommentsCount() {
        return reservationUserCommentDao.getUserCommentsCount();
    }

    @Override
    public int getCommentCountByProductId(int productId) {
        return reservationUserCommentDao.getUserCommentsCountByProductId(productId);
    }

    private List<String> getUserCommentImages(ReservationUserComment comment){
        int reservationUserCommentId = comment.getId();
        return reservationUserCommentDao.getUserCommentImagesByUserCommentId(reservationUserCommentId);
    }
    @Override
    public List<ReservationUserComment> selectUserComments(int start) {
        List<ReservationUserComment> comments = reservationUserCommentDao.selectUserComments(start);

        for (ReservationUserComment comment : comments){
            List<String> commentImages = getUserCommentImages(comment);
            comment.setReservationUserCommentImages(commentImages);
        }
        return comments;
    }

    @Override
    public List<ReservationUserComment> selectUserCommentsByProductId(int productId, int start) {

        List<ReservationUserComment> comments = reservationUserCommentDao.selectUserCommentsByProductId(productId, start);

        for (ReservationUserComment comment : comments){
            List<String> commentImages = getUserCommentImages(comment);
            comment.setReservationUserCommentImages(commentImages);
        }
        return comments;
    }

    public Integer getScoreAvgByProductId(int productId){
        return reservationUserCommentDao.getScoreAvgByProductId(productId);
    }
}

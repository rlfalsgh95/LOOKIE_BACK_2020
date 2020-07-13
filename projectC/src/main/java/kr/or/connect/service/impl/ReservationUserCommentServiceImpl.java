package kr.or.connect.service.impl;

import kr.or.connect.dao.ReservationUserCommentDao;
import kr.or.connect.dto.ReservationUserComment;
import kr.or.connect.service.ReservationUserCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReservationUserCommentServiceImpl implements ReservationUserCommentService {
    @Autowired
    ReservationUserCommentDao reservationUserCommentDao;

    @Override
    public int getTotalCount() {
        return reservationUserCommentDao.getTotalCount();
    }

    @Override
    public int getCountByProductId(int productId) {
        return reservationUserCommentDao.getCountByProductId(productId);
    }

    private List<String> getUserCommentImages(ReservationUserComment comment){
        int reservationUserCommentId = comment.getId();
        return reservationUserCommentDao.getUserCommentImagesByCommentId(reservationUserCommentId);
    }
    @Override
    public List<ReservationUserComment> selectComments(int start) {
        List<ReservationUserComment> comments = reservationUserCommentDao.selectComments(start);

        for (ReservationUserComment comment : comments){
            List<String> commentImages = getUserCommentImages(comment);
            comment.setReservationUserCommentImages(commentImages);
        }
        return comments;
    }

    @Override
    public List<ReservationUserComment> selectCommentsByProductId(int productId,int start) {

        List<ReservationUserComment> comments = reservationUserCommentDao.selectCommentsByProductId(productId, start);

        for (ReservationUserComment comment : comments){
            List<String> commentImages = getUserCommentImages(comment);
            comment.setReservationUserCommentImages(commentImages);
        }
        return comments;
    }

    public Integer getScore(int productId){
        return reservationUserCommentDao.getScore(productId);
    }
}

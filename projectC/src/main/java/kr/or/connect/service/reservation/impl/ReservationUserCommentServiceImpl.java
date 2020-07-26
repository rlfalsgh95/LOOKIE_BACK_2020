package kr.or.connect.service.reservation.impl;

import com.fasterxml.jackson.core.JsonToken;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import kr.or.connect.dao.reservation.ReservationUserCommentDao;
import kr.or.connect.dto.file.FileInfo;
import kr.or.connect.dto.reservation.ReservationUserComment;
import kr.or.connect.dto.reservation.ReservationUserCommentDetail;
import kr.or.connect.dto.reservation.ReservationUserCommentImage;
import kr.or.connect.dto.reservation.ReservationUserCommentImageDetail;
import kr.or.connect.service.file.FileInfoService;
import kr.or.connect.service.reservation.ReservationService;
import kr.or.connect.service.reservation.ReservationUserCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservationUserCommentServiceImpl implements ReservationUserCommentService {
    @Autowired
    ReservationUserCommentDao reservationUserCommentDao;

    @Autowired
    ReservationService reservationService;

    @Autowired
    FileInfoService fileInfoService;

    @Override
    @Transactional(readOnly = true)
    public int getUserCommentsCount() {
        return reservationUserCommentDao.getUserCommentsCount();
    }

    @Override
    @Transactional(readOnly = true)
    public int getCommentCountByProductId(int productId) {
        return reservationUserCommentDao.getUserCommentsCountByProductId(productId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationUserCommentImageDetail> selectUserCommentImages(ReservationUserCommentDetail comment){
        int reservationUserCommentId = comment.getId();
        return reservationUserCommentDao.selectUserCommentImagesByUserCommentId(reservationUserCommentId);
    }

    @Override
    // DB 작업을 하지 않기 때문에 transactional 붙여주지 않음.
    public List<ReservationUserCommentDetail> setUserCommentImagesOfComments(List<ReservationUserCommentDetail> comments) {
        for (ReservationUserCommentDetail comment : comments){
            List<ReservationUserCommentImageDetail> commentImages = selectUserCommentImages(comment);
            comment.setReservationUserCommentImages(commentImages);
        }

        return comments;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationUserCommentDetail> selectUserComments(int start) {
        List<ReservationUserCommentDetail> comments = reservationUserCommentDao.selectUserComments(start);
        comments = setUserCommentImagesOfComments(comments);

        return comments;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationUserCommentDetail> selectUserCommentsByProductId(int productId, int start) {
        List<ReservationUserCommentDetail> comments = reservationUserCommentDao.selectUserCommentsByProductId(productId, start);
        comments = setUserCommentImagesOfComments(comments);

        return comments;
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getScoreAvgByProductId(int productId){
        return reservationUserCommentDao.getScoreAvgByProductId(productId);
    }

    @Override
    @Transactional(readOnly = false)
    public int insertUserCommentWithFile(ReservationUserComment userComment, FileInfo fileInfo) {
        int reservationInfoId = userComment.getReservationInfoId();

        // reservation_user_comment 테이블에 댓글 정보 추가.
        int userCommentId = reservationUserCommentDao.insertUserComment(userComment);

        // file_info에 row 추가.
        int fileId = fileInfoService.insertFileInfo(fileInfo);

        // reservation_user_comment_image에 row 추가.
        ReservationUserCommentImage userCommentImage = new ReservationUserCommentImage(reservationInfoId, userCommentId, fileId);
        reservationUserCommentDao.insertUserCommentImage(userCommentImage);

        return userCommentId;   // reservation_user_comment 테이블에 삽입한 row의 id를 반환
    }

    @Override
    @Transactional(readOnly = false)
    public int insertUserComment(ReservationUserComment userComment) {
        // reservation_user_comment 테이블에 댓글 정보 추가.

        return reservationUserCommentDao.insertUserComment(userComment);   // reservation_user_comment 테이블에 삽입한 row의 id를 반환
    }
}

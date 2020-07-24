package kr.or.connect.dao.reservation;

import kr.or.connect.dao.reservation.sqls.ReservationUserCommentDaoSqls;
import kr.or.connect.dto.reservation.ReservationUserComment;
import kr.or.connect.dto.reservation.ReservationUserCommentDetail;
import kr.or.connect.dto.reservation.ReservationUserCommentImage;
import kr.or.connect.dto.reservation.ReservationUserCommentImageDetail;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

import static kr.or.connect.dao.reservation.sqls.ReservationUserCommentDaoSqls.*;

@Repository
public class ReservationUserCommentDao {
    private final NamedParameterJdbcTemplate jdbc;
    private final RowMapper<ReservationUserCommentDetail> reservationUserCommentDetailRowMapper = BeanPropertyRowMapper.newInstance(ReservationUserCommentDetail.class);
    private final RowMapper<ReservationUserCommentImageDetail> reservationUserCommentImageDetailRowMapper = BeanPropertyRowMapper.newInstance(ReservationUserCommentImageDetail.class);

    private final SimpleJdbcInsert userCommentInsertAction;
    private final SimpleJdbcInsert userCommentImageInsertAction;

    private final int LIMIT = 5;

    public ReservationUserCommentDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);

        this.userCommentInsertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_user_comment")
                .usingGeneratedKeyColumns("id");

        this.userCommentImageInsertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_user_comment_image")
                .usingGeneratedKeyColumns("id");
    }

    public Integer getScoreAvgByProductId(int productId){
        Map<String, Integer> params = new HashMap<>();
        params.put("productId", productId);

        return jdbc.queryForObject(GET_SCORE_AVG_BY_PRODUCT_ID, params, Integer.class);
    }

    public List<ReservationUserCommentImageDetail> selectUserCommentImagesByUserCommentId(int userCommentId){
        Map<String, Integer> params = new HashMap<>();
        params.put("userCommentId", userCommentId);

        return jdbc.query(ReservationUserCommentDaoSqls.SELECT_USER_COMMENT_IMAGE_DETAIL_BY_USER_COMMENT_ID, params, reservationUserCommentImageDetailRowMapper);
    }

    public List<ReservationUserCommentDetail> selectUserCommentsByProductId(int productId, int start){
        Map<String, Object> params = new HashMap<>();
        params.put("productId", productId);
        params.put("start", start);
        params.put("limit", LIMIT);

        return jdbc.query(SELECT_RESERVATION_USER_COMMENT_BY_PRODUCT_ID, params, reservationUserCommentDetailRowMapper);
    }

    public List<ReservationUserCommentDetail> selectUserComments(int start){
        Map<String, Object> params = new HashMap<>();

        params.put("start", start);
        params.put("limit", LIMIT);

        return jdbc.query(SELECT_RESERVATION_USER_COMMENT, params, reservationUserCommentDetailRowMapper);
    }

    public int getUserCommentsCount(){
        return jdbc.queryForObject(GET_RESERVATION_USER_COMMENT_COUNT, Collections.<String, Object>emptyMap(), Integer.class);
    }

    public int getUserCommentsCountByProductId(int productId){
        Map<String, Integer> params = new HashMap<>();
        params.put("productId", productId);

        return jdbc.queryForObject(GET_RESERVATION_USER_COMMENT_COUNT_BY_PRODUCT_ID, params, Integer.class);
    }

    public int insertUserComment(ReservationUserComment userComment){
        SqlParameterSource params = new BeanPropertySqlParameterSource(userComment);
        Date curDate = new Date();
        userComment.setCreateDate(curDate);
        userComment.setModifyDate(curDate);

        return userCommentInsertAction.executeAndReturnKey(params).intValue();
    }

    public int insertUserCommentImage(ReservationUserCommentImage userCommentImage){
        SqlParameterSource param = new BeanPropertySqlParameterSource(userCommentImage);

        return userCommentImageInsertAction.executeAndReturnKey(param).intValue();
    }
}

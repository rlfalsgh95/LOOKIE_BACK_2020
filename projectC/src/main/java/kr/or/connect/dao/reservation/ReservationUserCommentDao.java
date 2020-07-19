package kr.or.connect.dao.reservation;

import kr.or.connect.dto.ReservationUserComment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kr.or.connect.dao.reservation.sqls.ReservationUserCommentDaoSqls.*;

@Repository
public class ReservationUserCommentDao {
    private final NamedParameterJdbcTemplate jdbc;
    private final RowMapper<ReservationUserComment> rowMapper = BeanPropertyRowMapper.newInstance(ReservationUserComment.class);
    private final int LIMIT = 5;

    public ReservationUserCommentDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public Integer getScoreAvgByProductId(int productId){
        Map<String, Integer> params = new HashMap<>();
        params.put("productId", productId);

        return jdbc.queryForObject(GET_SCORE_AVG_BY_PRODUCT_ID, params, Integer.class);
    }

    public List<String> getUserCommentImagesByUserCommentId(int reservationUserCommentId){
        Map<String, Integer> params = new HashMap<>();
        params.put("reservationUserCommentId", reservationUserCommentId);

        return jdbc.queryForList(SELECT_COMMENT_IMAGE_FILE_NAME_BY_RESERVATION_USER_COMMENT_ID, params, String.class);
    }
    public List<ReservationUserComment> selectUserCommentsByProductId(int productId, int start){
        Map<String, Object> params = new HashMap<>();
        params.put("productId", productId);
        params.put("start", start);
        params.put("limit", LIMIT);

        return jdbc.query(SELECT_RESERVATION_USER_COMMENT_BY_PRODUCT_ID, params, rowMapper);
    }

    public List<ReservationUserComment> selectUserComments(int start){
        Map<String, Object> params = new HashMap<>();

        params.put("start", start);
        params.put("limit", LIMIT);

        return jdbc.query(SELECT_RESERVATION_USER_COMMENT, params, rowMapper);
    }

    public int getUserCommentsCount(){
        return jdbc.queryForObject(GET_RESERVATION_USER_COMMENT_COUNT, Collections.<String, Object>emptyMap(), Integer.class);
    }

    public int getUserCommentsCountByProductId(int productId){
        Map<String, Integer> params = new HashMap<>();
        params.put("productId", productId);

        return jdbc.queryForObject(GET_RESERVATION_USER_COMMENT_COUNT_BY_PRODUCT_ID, params, Integer.class);
    }
}

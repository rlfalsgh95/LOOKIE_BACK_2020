package kr.or.connect.dao;

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

import static kr.or.connect.dao.sqls.ReservationUserCommentDaoSqls.*;


@Repository
public class ReservationUserCommentDao {
    private NamedParameterJdbcTemplate jdbc;
    private RowMapper<ReservationUserComment> rowMapper = BeanPropertyRowMapper.newInstance(ReservationUserComment.class);
    private final int LIMIT = 5;

    public ReservationUserCommentDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public Integer getScore(int productId){
        Map<String, Integer> params = new HashMap<>();
        params.put("productId", productId);

        return jdbc.queryForObject(GET_SCORE_AVG, params, Integer.class);
    }

    public List<String> getUserCommentImagesByCommentId(int commentId){
        Map<String, Integer> params = new HashMap<>();
        params.put("reservationUserCommentId", commentId);

        return jdbc.queryForList(SELECT_COMMENT_IMAGE_FILE_NAME_BY_COMMENT_ID, params, String.class);
    }
    public List<ReservationUserComment> selectByProductId(int productId, int start){
        Map<String, Object> params = new HashMap<>();
        params.put("productId", productId);
        params.put("start", start);
        params.put("limit", LIMIT);

        return jdbc.query(SELECT_BY_PRODUCT_ID, params, rowMapper);
    }

    public List<ReservationUserComment> selectAll(int start){
        Map<String, Object> params = new HashMap<>();

        params.put("start", start);
        params.put("limit", LIMIT);

        return jdbc.query(SELECT_ALL, params, rowMapper);
    }

    public int getTotalCount(){
        return jdbc.queryForObject(GET_TOTAL_COUNT, Collections.<String, Object>emptyMap(), Integer.class);
    }
}

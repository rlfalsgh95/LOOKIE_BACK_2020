package kr.or.connect.dao.promotion;

import kr.or.connect.dto.promotion.PromotionDetail;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

import static kr.or.connect.dao.promotion.sqls.PromotionDaoSqls.SELECT_ALL_PROMOTION_DETAIL;
import static kr.or.connect.dao.promotion.sqls.PromotionDaoSqls.GET_PROMOTION_COUNT;

@Repository
public class PromotionDao {
    private final NamedParameterJdbcTemplate jdbc;
    private final RowMapper<PromotionDetail> promotionDetailRowMapper = new BeanPropertyRowMapper<>(PromotionDetail.class);

    public PromotionDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<PromotionDetail> selectAllPromotionDetail(){
        return jdbc.query(SELECT_ALL_PROMOTION_DETAIL, promotionDetailRowMapper);
    }

    public int getPromotionCount(){
        return jdbc.queryForObject(GET_PROMOTION_COUNT, Collections.<String, Object>emptyMap(), Integer.class);
    }
}

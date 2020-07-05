package kr.or.connect.dao;

import kr.or.connect.dto.Promotion;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

import static kr.or.connect.dao.sqls.PromotionDaoSqls.SELECT_ALL;
import static kr.or.connect.dao.sqls.PromotionDaoSqls.GET_COUNT;

@Repository
public class PromotionDao {
    private NamedParameterJdbcTemplate jdbc;
    private RowMapper<Promotion> rowMapper = new BeanPropertyRowMapper<>(Promotion.class);

    public PromotionDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Promotion> selectAll(){
        return jdbc.query(SELECT_ALL, rowMapper);
    }

    public int getCount(){
        return jdbc.queryForObject(GET_COUNT, Collections.<String, Object>emptyMap(), Integer.class);
    }
}

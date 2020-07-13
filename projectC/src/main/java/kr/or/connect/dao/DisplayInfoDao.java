package kr.or.connect.dao;

import kr.or.connect.dto.DisplayInfo;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kr.or.connect.dao.sqls.DisplayInfoDaoSqls.*;

@Repository
public class DisplayInfoDao {
    private NamedParameterJdbcTemplate jdbc;
    private SimpleJdbcInsert insertAction;
    private RowMapper<DisplayInfo> rowMapper = BeanPropertyRowMapper.newInstance(DisplayInfo.class);

    public DisplayInfoDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<DisplayInfo> selectDisplayInfos(int start, int limit, int categoryId){
        Map<String, Integer> params = new HashMap<>();
        List<DisplayInfo> result = null;

        params.put("start", start);
        params.put("limit", limit);

        if(categoryId == 0){
            result = jdbc.query(SELECT_ALL_LIMIT, params, rowMapper);
        }else{
            params.put("categoryId", categoryId);
            result = jdbc.query(SELECT_BY_CATEGORY_ID_LIMIT, params, rowMapper);
        }

        return result;
    }

    public DisplayInfo selectDisplayInfoById(int displayId){
        Map<String, Integer> params = new HashMap<>();
        params.put("displayId", displayId);
        try{
            return jdbc.queryForObject(SELECT_BY_DISPLAY_ID, params, rowMapper);
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    public int getTotalCount(){
        return jdbc.queryForObject(GET_TOTAL_COUNT, Collections.<String, Object>emptyMap(), Integer.class);
    }

    public int getCountByCategoryId(int categoryId){
        Map<String, Integer> params = new HashMap<>();
        params.put("categoryId", categoryId);

        return jdbc.queryForObject(GET_TOTAL_COUNT_BY_CATEGORY_ID_LIMIT, params, Integer.class);
    }

}

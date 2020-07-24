package kr.or.connect.dao.display;

import kr.or.connect.dto.display.DisplayDetailInfo;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kr.or.connect.dao.display.sqls.DisplayInfoDaoSqls.*;

@Repository
public class DisplayInfoDao {
    private final NamedParameterJdbcTemplate jdbc;
    private final RowMapper<DisplayDetailInfo> rowMapper = BeanPropertyRowMapper.newInstance(DisplayDetailInfo.class);

    public DisplayInfoDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<DisplayDetailInfo> selectDisplayDetailInfosByCategoryId(int start, int limit, int categoryId){
        Map<String, Integer> params = new HashMap<>();
        List<DisplayDetailInfo> result = null;

        params.put("start", start);
        params.put("limit", limit);

        if(categoryId == 0){
            result = jdbc.query(SELECT_ALL_DISPLAY_DETAIL_INFOS_LIMIT, params, rowMapper);
        }else{
            params.put("categoryId", categoryId);
            result = jdbc.query(SELECT_DISPAY_DETAIL_INFOS_BY_CATEGORY_ID_LIMIT, params, rowMapper);
        }

        return result;
    }

    public DisplayDetailInfo selectDisplayInfoByDisplayId(int displayId){
        Map<String, Integer> params = new HashMap<>();
        params.put("displayId", displayId);

        try{
            return jdbc.queryForObject(SELECT_DISPLAY_INFO_BY_DISPLAY_ID, params, rowMapper);
        }catch(EmptyResultDataAccessException e){   // JdbcTemplate, queryForInt, queryForLong, queryForObject의 조회 결과가 없거나 하나 이상의 row인 경우 IncorrectResultSizeDataAccessException(row가 하나 이상인 경우)
                                                    // 또는 EmptyResultDataAccessException(row가 없는 경우)이 발생한다.
            return null;
        }
    }

    public int getDisplayInfoCount(){
        return jdbc.queryForObject(GET_DISPLAY_INFO_COUNT, Collections.<String, Object>emptyMap(), Integer.class);
    }

    public int getDisplayInfoCountByCategoryId(int categoryId){
        Map<String, Integer> params = new HashMap<>();
        params.put("categoryId", categoryId);

        return jdbc.queryForObject(GET_DISPLAY_INFO_COUNT_BY_CATEGORY_ID_LIMIT, params, Integer.class);
    }

}

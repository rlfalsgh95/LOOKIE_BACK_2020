package kr.or.connect.dao.display;

import kr.or.connect.dao.display.sqls.DisplayInfoDaoSqls;
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

@Repository
public class DisplayInfoDao {
    private final NamedParameterJdbcTemplate jdbc;
    private final RowMapper<DisplayDetailInfo> displayDetailInfoRowMapper = BeanPropertyRowMapper.newInstance(DisplayDetailInfo.class);

    public DisplayInfoDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<DisplayDetailInfo> selectDisplayDetailInfosByCategoryId(int start, int limit, int categoryId){
        Map<String, Integer> params = new HashMap<>();
        List<DisplayDetailInfo> result = null;

        params.put("start", start);
        params.put("limit", limit);

        if(categoryId == 0){
            result = jdbc.query(DisplayInfoDaoSqls.SELECT_ALL_DISPLAY_DETAIL_INFOS_LIMIT, params, displayDetailInfoRowMapper);
        }else{
            params.put("categoryId", categoryId);
            result = jdbc.query(DisplayInfoDaoSqls.SELECT_DISPAY_DETAIL_INFOS_BY_CATEGORY_ID_LIMIT, params, displayDetailInfoRowMapper);
        }

        return result;
    }

    public DisplayDetailInfo selectDisplayInfoByDisplayId(int displayId){
        Map<String, Integer> params = Collections.singletonMap("displayId", displayId);

        try{
            return jdbc.queryForObject(DisplayInfoDaoSqls.SELECT_DISPLAY_INFO_BY_DISPLAY_ID, params, displayDetailInfoRowMapper);
        }catch(EmptyResultDataAccessException e){   // JdbcTemplate, queryForInt, queryForLong, queryForObject의 조회 결과가 없거나 하나 이상의 row인 경우 IncorrectResultSizeDataAccessException(row가 하나 이상인 경우)
            return null;                            // 또는 EmptyResultDataAccessException(row가 없는 경우)이 발생한다.
        }
    }

    public int getDisplayInfoCount(){
        return jdbc.queryForObject(DisplayInfoDaoSqls.GET_DISPLAY_INFO_COUNT, Collections.<String, Object>emptyMap(), Integer.class);
    }

    public int getDisplayInfoCountByCategoryId(int categoryId){
        Map<String, Integer> params = Collections.singletonMap("categoryId", categoryId);

        return jdbc.queryForObject(DisplayInfoDaoSqls.GET_DISPLAY_INFO_COUNT_BY_CATEGORY_ID_LIMIT, params, Integer.class);
    }

}

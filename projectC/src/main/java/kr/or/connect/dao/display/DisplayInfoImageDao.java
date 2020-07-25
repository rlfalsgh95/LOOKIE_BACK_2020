package kr.or.connect.dao.display;

import kr.or.connect.dao.display.sqls.DisplayInfoImageDaoSql;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class DisplayInfoImageDao {
    private final NamedParameterJdbcTemplate jdbc;

    public DisplayInfoImageDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Map<String, Object>> selectDisplayInfoImageInfosByDisplayInfoId(int displayInfoId){
        Map<String, Integer> params = Collections.singletonMap("displayInfoId", displayInfoId);

        return jdbc.queryForList(DisplayInfoImageDaoSql.SELECT_DISPLAY_INFO_IMAGE_INFO_BY_DISPLAY_INFO_ID, params);
    }
}

package kr.or.connect.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kr.or.connect.dao.sqls.DisplayInfoImageDaoSql.SELECT_DISPLAY_INFO_IMAGE_INFO_BY_DISPLAY_INFO_ID;


@Repository
public class DisplayInfoImageDao {
    private NamedParameterJdbcTemplate jdbc;

    public DisplayInfoImageDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Map<String, Object>> selectDisplayInfoImageInfosByDisplayInfoId(int displayInfoId){
        Map<String, Integer> params = new HashMap<>();
        params.put("displayInfoId", displayInfoId);
        return jdbc.queryForList(SELECT_DISPLAY_INFO_IMAGE_INFO_BY_DISPLAY_INFO_ID, params);
    }
}

package kr.or.connect.dao.file;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static kr.or.connect.dao.file.sqls.FileInfoDaoSqls.SELECT_BY_ID;

@Repository
public class FileInfoDao {
    private final NamedParameterJdbcTemplate jdbc;

    public FileInfoDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    @Transactional
    public Object selectByFileId(int fileId, Class<?> requiredType){
        Map<String, Integer> params = new HashMap<>();
        params.put("fileId", fileId);

        RowMapper requiredRowMapper = BeanPropertyRowMapper.newInstance(requiredType);
        return jdbc.queryForObject(SELECT_BY_ID, params, requiredRowMapper);
    }
}

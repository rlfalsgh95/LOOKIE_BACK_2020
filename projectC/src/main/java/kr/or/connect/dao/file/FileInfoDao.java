package kr.or.connect.dao.file;

import kr.or.connect.dao.file.sqls.FileInfoDaoSqls;
import kr.or.connect.dto.reservation.ReservationUserCommentImageDetail;
import kr.or.connect.dto.file.FileInfo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class FileInfoDao {
    private final NamedParameterJdbcTemplate jdbc;
    private final SimpleJdbcInsert insertAction;

    public FileInfoDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("file_info")
                .usingGeneratedKeyColumns("id");
    }

    public int insertFileInfo(FileInfo fileInfo){
        Date curDate = new Date();
        fileInfo.setDeleteFlag(0);  // deleteFlag를 1로 설정
        fileInfo.setCreateDate(curDate);
        fileInfo.setModifyDate(curDate);

        SqlParameterSource param = new BeanPropertySqlParameterSource(fileInfo);

        return insertAction.executeAndReturnKey(param).intValue();
    }

    public String selectSaveFileNameByFileid(int fileId){
        Map<String, Integer> param = Collections.singletonMap("fileId", fileId);

        return jdbc.queryForObject(FileInfoDaoSqls.SELECT_SAVE_FILE_NAME_BY_FILE_ID, param, String.class);
    }

    public Object selectByFileId(int fileId, Class<?> requiredType){
        Map<String, Integer> params = new HashMap<>();
        params.put("fileId", fileId);

        RowMapper requiredRowMapper = BeanPropertyRowMapper.newInstance(requiredType);
        return jdbc.queryForObject(FileInfoDaoSqls.SELECT_BY_ID, params, requiredRowMapper);
    }
}

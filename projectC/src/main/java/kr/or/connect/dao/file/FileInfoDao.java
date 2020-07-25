package kr.or.connect.dao.file;

import kr.or.connect.dao.file.sqls.FileInfoDaoSqls;
import kr.or.connect.dto.file.FileInfo;
import org.springframework.dao.EmptyResultDataAccessException;
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
    private final SimpleJdbcInsert fileInfoInsertAction;

    public FileInfoDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.fileInfoInsertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("file_info")
                .usingGeneratedKeyColumns("id");
    }

    public int insertFileInfo(FileInfo fileInfo){
        Date curDate = new Date();
        fileInfo.setDeleteFlag(0);  // deleteFlag를 1로 설정
        fileInfo.setCreateDate(curDate);
        fileInfo.setModifyDate(curDate);

        SqlParameterSource param = new BeanPropertySqlParameterSource(fileInfo);

        return fileInfoInsertAction.executeAndReturnKey(param).intValue();
    }

    public String selectSaveFileNameByFileid(int fileId){
        Map<String, Integer> param = Collections.singletonMap("fileId", fileId);

        try{
            return jdbc.queryForObject(FileInfoDaoSqls.SELECT_SAVE_FILE_NAME_BY_FILE_ID, param, String.class);
        }catch(EmptyResultDataAccessException e){   // JdbcTemplate, queryForInt, queryForLong, queryForObject의 조회 결과가 없거나 하나 이상의 row인 경우 IncorrectResultSizeDataAccessException(row가 하나 이상인 경우)
            return null;                            // 또는 EmptyResultDataAccessException(row가 없는 경우)이 발생한다.
        }
    }

    public Object selectFileInfoByFileId(int fileId, Class<?> requiredType){
        Map<String, Integer> params = Collections.singletonMap("fileId", fileId);

        RowMapper requiredTypeRowMapper = BeanPropertyRowMapper.newInstance(requiredType);

        try{
            return jdbc.queryForObject(FileInfoDaoSqls.SELECT_BY_ID, params, requiredTypeRowMapper);
        }catch(EmptyResultDataAccessException e){   // JdbcTemplate, queryForInt, queryForLong, queryForObject의 조회 결과가 없거나 하나 이상의 row인 경우 IncorrectResultSizeDataAccessException(row가 하나 이상인 경우)
            return null;                            // 또는 EmptyResultDataAccessException(row가 없는 경우)이 발생한다.
        }
    }
}

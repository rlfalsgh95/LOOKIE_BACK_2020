package kr.or.connect.dao.category;

import kr.or.connect.dto.category.CategoryDetail;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

import static kr.or.connect.dao.category.sqls.CategoryDaoSqls.GET_CATEGORY_COUNT;
import static kr.or.connect.dao.category.sqls.CategoryDaoSqls.SELECT_ALL_CATEGORY;

@Repository
public class CategoryDao {
    private final NamedParameterJdbcTemplate jdbc;
    private final RowMapper<CategoryDetail> categoryRowMapper = BeanPropertyRowMapper.newInstance(CategoryDetail.class);

    public CategoryDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<CategoryDetail> selectAllCategory(){
        return jdbc.query(SELECT_ALL_CATEGORY, categoryRowMapper);
    }

    public int getCategoryCount(){  // category 테이블의 행의 개수 반환.
        return jdbc.queryForObject(GET_CATEGORY_COUNT, Collections.<String, Object>emptyMap(), Integer.class);
    }
}

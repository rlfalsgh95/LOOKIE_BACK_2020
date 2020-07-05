package kr.or.connect.dao;

import kr.or.connect.dto.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

import static kr.or.connect.dao.sqls.CategoryDaoSqls.GET_COUNT;
import static kr.or.connect.dao.sqls.CategoryDaoSqls.SELLECT_ALL;

@Service
public class CategoryDao {
    @Autowired
    DataSource dataSource;

    private NamedParameterJdbcTemplate jdbc;
    private RowMapper<Category> rowMapper = BeanPropertyRowMapper.newInstance(Category.class);

    public CategoryDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Category> selectAll(){
        return jdbc.query(SELLECT_ALL, rowMapper);
    }

    public int getCount(){
        return jdbc.queryForObject(GET_COUNT, Collections.<String, Object>emptyMap(), Integer.class);
    }
}

package kr.or.connect.dao.security;

import kr.or.connect.dto.security.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Map;

import static kr.or.connect.dao.security.sqls.UserDaoSqls.SELECT_USER_BY_EMAIL;

@Repository
public class UserDao {
    private NamedParameterJdbcTemplate jdbc;
    private RowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);

    public UserDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public User getUserByEmail(String email){
        Map<String, String> params = Collections.singletonMap("email", email);

        try{
            return jdbc.queryForObject(SELECT_USER_BY_EMAIL, params, rowMapper);
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }
}

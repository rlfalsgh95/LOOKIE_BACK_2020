package kr.or.connect.dao.user;

import kr.or.connect.dao.user.sqls.UserDaoSqls;
import kr.or.connect.dto.user.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Map;

@Repository
public class UserDao {
    private final NamedParameterJdbcTemplate jdbc;
    private final RowMapper<User> userRowMapper = BeanPropertyRowMapper.newInstance(User.class);

    public UserDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public User selectUserByEmail(String email){
        Map<String, String> params = Collections.singletonMap("email", email);

        try{
            return jdbc.queryForObject(UserDaoSqls.SELECT_USER_BY_EMAIL, params, userRowMapper);
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }
}

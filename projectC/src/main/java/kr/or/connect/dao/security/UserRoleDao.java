package kr.or.connect.dao.security;

import kr.or.connect.dto.security.User;
import kr.or.connect.dto.security.UserRole;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static kr.or.connect.dao.security.sqls.UserRoleDaoSqls.SELECT_USER_ROLES_BY_EMAIL;


@Repository
public class UserRoleDao {
    private NamedParameterJdbcTemplate jdbc;
    private RowMapper<UserRole> rowMapper = BeanPropertyRowMapper.newInstance(UserRole.class);

    public UserRoleDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<UserRole> getUserRolesByEmail(String email){
        Map<String, String> params = Collections.singletonMap("email", email);

        return jdbc.query(SELECT_USER_ROLES_BY_EMAIL, params, rowMapper);
    }
}

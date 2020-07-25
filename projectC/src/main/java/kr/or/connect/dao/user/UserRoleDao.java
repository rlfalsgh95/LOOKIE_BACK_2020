package kr.or.connect.dao.user;

import kr.or.connect.dao.user.sqls.UserRoleDaoSqls;
import kr.or.connect.dto.user.UserRole;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class UserRoleDao {
    private final NamedParameterJdbcTemplate jdbc;
    private final RowMapper<UserRole> userRoleRowMapper = BeanPropertyRowMapper.newInstance(UserRole.class);

    public UserRoleDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<UserRole> selectUserRolesByEmail(String email){
        Map<String, String> params = Collections.singletonMap("email", email);

        return jdbc.query(UserRoleDaoSqls.SELECT_USER_ROLES_BY_EMAIL, params, userRoleRowMapper);
    }
}

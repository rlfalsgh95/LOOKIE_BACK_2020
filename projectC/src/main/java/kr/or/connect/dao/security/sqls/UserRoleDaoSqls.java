package kr.or.connect.dao.security.sqls;

public class UserRoleDaoSqls {
    public static final String SELECT_USER_ROLES_BY_EMAIL = "SELECT u_role.id, u_role.user_id, u_role.role_name " +
                                                           "FROM user u JOIN user_role u_role ON u.id = u_role.user_id " +
                                                           "WHERE u.email = :email";
}

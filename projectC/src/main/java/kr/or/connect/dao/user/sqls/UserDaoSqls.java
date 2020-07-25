package kr.or.connect.dao.user.sqls;

public class UserDaoSqls {
    public static final String SELECT_USER_BY_EMAIL = "SELECT id, name, password, email, phone, create_date, modify_date " +
                                                     "FROM user " +
                                                     "WHERE email = :email";
}

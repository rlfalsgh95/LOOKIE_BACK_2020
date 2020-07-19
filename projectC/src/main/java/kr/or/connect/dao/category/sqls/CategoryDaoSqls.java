package kr.or.connect.dao.category.sqls;

public class CategoryDaoSqls {
    public static final String SELECT_ALL_CATEGORY = "SELECT c.id, c.name, count(*) as count " +
                                                     "FROM category c JOIN product p ON c.id = p.category_id " +
                                                                     "JOIN display_info d_info ON p.id = d_info.product_id " +
                                                     "GROUP BY c.id";

    public static final String GET_CATEGORY_COUNT = "SELECT count(*) " +
                                                    "FROM category";
}



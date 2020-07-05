package kr.or.connect.dao.sqls;

public class CategoryDaoSqls {
    public static final String SELLECT_ALL = "SELECT c.id, c.name, count(*) as count " +
                                             "FROM category c, product p, display_info d_info " +
                                             "WHERE c.id = p.category_id AND p.id = d_info.product_id " +
                                             "GROUP BY c.id";

    public static final String GET_COUNT = "SELECT count(*) " +
                                           "FROM category";
}

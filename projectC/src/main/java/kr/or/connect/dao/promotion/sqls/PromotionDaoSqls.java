package kr.or.connect.dao.promotion.sqls;

public class PromotionDaoSqls {
    public static final String SELECT_ALL_PROMOTION_DETAIL = "SELECT promotion.id, product.id AS product_id, category_id, name AS category_name, description, file_id " +
                                                             "FROM category category JOIN product product ON category.id = product.category_id " +
                                                                                    "JOIN promotion promotion ON product.id = promotion.product_id " +
                                                                                    "JOIN product_image product_image ON product.id = product_image.product_id " +
                                                             "WHERE product_image.type = 'th'";

    public static final String GET_PROMOTION_COUNT = "SELECT count(*) " +
                                                     "FROM promotion";
}

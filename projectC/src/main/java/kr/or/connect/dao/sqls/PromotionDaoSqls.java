package kr.or.connect.dao.sqls;

public class PromotionDaoSqls {
    public static final String SELECT_ALL = "SELECT promotion.id, product.id AS product_id, category_id, name AS category_name, description, file_id " +
                                      "FROM category category, product product, promotion promotion, product_image product_image " +
                                      "WHERE category.id = product.category_id AND product.id = promotion.product_id AND product.id = product_image.product_id AND product_image.type = 'th'";

    public static final String GET_COUNT = "SELECT count(*) FROM promotion";
}

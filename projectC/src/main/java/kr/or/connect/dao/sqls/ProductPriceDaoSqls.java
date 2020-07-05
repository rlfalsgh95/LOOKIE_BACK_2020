package kr.or.connect.dao.sqls;

public class ProductPriceDaoSqls {
    public static final String SELECT_BY_PRODUCT_ID = "SELECT id, product_id, price_type_name, price, discount_rate, create_date, modify_date " +
                                                      "FROM product_price " +
                                                      "WHERE product_id = :productId";
}

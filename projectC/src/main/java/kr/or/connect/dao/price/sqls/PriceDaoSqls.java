package kr.or.connect.dao.price.sqls;

public class PriceDaoSqls {
    public static final String SELECT_PRICE_BY_RESERVATION_INFO_PRICE_ID = "SELECT id, reservation_info_id, product_price_id, count " +
                                                                           "FROM reservation_info_price " +
                                                                           "WHERE id = :reservationInfoPriceId";

    public static final String GET_SUM_BY_RESERVATION_INFO_ID = "SELECT SUM(count * price) " +
                                                                "FROM reservation_info_price r JOIN product_price p ON r.product_price_id = p.id " +
                                                                "WHERE reservation_info_id = :reservationInfoId";
}

package kr.or.connect.dao.reservation.sqls;

public class ReservationDaoSqls {
    public static final String SELECT_RESERVATION_INFO_BY_RESERVATION_INFO_ID = "SELECT id, product_id, cancel_flag, display_info_id, user_id, reservation_date, modify_date, create_date " +
                                                                                "FROM reservation_info " +
                                                                                "WHERE id = :reservationInfoId";

    public static final String SELECT_RESERVATION_DETAIL_INFO_BY_USER_ID = "SELECT r_info.id, product.id as product_id, display_info_id, user_id, reservation_date, cancel_flag, r_info.create_date, r_info.modify_date, description as product_description, content as product_content " +
                                                                           "FROM reservation_info r_info JOIN product product ON product.id = r_info.product_id " +
                                                                           "WHERE r_info.user_id = :userId";

    public static final String SELECT_ALL_RESERVATION_INFO_ID_OF_USER = "SELECT id as reservation_id " +
                                                                        "FROM reservation_info " +
                                                                        "WHERE user_id = :userId";

    public static final String CANCEL_RESERVATION_BY_RESERVATION_INFO_ID = "UPDATE reservation_info " +
                                                                           "SET cancel_flag = :cancelFlag " +
                                                                           "WHERE id = :reservationInfoId";

    public static final String UPDATE_MODIFY_DATE_BY_RESERVATION_INFO_ID = "UPDATE reservation_info " +
                                                                           "SET modify_date = :modifyDate " +
                                                                           "WHERE id = :reservationInfoId";
}

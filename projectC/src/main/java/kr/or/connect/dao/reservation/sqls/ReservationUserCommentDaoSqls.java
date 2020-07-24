package kr.or.connect.dao.reservation.sqls;

public class ReservationUserCommentDaoSqls {
    private static final String LIMIT_CLAUSE = " LIMIT :start, :limit ";
    private static final String ORDER_BY = " ORDER BY id DESC ";
    private static final String BY_PRODUCT_ID = " WHERE product_id = :productId ";

    public static final String SELECT_RESERVATION_USER_COMMENT = "SELECT id, product_id, reservation_info_id, score, comment, create_date, modify_date " +
                                            "FROM reservation_user_comment" + ORDER_BY + LIMIT_CLAUSE;

    public static final String SELECT_RESERVATION_USER_COMMENT_BY_PRODUCT_ID = "SELECT id, product_id, reservation_info_id, score, comment, create_date, modify_date " +
                                                      "FROM reservation_user_comment " + BY_PRODUCT_ID + ORDER_BY + LIMIT_CLAUSE;

    public static final String GET_RESERVATION_USER_COMMENT_COUNT = "SELECT count(*) " +
                                                 "FROM reservation_user_comment ";

    public static final String GET_RESERVATION_USER_COMMENT_COUNT_BY_PRODUCT_ID = GET_RESERVATION_USER_COMMENT_COUNT + BY_PRODUCT_ID;

    public static final String GET_SCORE_AVG_BY_PRODUCT_ID = "SELECT avg(score) " +
                                               "FROM reservation_user_comment" + BY_PRODUCT_ID;

    public static final String SELECT_USER_COMMENT_IMAGE_DETAIL_BY_USER_COMMENT_ID = "SELECT r_user_comment_image.id, file_name, save_file_name, content_type, delete_flag, create_date, modify_date, reservation_info_id, reservation_user_comment_id " +
                                                                                     "FROM reservation_user_comment_image r_user_comment_image JOIN file_info f_info ON r_user_comment_image.file_id = f_info.id " +
                                                                                     "WHERE reservation_user_comment_id = :userCommentId";

}

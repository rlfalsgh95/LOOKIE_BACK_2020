package kr.or.connect.dao.sqls;

public class ReservationUserCommentDaoSqls {
    private static final String LIMIT_CLAUSE = " LIMIT :start, :limit";
    private static final String ORDER_BY = " ORDER BY id DESC";

    public static final String SELECT_ALL = "SELECT id, product_id, reservation_info_id, score, comment, create_date, modify_date " +
            "FROM reservation_user_comment" + ORDER_BY + LIMIT_CLAUSE;
    public static final String SELECT_BY_PRODUCT_ID = "SELECT id, product_id, reservation_info_id, score, comment, create_date, modify_date " +
            "FROM reservation_user_comment " +
            "WHERE product_id = :productId" + ORDER_BY + LIMIT_CLAUSE;

    public static final String GET_TOTAL_COUNT = "SELECT count(*) " +
            "FROM reservation_user_comment";

    public static final String GET_SCORE_AVG = "SELECT avg(score) FROM reservation_user_comment WHERE product_id = :productId";

    public static final String SELECT_COMMENT_IMAGE_FILE_NAME_BY_COMMENT_ID = "SELECT f_info.file_name " +
                                                                              "FROM reservation_user_comment_image r_user_comment_image, file_info f_info " +
                                                                              "WHERE r_user_comment_image.file_id = f_info.id and r_user_comment_image.reservation_user_comment_id = :reservationUserCommentId";

}

package kr.or.connect.dao.display.sqls;

public class DisplayInfoImageDaoSql {
    public static final String SELECT_DISPLAY_INFO_IMAGE_INFO_BY_DISPLAY_INFO_ID = "SELECT id, display_info_id, file_id " +
                                                                                   "FROM display_info_image " +
                                                                                   "WHERE display_info_id = :displayInfoId";
}

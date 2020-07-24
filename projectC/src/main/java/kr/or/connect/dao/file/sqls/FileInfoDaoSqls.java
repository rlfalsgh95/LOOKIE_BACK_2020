package kr.or.connect.dao.file.sqls;

public class FileInfoDaoSqls {
    public static final String SELECT_BY_ID = "SELECT id, file_name, save_file_name, content_type, delete_flag, create_date, modify_date " +
                                              "FROM file_info " +
                                              "WHERE id = :fileId";

    public static final String SELECT_SAVE_FILE_NAME_BY_FILE_ID = "SELECT save_file_name " +
                                                                  "FROM file_info " +
                                                                  "WHERE id = :fileId";
}

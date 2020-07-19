package kr.or.connect.dao.file.sqls;

public class FileInfoDaoSqls {
    public static final String SELECT_BY_ID = "SELECT file_name, save_file_name, content_type, delete_flag, create_date, modify_date " +
                                              "FROM file_info " +
                                              "WHERE id = :fileId";
}

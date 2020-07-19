package kr.or.connect.dao.product.sqls;

public class ProductImageDaoSqls {
    public static final String SELECT_PRODUCT_IMAGE_INFO_BY_PRODUCT_ID = "SELECT id as product_image_id, type, file_id FROM product_image WHERE product_id = :productId";
}

package kr.or.connect.dao.product;

import kr.or.connect.dao.product.sqls.ProductImageDaoSqls;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class ProductImageDao {
    private final NamedParameterJdbcTemplate jdbc;

    public ProductImageDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Map<String, Object>> selectProductImageInfosByProductId(int productId){
        Map<String, Integer> params = Collections.singletonMap("productId", productId);

        return jdbc.queryForList(ProductImageDaoSqls.SELECT_PRODUCT_IMAGE_INFO_BY_PRODUCT_ID, params);
    }
}

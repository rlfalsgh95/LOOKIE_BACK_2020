package kr.or.connect.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kr.or.connect.dao.sqls.ProductImageDaoSqls.SELECT_PRODUCT_IMAGE_INFO_BY_PRODUCT_ID;

@Repository
public class ProductImageDao {
    private NamedParameterJdbcTemplate jdbc;

    public ProductImageDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Map<String, Object>> selectProductImageInfosByProductId(int productId){
        Map<String, Integer> params = new HashMap<>();
        params.put("productId", productId);
        return jdbc.queryForList(SELECT_PRODUCT_IMAGE_INFO_BY_PRODUCT_ID, params);
    }
}

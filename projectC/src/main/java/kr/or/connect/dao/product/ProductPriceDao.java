package kr.or.connect.dao.product;

import kr.or.connect.dao.product.sqls.ProductPriceDaoSqls;
import kr.or.connect.dto.product.ProductPrice;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class ProductPriceDao {
    private final NamedParameterJdbcTemplate jdbc;
    private final RowMapper<ProductPrice> rowMapper = BeanPropertyRowMapper.newInstance(ProductPrice.class);

    public ProductPriceDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<ProductPrice> selectByProductId(int productId){
        Map<String, Integer> params = Collections.singletonMap("productId", productId);

        return jdbc.query(ProductPriceDaoSqls.SELECT_BY_PRODUCT_ID, params, rowMapper);
    }
}

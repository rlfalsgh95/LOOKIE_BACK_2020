package kr.or.connect.service.product.impl;

import kr.or.connect.dao.product.ProductPriceDao;
import kr.or.connect.dto.product.ProductPrice;
import kr.or.connect.service.product.ProductPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductPriceServiceImpl implements ProductPriceService {
    @Autowired
    ProductPriceDao productPriceDao;

    @Override
    public List<ProductPrice> selectByProductId(int productId) {
        return productPriceDao.selectByProductId(productId);
    }
}

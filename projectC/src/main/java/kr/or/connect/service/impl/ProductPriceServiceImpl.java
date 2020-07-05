package kr.or.connect.service.impl;

import kr.or.connect.dao.ProductPriceDao;
import kr.or.connect.dto.ProductPrice;
import kr.or.connect.service.ProductPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductPriceServiceImpl implements ProductPriceService {
    @Autowired
    ProductPriceDao productPriceDao;

    @Override
    public List<ProductPrice> selectByProductId(int productId) {
        return productPriceDao.selectByProductId(productId);
    }
}

package kr.or.connect.service;

import kr.or.connect.dto.ProductPrice;

import java.util.List;

public interface ProductPriceService {
    public List<ProductPrice> selectByProductId(int productId);
}

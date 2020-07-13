package kr.or.connect.service;

import kr.or.connect.dto.ProductPrice;

import java.util.List;

public interface ProductPriceService {
    List<ProductPrice> selectByProductId(int productId);
}

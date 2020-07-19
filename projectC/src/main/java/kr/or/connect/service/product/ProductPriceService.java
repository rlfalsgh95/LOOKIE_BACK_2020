package kr.or.connect.service.product;

import kr.or.connect.dto.ProductPrice;

import java.util.List;

public interface ProductPriceService {
    List<ProductPrice> selectByProductId(int productId);
}

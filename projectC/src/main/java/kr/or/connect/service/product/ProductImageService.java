package kr.or.connect.service.product;

import kr.or.connect.dto.product.ProductImage;

import java.util.List;

public interface ProductImageService {
    List<ProductImage> selectProductImagesByProductId(int productId);
}

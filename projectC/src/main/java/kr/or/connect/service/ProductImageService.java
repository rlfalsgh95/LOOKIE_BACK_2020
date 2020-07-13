package kr.or.connect.service;

import kr.or.connect.dto.ProductImage;

import java.util.List;

public interface ProductImageService {
    List<ProductImage> selectProductImagesByProductId(int productId);
}

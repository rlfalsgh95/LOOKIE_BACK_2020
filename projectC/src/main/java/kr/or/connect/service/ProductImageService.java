package kr.or.connect.service;

import kr.or.connect.dto.ProductImage;

import java.util.List;

public interface ProductImageService {
    public List<ProductImage> selectProductImagesByProductId(int productId);
}

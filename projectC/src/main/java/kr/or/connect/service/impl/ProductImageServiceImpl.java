package kr.or.connect.service.impl;

import kr.or.connect.dao.ProductImageDao;
import kr.or.connect.dto.ProductImage;
import kr.or.connect.service.FileInfoService;
import kr.or.connect.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProductImageServiceImpl implements ProductImageService {
    @Autowired
    ProductImageDao productImageDao;

    @Autowired
    FileInfoService fileInfoService;

    public List<ProductImage> selectProductImagesByProductId(int productId){
        List<ProductImage> productImageList = new ArrayList<>();

        List<Map<String, Object>> productImageInfos = productImageDao.selectProductImageInfosByProductId(productId);

        for (Map<String, Object> productImageInfo: productImageInfos){
            int productImageId = (int)productImageInfo.get("product_image_id");
            int fileInfoId = (int)productImageInfo.get("file_id");
            String type = (String)productImageInfo.get("type");

            ProductImage productImage = (ProductImage)fileInfoService.selectByFileId((int)productImageInfo.get("file_id"), ProductImage.class);

            productImage.setProductId(productId);
            productImage.setProductImageId(productImageId);
            productImage.setFileInfoId(fileInfoId);
            productImage.setType(type);
            productImageList.add(productImage);
        }

        return productImageList;
    }
}

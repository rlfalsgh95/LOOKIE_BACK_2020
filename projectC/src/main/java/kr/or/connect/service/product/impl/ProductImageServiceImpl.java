package kr.or.connect.service.product.impl;

import kr.or.connect.dao.file.FileInfoDao;
import kr.or.connect.dao.product.ProductImageDao;
import kr.or.connect.dto.product.ProductImage;
import kr.or.connect.service.product.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class ProductImageServiceImpl implements ProductImageService {
    @Autowired
    ProductImageDao productImageDao;

    @Autowired
    FileInfoDao fileInfoDao;

    public List<ProductImage> selectProductImagesByProductId(int productId){
        List<ProductImage> productImageList = new ArrayList<>();

        List<Map<String, Object>> productImageInfos = productImageDao.selectProductImageInfosByProductId(productId);

        for (Map<String, Object> productImageInfo: productImageInfos){
            int productImageId = (int)productImageInfo.get("product_image_id");
            int fileInfoId = (int)productImageInfo.get("file_id");
            String type = (String)productImageInfo.get("type");

            ProductImage productImage = (ProductImage)fileInfoDao.selectByFileId((int)productImageInfo.get("file_id"), ProductImage.class);

            productImage.setProductId(productId);
            productImage.setProductImageId(productImageId);
            productImage.setFileInfoId(fileInfoId);
            productImage.setType(type);
            productImageList.add(productImage);
        }

        return productImageList;
    }
}

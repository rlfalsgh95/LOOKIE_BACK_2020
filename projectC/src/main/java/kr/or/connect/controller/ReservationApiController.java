package kr.or.connect.controller;

import kr.or.connect.dto.*;
import kr.or.connect.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api")
public class ReservationApiController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    DisplayInfoService displayInfoService;

    @Autowired
    PromotionService promotionService;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    ReservationUserCommentService reservationUserCommentService;

    @Autowired
    ProductPriceService productPriceService;

    @Autowired
    DisplayInfoImageService displayInfoImageService;

    @GetMapping(path = "/categories")
    public Map<String, Object> getCategories(){
        Map<String, Object> resultMap = new LinkedHashMap<>();  // 응답 결과에서 Map의 요소가 순서대로 출력되도록 LinkedHashMap을 사용

        List<Category> items = categoryService.selectAll();
        int size = categoryService.getCount();

        resultMap.put("size", size);
        resultMap.put("items", items);

        return resultMap;
    }

    @GetMapping(path = "/displayinfos")
    public Map<String, ?> getDisplayinfos(@RequestParam(name ="categoryId", required = false, defaultValue = "0") int categoryId, @RequestParam (name = "start", required = false, defaultValue = "1") int start){
        Map<String, Object> resultMap = new LinkedHashMap<>();
        List<DisplayInfo> displayInfos = null;
        int displayCount = 0;
        int totalCount = 0;

        try{
             displayInfos = displayInfoService.selectDisplayInfos(start - 1, categoryId);
             displayCount = displayInfos.size();

             if(categoryId == 0)
                 totalCount = displayInfoService.getTotalCount();
             else
                 totalCount = displayInfoService.getCountByCategoryId(categoryId);

            resultMap.put("totalCount", totalCount);
            resultMap.put("productCount", displayCount);
            resultMap.put("products", displayInfos);
        }catch(Exception e){
            e.printStackTrace();
        }

        return resultMap;
    }

    @GetMapping(path = "/displayinfos/{displayId}")
    public Map<String, Object> getDisplayinfosById(@PathVariable (name = "displayId") int displayId){
        Map<String, Object> resultMap = new LinkedHashMap<>();

        try{
            DisplayInfo displayInfo = displayInfoService.selectDisplayInfoById(displayId);

            int productId = displayInfo.getId();
            List<ProductImage> productImageList = productImageService.selectProductImagesByProductId(productId);
            //int avgScore = reservationUserCommentService.getScore(productId);

            List<ProductPrice> productPriceList = productPriceService.selectByProductId(productId);
            List<DisplayInfoImage> displayInfoImageList = displayInfoImageService.selectDisplayInfoImagesByDisplayInfoId(displayId);

            resultMap.put("product", displayInfo);
            resultMap.put("productImages", productImageList);
            resultMap.put("displayInfoImages", displayInfoImageList);
            resultMap.put("productPrices", productPriceList);
            //resultMap.put("avgScore", avgScore);
        }catch(Exception e){
            e.printStackTrace();
        }

        return resultMap;
    }

    @GetMapping(path = "/promotions")
    public Map<String, Object> getPromotions(){
        Map<String, Object> resultMap = new LinkedHashMap<>();

        int size = promotionService.getCount();
        List<Promotion> promotions = promotionService.selectAll();

        resultMap.put("size", size);
        resultMap.put("items", promotions);

        return resultMap;
    }

    @GetMapping(path = "/comments")
    public Map<String, Object> getComments(@RequestParam(name ="productId", required = false, defaultValue = "-1") int productId, @RequestParam (name = "start", required = false, defaultValue = "1") int start){
        Map<String, Object> resultMap = new LinkedHashMap<>();
        List<ReservationUserComment> commentList = null;

        try{
            int totalCount = reservationUserCommentService.getTotalCount();

            if(productId == -1) {
                commentList = reservationUserCommentService.selectAll(start - 1);
            }else{
                commentList = reservationUserCommentService.selectByProductId(productId, start - 1);
            }
            int commentCount = commentList.size();

            resultMap.put("totalCount", totalCount);
            resultMap.put("commentCount", commentCount);
            resultMap.put("reservationUserComments", commentList);
        }catch(Exception e){
            e.printStackTrace();
        }

        return resultMap;
    }

}


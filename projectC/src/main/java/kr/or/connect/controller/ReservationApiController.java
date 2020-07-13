package kr.or.connect.controller;

import io.swagger.annotations.ApiOperation;
import kr.or.connect.dto.*;
import kr.or.connect.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @ApiOperation(value = "카테고리 목록 구하기")
    @GetMapping(path = "/categories")
    public Map<String, Object> getCategories(){
        Map<String, Object> resultMap = new LinkedHashMap<>();  // 응답 결과에서 Map의 요소가 순서대로 출력되도록 LinkedHashMap을 사용

        int size = categoryService.getCount();  // 카테고리의 개수
        List<Category> items = categoryService.selectAllCategory(); // 카테고리 목록 select

        /* 결과 응답 */
        resultMap.put("size", size);
        resultMap.put("items", items);

        /* category 테이블이 비어있으면, 아래 내용이 출력됨.
        {
            "size": 0,
            "items":[]
        }
        */
        return resultMap;
    }

    @ApiOperation(value = "상품 목록 구하기")
    @GetMapping(path = "/displayinfos")
    public Map<String, ?> getDisplayinfos(@RequestParam(name ="categoryId", required = false, defaultValue = "0") int categoryId, @RequestParam (name = "start", required = false, defaultValue = "1") int start){  //
        Map<String, Object> resultMap = new LinkedHashMap<>();

        List<DisplayInfo> displayInfos = new ArrayList<>();
        int displayCount = 0;
        int totalCount = 0;

        if(categoryId == 0) // start의 값과는 상관없이 해당 카테고리 또는 전체 상품의 개수를 응답에 포함시킴.
            totalCount = displayInfoService.getTotalCount();
        else
            totalCount = displayInfoService.getCountByCategoryId(categoryId);

        if (start >= 1) // start가 1보다 작으면, limit 절에서 error를 발생시키므로, db에서 조회하지 않고 기본값으로 응답함.
        {
            displayInfos = displayInfoService.selectDisplayInfos(start - 1, categoryId);
            displayCount = displayInfos.size();
        }

        /* 결과 응답 */
        resultMap.put("totalCount", totalCount);
        resultMap.put("productCount", displayCount);
        resultMap.put("products", displayInfos);

        /* display_info 테이블이 비어있으면, 아래 내용이 출력됨.
        {
            "totalCount": 0,
            "productCount": 0,
            "products":[]
        }
        */

        return resultMap;
    }

    @ApiOperation(value = "프로모션 정보 구하기")
    @GetMapping(path = "/promotions")
    public Map<String, Object> getPromotions(){
        Map<String, Object> resultMap = new LinkedHashMap<>();

        int size = promotionService.getCount();
        List<Promotion> promotions = promotionService.selectAll();

        /* 결과 응답 */
        resultMap.put("size", size);
        resultMap.put("items", promotions);

        /* promotion 테이블이 비어있으면, 아래 내용이 출력됨.
        {
            "size": 0,
            "items":[]
        }
        */

        return resultMap;
    }
    
    @ApiOperation(value = "전시 정보 구하기")
    @GetMapping(path = "/displayinfos/{displayId}")
    public Map<String, Object> getDisplayinfosById(@PathVariable (name = "displayId") int displayId){
        Map<String, Object> resultMap = new LinkedHashMap<>();
        List<ProductImage> productImageList = new ArrayList<>();
        List<ProductPrice> productPriceList = new ArrayList<>();
        List<DisplayInfoImage> displayInfoImageList = new ArrayList<>();
        Integer avgScore = null;

        DisplayInfo displayInfo = displayInfoService.selectDisplayInfoById(displayId);

        if(displayInfo != null){    // displayId에 해당 하는 정보가 있는 경우에만 나머지 정보를 조회
            int productId = displayInfo.getId();
            productImageList = productImageService.selectProductImagesByProductId(productId);
            avgScore = reservationUserCommentService.getScore(productId);   // 반환 타입을 Integer로 하여, score의 평균을 구할 수 없을 경우 null을 반환하도록 함.

            productPriceList = productPriceService.selectByProductId(productId);
            displayInfoImageList = displayInfoImageService.selectDisplayInfoImagesByDisplayInfoId(displayId);
        }

        /* 결과 응답 */
        resultMap.put("product", displayInfo);
        resultMap.put("productImages", productImageList);
        resultMap.put("displayInfoImages", displayInfoImageList);
        resultMap.put("avgScore", avgScore);
        resultMap.put("productPrices", productPriceList);

        return resultMap;
    }

    @ApiOperation(value = "댓글 목록 구하기")
    @GetMapping(path = "/comments")
    public Map<String, Object> getComments(@RequestParam(name ="productId", required = false) Integer productId, @RequestParam (name = "start", required = false, defaultValue = "1") int start){
        Map<String, Object> resultMap = new LinkedHashMap<>();

        List<ReservationUserComment> commentList = new ArrayList<>();
        int totalCount = 0;
        int commentCount = 0;

        if(productId == null)   // url에 productId의 값을 명시하지 않은 경우
            totalCount = reservationUserCommentService.getTotalCount(); // totalCount는 전체 comment의 개수
        else
            totalCount = reservationUserCommentService.getCountByProductId(productId);  // totalCount는 productId의 댓글의 개수

        if(start >= 1){ // start가 1보다 작으면, limit 절에서 error를 발생시키므로, db에서 조회하지 않고 기본값으로 응답함.
            if(productId == null) { // url에 productId의 값을 명시하지 않은 경우
                commentList = reservationUserCommentService.selectComments(start - 1);
            }else{
                commentList = reservationUserCommentService.selectCommentsByProductId(productId, start - 1);
            }
            commentCount = commentList.size();
        }

        /* 결과 응답 */
        resultMap.put("totalCount", totalCount);
        resultMap.put("commentCount", commentCount);
        resultMap.put("reservationUserComments", commentList);

        return resultMap;
    }
}

/*
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The requested information could not be found.")
class InformationNotFoundException extends RuntimeException{
}
*/


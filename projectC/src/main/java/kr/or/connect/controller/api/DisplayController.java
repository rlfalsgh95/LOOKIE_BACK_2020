package kr.or.connect.controller.api;

import io.swagger.annotations.ApiOperation;
import kr.or.connect.dto.display.DisplayDetailInfo;
import kr.or.connect.dto.display.DisplayInfoImage;
import kr.or.connect.dto.product.ProductImage;
import kr.or.connect.dto.product.ProductPrice;
import kr.or.connect.service.display.DisplayInfoImageService;
import kr.or.connect.service.display.DisplayInfoService;
import kr.or.connect.service.product.ProductImageService;
import kr.or.connect.service.product.ProductPriceService;
import kr.or.connect.service.reservation.ReservationUserCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DisplayController {
    @Autowired
    DisplayInfoService displayInfoService;

    @Autowired
    DisplayInfoImageService displayInfoImageService;

    @Autowired
    ProductPriceService productPriceService;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    ReservationUserCommentService reservationUserCommentService;

    @ApiOperation(value = "상품 목록 구하기")
    @GetMapping(path = "/displayInfos")
    public Map<String, ?> getDisplayinfos(@RequestParam(name = "categoryId", required = false, defaultValue = "0") int categoryId,
                                          @RequestParam(name = "start", required = false, defaultValue = "1") int start) {
        Map<String, Object> resultMap = new LinkedHashMap<>();

        List<DisplayDetailInfo> displayDetailInfos = new ArrayList<>();
        int displayCount = 0;
        int totalCount = 0;

        if (categoryId == 0) // categoryId가 0이면, start의 값과는 상관없이 전체 상품의 개수를 응답에 포함시킴.
            totalCount = displayInfoService.getDisplayInfoCount();
        else    // categoryId가 0이 아니면 해당 카테고리의 상품 개수를 응답에 포함시킴.
            totalCount = displayInfoService.getDisplayInfoCountByCategoryId(categoryId);

        if (start >= 1) // 메서드의 인자로 "start - 1"을 전달하기 때문에 start가 1보다 작으면, SQL문의 LIMIT 절에서 error를 발생시키므로, db에서 조회하지 않고 totalCount를 제외한 나머지 값을 기본값으로 응답함.
        {
            displayDetailInfos = displayInfoService.selectDisplayDetailInfosByCategoryId(start - 1, categoryId);    // categoryId가 '0'이라면 전체를 조회, 0이 아니라면 해당 category를 조회
            displayCount = displayDetailInfos.size();
        }

        /* 결과 응답 */
        resultMap.put("totalCount", totalCount);
        resultMap.put("productCount", displayCount);
        resultMap.put("products", displayDetailInfos);

        /* display_info 테이블이 비어있으면, 아래 내용이 출력됨.
        {
            "totalCount": 0,
            "productCount": 0,
            "products":[]
        }
        */

        return resultMap;
    }

    @ApiOperation(value = "전시 정보 구하기")
    @GetMapping(path = "/displayInfos/{displayId}")
    public Map<String, Object> getDisplayinfosById(@PathVariable(name = "displayId") int displayId) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        List<ProductImage> productImageList = new ArrayList<>();
        List<ProductPrice> productPriceList = new ArrayList<>();
        List<DisplayInfoImage> displayInfoImageList = new ArrayList<>();
        Integer avgScore = null;

        DisplayDetailInfo displayDetailInfo = displayInfoService.selectDisplayInfoByDisplayId(displayId);

        if (displayDetailInfo != null) {    // displayId에 해당 하는 정보가 있는 경우에만 나머지 정보를 조회
            int productId = displayDetailInfo.getId();
            productImageList = productImageService.selectProductImagesByProductId(productId);
            avgScore = reservationUserCommentService.getScoreAvgByProductId(productId);   // 반환 타입을 Integer로 하여, score의 평균을 구할 수 없을 경우 null을 반환하도록 함.

            productPriceList = productPriceService.selectByProductId(productId);
            displayInfoImageList = displayInfoImageService.selectDisplayInfoImagesByDisplayInfoId(displayId);
        }

        /* 결과 응답 */
        resultMap.put("product", displayDetailInfo);
        resultMap.put("productImages", productImageList);
        resultMap.put("displayInfoImages", displayInfoImageList);
        resultMap.put("avgScore", avgScore);
        resultMap.put("productPrices", productPriceList);

        return resultMap;
    }
}

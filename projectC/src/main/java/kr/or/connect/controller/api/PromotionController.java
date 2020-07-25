package kr.or.connect.controller.api;

import io.swagger.annotations.ApiOperation;
import kr.or.connect.dto.promotion.PromotionDetail;
import kr.or.connect.service.promotion.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PromotionController {
    @Autowired
    PromotionService promotionService;

    @ApiOperation(value = "프로모션 정보 구하기")
    @GetMapping(path = "/promotions")
    public Map<String, Object> getPromotions() {
        Map<String, Object> resultMap = new LinkedHashMap<>();

        int size = promotionService.getPromotionCount();
        List<PromotionDetail> promotionDetails = promotionService.selectAllPromotionDetail();

        /* 결과 응답 */
        resultMap.put("size", size);
        resultMap.put("items", promotionDetails);

        /* promotion 테이블이 비어있으면, 아래 내용이 출력됨.
        {
            "size": 0,
            "items":[]
        }
        */
        return resultMap;
    }
}

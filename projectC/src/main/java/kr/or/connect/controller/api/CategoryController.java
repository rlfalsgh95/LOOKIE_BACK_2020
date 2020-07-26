package kr.or.connect.controller.api;

import io.swagger.annotations.ApiOperation;
import kr.or.connect.dto.category.CategoryDetail;
import kr.or.connect.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @ApiOperation(value = "카테고리 목록 구하기")
    @GetMapping(path = "/categories")
    public Map<String, Object> getCategories() {
        Map<String, Object> resultMap = new LinkedHashMap<>();  // 응답 결과에서 Map의 요소가 순서대로 출력되도록 LinkedHashMap을 사용

        int size = categoryService.getCategoryCount();  // category 테이블의 행 개수 반환
        List<CategoryDetail> items = categoryService.selectAllCategory(); // 카테고리 목록 select (product, display_info와 Join하여 해당 category에 포함된 전시 상품의 수도 조회)

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
}



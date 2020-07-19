package kr.or.connect.controller;

import io.swagger.annotations.ApiOperation;
import kr.or.connect.dto.*;
import kr.or.connect.dto.security.UserEntity;
import kr.or.connect.service.category.CategoryService;
import kr.or.connect.service.display.DisplayInfoImageService;
import kr.or.connect.service.display.DisplayInfoService;
import kr.or.connect.service.product.ProductImageService;
import kr.or.connect.service.product.ProductPriceService;
import kr.or.connect.service.promotion.PromotionService;
import kr.or.connect.service.reservation.ReservationService;
import kr.or.connect.service.reservation.ReservationUserCommentService;
import kr.or.connect.service.user.UserDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    UserDbService userDbService;

    @Autowired
    ReservationService reservationService;

    @ApiOperation(value = "예약 등록하기")
    @PostMapping("/reservation")
    public Map<String, Object> reservation(@RequestBody Map<String, Object> requestParam, Principal principal){
        String userEmail = principal.getName(); // principal.getName()는 유저의 로그인 시 사용한 아이디
        UserEntity userEntity = userDbService.getUser(userEmail);

        ArrayList<Map<String, Object>> prices = null;
        Map reservationResult = new HashMap();
        
        Integer reservationUserId = (Integer) requestParam.get("userId");  // 요청 Body의 userId (실제 로그인한 사용자의 userId와 다를 수 있다.)

        if(reservationUserId != null && (userEntity.getUserId() == reservationUserId)){ // 유저의 DB상 userId와 예약 정보의 userId가 동일한 경우에만 예약을 진행.
            Integer productId = (Integer)requestParam.get("productId");
            Integer displayInfoId = (Integer)requestParam.get("displayInfoId");

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
            Date reservationYearMonthDay = null;

            try{
                reservationYearMonthDay = simpleDateFormat.parse(requestParam.get("reservationYearMonthDay").toString());
            }catch(ParseException e){   // 문자열을 Date 객체로 parsing하는 중에 error가 발생한다면 빈 Json 객체로 응답.
                e.printStackTrace();
                return reservationResult;
            }

            if(productId != null && displayInfoId != null && reservationYearMonthDay != null && requestParam.get("prices") != null){    // 예약에 필요한 요소들이 요청 Body에 포함되어 있다면 예약을 진행, 그렇지 않다면 빈 Json 객체로 응답.
                ReservationInfo reservationInfo = new ReservationInfo(productId, displayInfoId, reservationYearMonthDay, reservationUserId);

                prices = (ArrayList<Map<String, Object>>)requestParam.get("prices");

                reservationResult = reservationService.reservation(prices, reservationInfo);    // 예약을 진행.
            }
        }else{
            System.out.println("유저 userId :" + userEntity.getUserId() + " 요청 userId : " + requestParam.get("userId"));
            System.out.println("요청의 user와 일치하지 않아 예약을 진행할 수 없습니다.");
        }

        return reservationResult;
    }
    
    @ApiOperation(value = "주문 정보 구하기")
    @GetMapping("/reservation-infos")
    public Map<String, Object> reservationInfos(Principal principal){
        Map<String, Object> resultMap = new LinkedHashMap<>();

        String userEmail = principal.getName(); // principal.getName()는 유저의 로그인 시 사용한 아이디
        UserEntity userEntity = userDbService.getUser(userEmail);
        int userId = userEntity.getUserId();

        List<ReservationDetailInfo> items = reservationService.selectReservationDetailInfoByUserId(userId); // 예약 상품 정보 조회
        int size = items.size();

        resultMap.put("size", size);
        resultMap.put("items", items);

        return resultMap;
    }

    @ApiOperation(value = "예약 취소하기")
    @PutMapping("/reservation-cancel")
    public Map<String, Boolean> reservationCancel(@RequestBody Map<String, Integer> requestParam,
                                                  Principal principal){
        Map<String, Boolean> resultMap = new HashMap<>();

        int reservationInfoId = requestParam.get("id"); // 요청 Body의 id
        boolean isSelf = false; // 요청 Body의 예약 id에 해당 하는 예약 정보가 요청 유저의 것인지 나타내는 flag
        boolean cancelResult = false;   // 예약 취소 결과를 나타냄.

        String userEmail = principal.getName(); // principal.getName()는 유저의 로그인 시 사용한 아이디
        UserEntity userEntity = userDbService.getUser(userEmail);
        int userId = userEntity.getUserId();

        List<ReservationDetailInfo> reservationDetailInfos = reservationService.selectReservationDetailInfoByUserId(userId);

        for(ReservationDetailInfo reservationDetailInfo : reservationDetailInfos){  // 요청 유저의 예약 정보를 가져옴.
            if(reservationDetailInfo.getId() == reservationInfoId){ // 각 예약 정보의 id를 확인하고, 요청 Body의 예약 id와 동일한지 확인.
                isSelf = true;  // 동일하다면 true, 이는 요청 Body의 예약 id가 요청 유저의 예약 정보임을 나타냄.
            }
        }

        if(isSelf == true){ // 요청 Body의 예약 id가 요청 유저의 예약 정보라면
            cancelResult = reservationService.cancelReservation(reservationInfoId); // 예약 취소를 진행함.
        }

        resultMap.put("result", cancelResult);

        return resultMap;
    }

    @ApiOperation(value = "카테고리 목록 구하기")
    @GetMapping(path = "/categories")
    public Map<String, Object> getCategories(){
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

    @ApiOperation(value = "상품 목록 구하기")
    @GetMapping(path = "/display-infos")
    public Map<String, ?> getDisplayinfos(@RequestParam(name ="categoryId", required = false, defaultValue = "0") int categoryId,
                                          @RequestParam (name = "start", required = false, defaultValue = "1") int start){
        Map<String, Object> resultMap = new LinkedHashMap<>();

        List<DisplayDetailInfo> displayDetailInfos = new ArrayList<>();
        int displayCount = 0;
        int totalCount =   0;

        if(categoryId == 0) // categoryId가 0이면, start의 값과는 상관없이 전체 상품의 개수를 응답에 포함시킴.
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
    @GetMapping(path = "/display-infos/{displayId}")
    public Map<String, Object> getDisplayinfosById(@PathVariable (name = "displayId") int displayId){
        Map<String, Object> resultMap = new LinkedHashMap<>();
        List<ProductImage> productImageList = new ArrayList<>();
        List<ProductPrice> productPriceList = new ArrayList<>();
        List<DisplayInfoImage> displayInfoImageList = new ArrayList<>();
        Integer avgScore = null;

        DisplayDetailInfo displayDetailInfo = displayInfoService.selectDisplayInfoByDisplayId(displayId);

        if(displayDetailInfo != null){    // displayId에 해당 하는 정보가 있는 경우에만 나머지 정보를 조회
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

    @ApiOperation(value = "프로모션 정보 구하기")
    @GetMapping(path = "/promotions")
    public Map<String, Object> getPromotions(){
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

    @ApiOperation(value = "댓글 목록 구하기")
    @GetMapping(path = "/comments")
    public Map<String, Object> getComments(@RequestParam(name ="productId", required = false) Integer productId,
                                           @RequestParam (name = "start", required = false, defaultValue = "1") int start){
        Map<String, Object> resultMap = new LinkedHashMap<>();

        List<ReservationUserComment> commentList = new ArrayList<>();
        int totalCount = 0;
        int commentCount = 0;

        if(productId == null)   // url에 productId의 값을 명시하지 않은 경우
            totalCount = reservationUserCommentService.getUserCommentsCount(); // 전체 comment의 개수
        else
            totalCount = reservationUserCommentService.getCommentCountByProductId(productId);  // productId의 댓글의 개수

        if(start >= 1){  // 메서드의 인자로 "start - 1"을 전달하기 때문에 start가 1보다 작으면, SQL문의 LIMIT 절에서 error를 발생시키므로, db에서 조회하지 않고 totalCount를 제외한 나머지 값을 기본값으로 응답함.
            if(productId == null) { // url에 productId의 값을 명시하지 않은 경우
                commentList = reservationUserCommentService.selectUserComments(start - 1);
            }else{
                commentList = reservationUserCommentService.selectUserCommentsByProductId(productId, start - 1);
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


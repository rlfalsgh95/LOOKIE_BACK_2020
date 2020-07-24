package kr.or.connect.controller.api;

import io.swagger.annotations.ApiOperation;
import kr.or.connect.dto.category.CategoryDetail;
import kr.or.connect.dto.display.DisplayDetailInfo;
import kr.or.connect.dto.display.DisplayInfoImage;
import kr.or.connect.dto.file.FileInfo;
import kr.or.connect.dto.product.ProductImage;
import kr.or.connect.dto.product.ProductPrice;
import kr.or.connect.dto.promotion.PromotionDetail;
import kr.or.connect.dto.reservation.ReservationDetailInfo;
import kr.or.connect.dto.reservation.ReservationInfo;
import kr.or.connect.dto.reservation.ReservationUserComment;
import kr.or.connect.dto.reservation.ReservationUserCommentDetail;
import kr.or.connect.dto.user.UserEntity;
import kr.or.connect.service.category.CategoryService;
import kr.or.connect.service.display.DisplayInfoImageService;
import kr.or.connect.service.display.DisplayInfoService;
import kr.or.connect.service.file.FileInfoService;
import kr.or.connect.service.product.ProductImageService;
import kr.or.connect.service.product.ProductPriceService;
import kr.or.connect.service.promotion.PromotionService;
import kr.or.connect.service.reservation.ReservationService;
import kr.or.connect.service.reservation.ReservationUserCommentService;
import kr.or.connect.service.user.UserDbService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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

    @Autowired
    FileInfoService fileInfoService;

    private final String UPLOAD_DIR_PATH = "C:/tmp/";
    private final String DOWNLOAD_DIR_PATH = "C:/tmp/";
    private final String[] ALLOW_CONTENT_TYPE_ARRAY = {"image/png", "image/jpeg", "image/svg+xml"};

    @ApiOperation(value = "예약 등록하기")
    @PostMapping("/reservationInfos")
    public Map<String, Object> addReservation(@RequestBody Map<String, Object> requestParam, Principal principal) {
        String userEmail = principal.getName(); // principal.getName()는 유저의 로그인 시 사용한 아이디
        UserEntity userEntity = userDbService.getUser(userEmail);

        ArrayList<Map<String, Object>> prices = null;
        Map reservationResult = new HashMap();

        Integer reservationUserId = (Integer) requestParam.get("userId");  // 요청 Body의 userId (실제 로그인한 사용자의 userId와 다를 수 있다.)

        if (reservationUserId != null && (userEntity.getUserId() == reservationUserId)) { // 유저의 DB상 userId와 예약 정보의 userId가 동일한 경우에만 예약을 진행.
            Integer productId = (Integer) requestParam.get("productId");
            Integer displayInfoId = (Integer) requestParam.get("displayInfoId");

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
            Date reservationYearMonthDay = null;

            try {
                reservationYearMonthDay = simpleDateFormat.parse(requestParam.get("reservationYearMonthDay").toString());
            } catch (ParseException e) {   // 문자열을 Date 객체로 parsing하는 중에 error가 발생한다면 빈 Json 객체로 응답.
                e.printStackTrace();
                return reservationResult;
            }

            if (productId != null && displayInfoId != null && reservationYearMonthDay != null && requestParam.get("prices") != null) {    // 예약에 필요한 요소들이 요청 Body에 포함되어 있다면 예약을 진행, 그렇지 않다면 빈 Json 객체로 응답.
                ReservationInfo reservationInfo = new ReservationInfo(productId, displayInfoId, reservationYearMonthDay, reservationUserId);

                prices = (ArrayList<Map<String, Object>>) requestParam.get("prices");

                reservationResult = reservationService.reservation(prices, reservationInfo);    // 예약을 진행.
            }
        } else {
            System.out.println("유저 userId :" + userEntity.getUserId() + " 요청 userId : " + requestParam.get("userId"));
            System.out.println("요청의 user와 일치하지 않아 예약을 진행할 수 없습니다.");
        }

        return reservationResult;
    }

    @ApiOperation(value = "주문 정보 구하기")
    @GetMapping("/reservationInfos")
    public Map<String, Object> reservationInfos(Principal principal) {
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
    @PutMapping("/reservationInfos")
    public Map<String, Boolean> reservationCancel(@RequestBody Map<String, Integer> requestParam,
                                                  Principal principal) {
        Map<String, Boolean> resultMap = new HashMap<>();

        int reservationInfoId = requestParam.get("id"); // 요청 Body의 id
        boolean isSelf = false; // 요청 Body의 예약 id에 해당 하는 예약 정보가 요청 유저의 것인지 나타내는 flag
        boolean cancelResult = false;   // 예약 취소 결과를 나타냄.

        String userEmail = principal.getName(); // principal.getName()는 유저의 로그인 시 사용한 아이디
        UserEntity userEntity = userDbService.getUser(userEmail);
        int userId = userEntity.getUserId();

        List<ReservationDetailInfo> reservationDetailInfos = reservationService.selectReservationDetailInfoByUserId(userId);

        for (ReservationDetailInfo reservationDetailInfo : reservationDetailInfos) {  // 요청 유저의 예약 정보를 가져옴.
            if (reservationDetailInfo.getId() == reservationInfoId) { // 각 예약 정보의 id를 확인하고, 요청 Body의 예약 id와 동일한지 확인.
                isSelf = true;  // 동일하다면 true, 이는 요청 Body의 예약 id가 요청 유저의 예약 정보임을 나타냄.
                break;
            }
        }

        if (isSelf == true) { // 요청 Body의 예약 id가 요청 유저의 예약 정보라면
            cancelResult = reservationService.cancelReservation(reservationInfoId); // 예약 취소를 진행함.
        }

        resultMap.put("result", cancelResult);

        return resultMap;
    }

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

    @ApiOperation(value = "상품 목록 구하기")
    @GetMapping(path = "/display-infos")
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
    @GetMapping(path = "/display-infos/{displayId}")
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

    @ApiOperation(value = "댓글 목록 구하기")
    @GetMapping(path = "/comments")
    public Map<String, Object> getComments(@RequestParam(name = "productId", required = false) Integer productId,
                                           @RequestParam(name = "start", required = false, defaultValue = "1") int start) {
        Map<String, Object> resultMap = new LinkedHashMap<>();

        List<ReservationUserCommentDetail> commentList = new ArrayList<>();
        int totalCount = 0;
        int commentCount = 0;

        if (productId == null)   // url에 productId의 값을 명시하지 않은 경우
            totalCount = reservationUserCommentService.getUserCommentsCount(); // 전체 comment의 개수
        else
            totalCount = reservationUserCommentService.getCommentCountByProductId(productId);  // productId의 댓글의 개수

        if (start >= 1) {  // 메서드의 인자로 "start - 1"을 전달하기 때문에 start가 1보다 작으면, SQL문의 LIMIT 절에서 error를 발생시키므로, db에서 조회하지 않고 totalCount를 제외한 나머지 값을 기본값으로 응답함.
            if (productId == null) { // url에 productId의 값을 명시하지 않은 경우
                commentList = reservationUserCommentService.selectUserComments(start - 1);
            } else {
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

    @ApiOperation("예약 등록하기")
    @PostMapping("/comments")
    public Map<String, Object> addComments(@RequestParam(name = "reservationInfoId", required = true) int reservationInfoId,
                                           @RequestParam(name = "score", required = true) int score,
                                           @RequestParam(name = "comment", required = true) String comment,
                                           @RequestParam(name = "multipartFile", required = false) MultipartFile multipartFile,
                                           Principal principal, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", "fail");    // reservationInfoId에 해당하는 예약이 요청 유저의 것이 아닌 경우, contentType이 허용된 타입이 아닌 경우 "fail"로 응답

        boolean isSelf = false;    // reservationInfoId에 해당하는 예약이 요청 유저의 예약 정보인지를 나타내는 boolean 변수

        String userEmail = principal.getName();
        UserEntity userEntity = userDbService.getUser(userEmail);
        int userId = userEntity.getUserId();

        List<Integer> reservationInfoIdList = reservationService.selectAllReservationInfoIdOfUser(userId); // 요청 유저의 예약 정보들의 id를 조회.

        if (reservationInfoIdList.size() != 0) {  // 요청 유저의 예약 정보가 있는 경우
            for (int reservationInfoIdOfUser : reservationInfoIdList) {   // 각 예약 정보와 요청 파라미터의 id와 일치하는지 확인. (즉, 해당 예약이 요청 유저의 예약인지 확인)
                if (reservationInfoIdOfUser == reservationInfoId) {   // 일치한다면
                    isSelf = true; // 해당 유저의 예약임을 나타냄.
                    break;
                }
            }
        }

        if (isSelf == true) {     // 해당 유저의 예약임을 확인한 경우
            String originalFileName = null;
            String extension = null;
            String baseName = null;
            String contentType = null;

            String fileName = null;
            String saveFileName = null;

            boolean isAllowedType = false;
            File convFile = null;

            if (multipartFile != null) {
                contentType = multipartFile.getContentType();

                if (Arrays.stream(ALLOW_CONTENT_TYPE_ARRAY).anyMatch(contentType::equals)) { // 업로드된 파일이 이미지인 경우에만 댓글을 등록.
                    isAllowedType = true;

                    originalFileName = multipartFile.getOriginalFilename();  // 업로드 파일의 이름
                    extension = FilenameUtils.getExtension(originalFileName);    // 파일의 확장자
                    baseName = FilenameUtils.getBaseName(originalFileName);  // getBaseName은 전체 경로명에서 파일명만을 반환

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");
                    String curDate = simpleDateFormat.format(new Date());

                    fileName = multipartFile.getOriginalFilename();
                    saveFileName = baseName + "_" + curDate + "." + extension;  // 저장할 파일의 실제 파일명

                    // 파일 저장
                    convFile = new File(UPLOAD_DIR_PATH, saveFileName);

                    try{
                        multipartFile.transferTo(convFile);
                    }catch(IOException e){
                        e.printStackTrace();
                        return resultMap;   // 파일 저장에 실패할 경우 댓글 등록을 진행하지 않고 결과를 리턴.
                    }
                }
            }

            if (isAllowedType == true || multipartFile == null) { // 첨부 파일이 없거나 파일의 type이 허용된 포맷인 경우에만 댓글을 등록
                try{
                    ReservationInfo reservationInfo = reservationService.selectReservationInfoByReservationInfoId(reservationInfoId);   // 예약 id에 해당하는 예약 정보를 조회

                    if (reservationInfo != null) {    // selectReservationInfoByReservationInfoId() 메서드는 내부적으로 queryForObject() 메서드를 호출하므로 null을 체크
                        int productId = reservationInfo.getProductId(); // 예약 id에 해당하는 productId 조회
                        ReservationUserComment userComment = new ReservationUserComment(productId, reservationInfoId, userId, score, comment);   // ReservationUserComment 테이블에 삽입할 dto 객체 생성

                        // reservation_user_comment 테이블, 그리고 그와 관련된 테이블에 댓글 정보 삽입
                        if(multipartFile != null){  // 요청 유저가 파일을 첨부한 경우, File_info 및 reservation_info_image에도 정보를 insert하기 위해 FileInfo 객체 전달.
                            FileInfo fileInfo = new FileInfo(fileName, saveFileName, contentType);
                            reservationUserCommentService.insertUserCommentWithFile(userComment, fileInfo);
                        }else{
                            reservationUserCommentService.insertUserComment(userComment);
                        }

                        resultMap.put("result", "success");
                        resultMap.put("productId", productId);
                    }
                }catch(Exception e){
                    e.printStackTrace();

                    if(multipartFile != null){  // 업로드 파일을 저장한 경우
                        // 저장한 파일을 다시 삭제
                        if(convFile.exists()){
                            convFile.delete();  // 파일 삭제
                        }
                    }
                    return resultMap;
                }
            }else{
                System.out.println("허용된 ContentType이 아님.");
            }
        }else{
            System.out.println("요청 유저의 예약 정보가 아님.");
        }

        return resultMap;
    }
    
    @ApiOperation(value = "이미지 다운로드하기")
    @GetMapping("/files/{fileId}")
    public void downloadFile(@PathVariable(name = "fileId", required = true)int fileId, HttpServletResponse response){
        FileInfo fileInfo = (FileInfo) fileInfoService.selectByFileId(fileId, FileInfo.class);

        if(fileInfo != null){
            String saveFileName = fileInfo.getSaveFileName();
            String contentType = fileInfo.getContentType();

            File downloadFile = new File(DOWNLOAD_DIR_PATH + saveFileName);
            String contentLength = Long.toString(downloadFile.length());

            response.setHeader("Content-Disposition", "attachment; filename=\"" + saveFileName + "\";");
            response.setHeader("Content-Transfer-Encoding", "binary");
            response.setHeader("Content-Type", contentType);
            response.setHeader("Content-Length", contentLength);
            response.setHeader("Pragma", "no-cache;");
            response.setHeader("Expires", "-1;");

            try(FileInputStream fis = new FileInputStream(downloadFile);
                OutputStream out = response.getOutputStream();){

                int readCount = 0;
                byte[] buffer = new byte[1024];

                while((readCount = fis.read(buffer)) != -1){
                    out.write(buffer, 0, readCount);
                }
            }catch(Exception e){
                e.printStackTrace();
                //throw new RuntimeException("fail to download file");
            }
        }
    }
}

/*
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The requested information could not be found.")
class InformationNotFoundException extends RuntimeException{
}
*/


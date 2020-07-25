package kr.or.connect.controller.api;

import io.swagger.annotations.ApiOperation;
import kr.or.connect.dto.file.FileInfo;
import kr.or.connect.dto.reservation.ReservationInfo;
import kr.or.connect.dto.reservation.ReservationUserComment;
import kr.or.connect.dto.reservation.ReservationUserCommentDetail;
import kr.or.connect.dto.user.UserEntity;
import kr.or.connect.service.reservation.ReservationService;
import kr.or.connect.service.reservation.ReservationUserCommentService;
import kr.or.connect.service.user.UserDbService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    ReservationUserCommentService reservationUserCommentService;

    @Autowired
    UserDbService userDbService;

    @Autowired
    ReservationService reservationService;

    private final String UPLOAD_DIR_PATH = "C:/tmp/";
    private final String[] ALLOW_CONTENT_TYPE_ARRAY = {"image/png", "image/jpeg", "image/svg+xml"};

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
        UserEntity userEntity = userDbService.selectUser(userEmail);
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
            }
        }

        return resultMap;
    }
}

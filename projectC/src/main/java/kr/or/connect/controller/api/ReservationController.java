package kr.or.connect.controller.api;

import io.swagger.annotations.ApiOperation;
import kr.or.connect.dto.reservation.ReservationDetailInfo;
import kr.or.connect.dto.reservation.ReservationInfo;
import kr.or.connect.dto.user.UserEntity;
import kr.or.connect.service.reservation.ReservationService;
import kr.or.connect.service.user.UserDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api")
public class ReservationController {
    @Autowired
    UserDbService userDbService;

    @Autowired
    ReservationService reservationService;

    @ApiOperation(value = "예약 등록하기")
    @PostMapping("/reservationInfos")
    public Map<String, Object> addReservation(@RequestBody Map<String, Object> requestParam, Principal principal) {
        String userEmail = principal.getName(); // principal.getName()는 유저의 로그인 시 사용한 아이디
        UserEntity userEntity = userDbService.selectUser(userEmail);

        ArrayList<Map<String, Object>> prices = null;
        Map<String, Object> reservationResult = new HashMap();

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
        UserEntity userEntity = userDbService.selectUser(userEmail);
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
        UserEntity userEntity = userDbService.selectUser(userEmail);
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
}

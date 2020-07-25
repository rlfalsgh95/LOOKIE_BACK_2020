package kr.or.connect.service.reservation;

import kr.or.connect.dto.reservation.ReservationDetailInfo;
import kr.or.connect.dto.reservation.ReservationInfo;

import java.util.List;
import java.util.Map;

public interface ReservationService {
    Map<String, Object> reservation(List<Map<String, Object>> prices, ReservationInfo reservationInfo);
    List<ReservationDetailInfo> selectReservationDetailInfoByUserId(int userId);
    List<Integer> selectAllReservationInfoIdOfUser(int userId);
    ReservationInfo selectReservationInfoByReservationInfoId(int reservationInfoId);
    boolean cancelReservation(int reservationInfoId);
}

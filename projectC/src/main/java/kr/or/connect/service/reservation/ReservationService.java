package kr.or.connect.service.reservation;

import kr.or.connect.dto.reservation.ReservationDetailInfo;
import kr.or.connect.dto.reservation.ReservationInfo;

import java.util.List;
import java.util.Map;

public interface ReservationService {
    public Map<String, Object> reservation(List<Map<String, Object>> prices, ReservationInfo reservationInfo);
    public List<ReservationDetailInfo> selectReservationDetailInfoByUserId(int userId);
    public List<Integer> selectAllReservationInfoIdOfUser(int userId);
    public ReservationInfo selectReservationInfoByReservationInfoId(int reservationInfoId);
    public boolean cancelReservation(int reservationInfoId);
}

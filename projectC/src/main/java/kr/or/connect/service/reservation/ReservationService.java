package kr.or.connect.service.reservation;

import kr.or.connect.dto.ReservationDetailInfo;
import kr.or.connect.dto.ReservationInfo;

import java.util.List;
import java.util.Map;

public interface ReservationService {
    public Map<String, Object> reservation(List<Map<String, Object>> prices, ReservationInfo reservationInfo);
    public List<ReservationDetailInfo> selectReservationDetailInfoByUserId(int userId);
    public boolean cancelReservation(int reservationInfoId);
}

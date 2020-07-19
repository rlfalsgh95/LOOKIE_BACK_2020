package kr.or.connect.service.reservation.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.or.connect.dao.PriceDao;
import kr.or.connect.dao.reservation.ReservationDao;
import kr.or.connect.dto.ReservationDetailInfo;
import kr.or.connect.dto.ReservationInfo;
import kr.or.connect.dto.ReservationinfoPrice;
import kr.or.connect.service.reservation.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    ReservationDao reservationDao;

    @Autowired
    PriceDao priceDao;

    @Override
    @Transactional(readOnly = false)
    public Map<String, Object> reservation(List<Map<String, Object>> prices, ReservationInfo reservationInfo){
        int reservationInfoId = reservationDao.insertReservation(reservationInfo);  // reservation_info 테이블에 예약 정보를 insert
        ReservationInfo resultReservationInfo = reservationDao.selectReservationInfoByReservationInfoId(reservationInfoId); // insert한 예약 정보를 조회(Dao 객체에서 id, createDate, modifyDate를 reservationInfo를 삽입해주므로, reservationInfo는 resultReservationInfo와 다름) 

        ObjectMapper objectMapper = new ObjectMapper(); // json 문자열로 바꾸거나 json 문자열을 객체로 바꾸는 역할을 수행하는 객체.
        Map resultMap = objectMapper.convertValue(resultReservationInfo, Map.class);    // resultReservationInfo를 Map객체로 변환

        List<ReservationinfoPrice> reservationInfoPriceList = new ArrayList<>();  // 예매 할 때 선택한 가격과 수량 정보 list

        for(Map<String, Object> price : prices){
            int count = (Integer)price.get("count");
            int productPriceId = (Integer)price.get("productPriceId");

            ReservationinfoPrice reservationinfoPrice = new ReservationinfoPrice(reservationInfoId, productPriceId, count);

            int reservationInfoPriceId = priceDao.insertReservationInfoPrice(reservationinfoPrice); // reservation_info_price 테이블에 예약 정보를 insert
            reservationInfoPriceList.add(priceDao.selectPriceByReservationInfoPriceId(reservationInfoPriceId)); // 예매 할 때 선택한 각 가격과 수량 정보를 list에 추가.
        }
        resultMap.put("prices", reservationInfoPriceList);

        return resultMap;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationDetailInfo> selectReservationDetailInfoByUserId(int userId){
        List<ReservationDetailInfo> items = reservationDao.selectReservationDetailInfoByUserId(userId); // userId에 해당하는 유저의 예약 상세 정보를 조회

        for(ReservationDetailInfo item : items){    
            int sumPrice = priceDao.getSumByReservationInfoId(item.getId());    // 각 예약 정보의 총 비용을 조회
            item.setSumPrice(sumPrice);
        }
        return items;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean cancelReservation(int reservationInfoId) {
        int cancelCount = reservationDao.changeCancelFlagByReservatonInfoId(reservationInfoId, 1);    // reservationInfoId에 해당하는 예약을 취소
        int modifyCount = reservationDao.updateModifyDateByReservationInfoId(reservationInfoId);    // reservationInfoId에 해당하는 예약의 modify_date를 업데이트

        return (cancelCount == 1 && modifyCount == 1) ? true : false;   // 예약 취소 결과가 1건이라면 true를 반환, 그렇지 않다면 false를 반환 (예약 취소 성공 여부를 나타냄)
    }
}

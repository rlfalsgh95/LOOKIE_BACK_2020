package kr.or.connect.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.or.connect.dao.PriceDao;
import kr.or.connect.dao.ReservationDao;
import kr.or.connect.dto.ReservationDetailInfo;
import kr.or.connect.dto.ReservationInfo;
import kr.or.connect.dto.ReservationinfoPrice;
import kr.or.connect.service.ReservationService;
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


    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> reservation(List<Map<String, Object>> prices, ReservationInfo reservationInfo){
        int reservationInfoId = reservationDao.insertReservation(reservationInfo);
        ReservationInfo resultReservationInfo = reservationDao.selectReservationInfoByReservationInfoId(reservationInfoId);

        ObjectMapper objectMapper = new ObjectMapper(); // json 문자열로 바꾸거나 json 문자열을 객체로 바꾸는 역할을 수행하는 객체.
        Map resultMap = objectMapper.convertValue(resultReservationInfo, Map.class);    // list를 json 문자열로 변환
        List<ReservationinfoPrice> reservationInfoPriceIdList = new ArrayList<>();

        for(Map<String, Object> price : prices){
            int count = (Integer)price.get("count");
            int productPriceId = (Integer)price.get("productPriceId");

            ReservationinfoPrice reservationinfoPrice = new ReservationinfoPrice(reservationInfoId, productPriceId, count);

            int reservationInfoPriceId = priceDao.insertReservationInfoPrice(reservationinfoPrice);
            reservationInfoPriceIdList.add(priceDao.selectPriceByReservationInfoPriceId(reservationInfoPriceId));
        }
        resultMap.put("prices", reservationInfoPriceIdList);

        return resultMap;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReservationDetailInfo> selectReservationDetailInfoByUserId(int userId){
        List<ReservationDetailInfo> items = reservationDao.selectReservationDetailInfoByUserId(userId);

        for(ReservationDetailInfo item : items){
            int sumPrice = priceDao.getSumByReservationInfoId(item.getId());
            item.setSumPrice(sumPrice);
        }
        return items;
    }

    @Override
    public boolean cancelReservation(int reservationInfoId) {
        int cancelCount = reservationDao.cancelReservationByReservatonInfoId(reservationInfoId);

        return (cancelCount == 1) ? true : false;
    }
}

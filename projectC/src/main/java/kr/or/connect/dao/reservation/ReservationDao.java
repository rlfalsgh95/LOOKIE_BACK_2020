package kr.or.connect.dao.reservation;

import kr.or.connect.dao.reservation.sqls.ReservationDaoSqls;
import kr.or.connect.dto.reservation.ReservationDetailInfo;
import kr.or.connect.dto.reservation.ReservationInfo;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class ReservationDao {
    private final NamedParameterJdbcTemplate jdbc;
    private final SimpleJdbcInsert insertAction;
    private final RowMapper<ReservationInfo> ReservationInfoRowMapper = BeanPropertyRowMapper.newInstance(ReservationInfo.class);
    private final RowMapper<ReservationDetailInfo> ReservationDetailInfoRowMapper = BeanPropertyRowMapper.newInstance(ReservationDetailInfo.class);

    public ReservationDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("reservation_info").usingGeneratedKeyColumns("id");
    }

    public int insertReservation(ReservationInfo reservationInfo){
        reservationInfo.setCancelFlag(0);   // cancelflag는 취소 여부를 나타냄(0 : 취소 안함)
        reservationInfo.setCreateDate(new Date());  // 생성 날짜
        reservationInfo.setModifyDate(new Date());  // 수정 날짜

        SqlParameterSource params = new BeanPropertySqlParameterSource(reservationInfo);
        return insertAction.executeAndReturnKey(params).intValue(); // 삽입 후, row의 id(primary key)를 반환.
    }

    public List<Integer> selectAllReservationInfoIdOfUser(int userId){
        Map<String, Integer> param = Collections.singletonMap("userId", userId);

        return jdbc.queryForList(ReservationDaoSqls.SELECT_ALL_RESERVATION_INFO_ID_OF_USER, param, Integer.class);
    }

    public ReservationInfo selectReservationInfoByReservationInfoId(int reservationInfoId){ // reservationInfoId에 해당하는 예약 정보를 조회
        Map<String, Integer> param = Collections.singletonMap("reservationInfoId", reservationInfoId);

        try{
            return jdbc.queryForObject(ReservationDaoSqls.SELECT_RESERVATION_INFO_BY_RESERVATION_INFO_ID, param, ReservationInfoRowMapper);
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<ReservationDetailInfo> selectReservationDetailInfoByUserId(int userId){ // userId에 해당 하는 유저의 예약 상세 정보를 조회
        Map<String, Integer> params = Collections.singletonMap("userId", userId);

        return jdbc.query(ReservationDaoSqls.SELECT_RESERVATION_DETAIL_INFO_BY_USER_ID, params, ReservationDetailInfoRowMapper);
    }

    public int changeCancelFlagByReservatonInfoId(int reservationInfoId, int cancelFlag){  // reservationInfoId에 해당하는 예약 정보의 cancelFlag를 1로 수정
        Map<String, Integer> params = new HashMap<>();
        params.put("reservationInfoId", reservationInfoId);
        params.put("cancelFlag", cancelFlag);

        return jdbc.update(ReservationDaoSqls.CANCEL_RESERVATION_BY_RESERVATION_INFO_ID, params);
    }

    public int updateModifyDateByReservationInfoId(int reservationInfoId){
        Map<String, Object> params = new HashMap<>();
        params.put("reservationInfoId", reservationInfoId);
        params.put("modifyDate", new Date());

        return jdbc.update(ReservationDaoSqls.UPDATE_MODIFY_DATE_BY_RESERVATION_INFO_ID, params);
    }
}

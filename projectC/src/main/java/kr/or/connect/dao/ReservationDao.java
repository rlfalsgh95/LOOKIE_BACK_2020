package kr.or.connect.dao;

import io.swagger.models.auth.In;
import kr.or.connect.dao.sqls.ReservationDaoSqls;
import kr.or.connect.dto.ReservationDetailInfo;
import kr.or.connect.dto.ReservationInfo;
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
    private NamedParameterJdbcTemplate jdbc;
    private SimpleJdbcInsert insertAction;
    private RowMapper<ReservationInfo> ReservationInfoRowMapper = BeanPropertyRowMapper.newInstance(ReservationInfo.class);
    private RowMapper<ReservationDetailInfo> ReservationDefailInfoRowMapper = BeanPropertyRowMapper.newInstance(ReservationDetailInfo.class);

    public ReservationDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("reservation_info").usingGeneratedKeyColumns("id");
    }

    public int insertReservation(ReservationInfo reservationInfo){
        reservationInfo.setCancelFlag(0);
        reservationInfo.setModifyDate(new Date());
        reservationInfo.setCreateDate(new Date());

        SqlParameterSource params = new BeanPropertySqlParameterSource(reservationInfo);
        return insertAction.executeAndReturnKey(params).intValue();
    }

    public ReservationInfo selectReservationInfoByReservationInfoId(int reservationInfoId){
        Map<String, Integer> param = Collections.singletonMap("reservationInfoId", reservationInfoId);

        try{
            return jdbc.queryForObject(ReservationDaoSqls.SELECT_RESERVATION_INFO_BY_RESERVATION_INFO_ID, param, ReservationInfoRowMapper);
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<ReservationDetailInfo> selectReservationDetailInfoByUserId(int userId){
        Map<String, Integer> params = Collections.singletonMap("userId", userId);

        return jdbc.query(ReservationDaoSqls.SELECT_RESERVATION_DETAIL_INFO_BY_USER_ID, params, ReservationDefailInfoRowMapper);
    }

    public int cancelReservationByReservatonInfoId(int reservationInfoId){
        Map<String, Integer> params = Collections.singletonMap("reservationInfoId", reservationInfoId);

        return jdbc.update(ReservationDaoSqls.CANCEL_RESERVATION_BY_RESERVATION_INFO_ID, params);
    }
}

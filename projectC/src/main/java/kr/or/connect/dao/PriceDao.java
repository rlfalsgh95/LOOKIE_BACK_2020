package kr.or.connect.dao;

import kr.or.connect.dao.sqls.PriceDaoSqls;
import kr.or.connect.dto.reservation.ReservationinfoPrice;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Map;

@Repository
public class PriceDao {
    private final NamedParameterJdbcTemplate jdbc;
    private final SimpleJdbcInsert insertAction;
    private final RowMapper<ReservationinfoPrice> priceRowMapper = BeanPropertyRowMapper.newInstance(ReservationinfoPrice.class);

    public PriceDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("reservation_info_price").usingGeneratedKeyColumns("id");
    }

    public int insertReservationInfoPrice(ReservationinfoPrice reservationinfoPrice){
        SqlParameterSource params = new BeanPropertySqlParameterSource(reservationinfoPrice);
        return insertAction.executeAndReturnKey(params).intValue();
    }

    public ReservationinfoPrice selectPriceByReservationInfoPriceId(int reservationInfoPriceId){
        Map<String, Integer> params = Collections.singletonMap("reservationInfoPriceId", reservationInfoPriceId);

        try{
            return jdbc.queryForObject(PriceDaoSqls.SELECT_PRICE_BY_RESERVATION_INFO_PRICE_ID, params, priceRowMapper);
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    public Integer getSumByReservationInfoId(int reservationInfoId){
        Map<String, Integer> params = Collections.singletonMap("reservationInfoId", reservationInfoId);

        try{
            return jdbc.queryForObject(PriceDaoSqls.GET_SUM_BY_RESERVATION_INFO_ID, params, Integer.class);
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }
}

package kr.or.connect.dto.reservation;

public class ReservationinfoPrice {
    private int id;
    private int reservationInfoId;
    private int productPriceId;
    private int count;

    public ReservationinfoPrice(){}
    public ReservationinfoPrice(int reservationInfoId, int productPriceId, int count){
        this.reservationInfoId = reservationInfoId;
        this.productPriceId = productPriceId;
        this.count = count;
    }

    @Override
    public String toString() {
        return "price{" +
                "id=" + id +
                ", reservationInfoId=" + reservationInfoId +
                ", productPriceId=" + productPriceId +
                ", count=" + count +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReservationInfoId() {
        return reservationInfoId;
    }

    public void setReservationInfoId(int reservationInfoId) {
        this.reservationInfoId = reservationInfoId;
    }

    public int getProductPriceId() {
        return productPriceId;
    }

    public void setProductPriceId(int productPriceId) {
        this.productPriceId = productPriceId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

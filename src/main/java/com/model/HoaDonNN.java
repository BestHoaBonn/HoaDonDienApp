package com.model;

import java.time.LocalDate;

public class HoaDonNN extends HoaDon {

    private double thue;

    public HoaDonNN(String id, String tenKhachHang, LocalDate ngayLap, double tongTien, double thue) {
        super(id, tenKhachHang, ngayLap, tongTien);
        this.thue = thue;
    }

    @Override
    public double tinhTien() {
        return tongTien + tongTien * thue;
    }
}

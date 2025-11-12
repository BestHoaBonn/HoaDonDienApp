package com.model;

import java.time.LocalDate;

public class HoaDonVN extends HoaDon {

    private double dinhMuc;

    public HoaDonVN(String id, String tenKhachHang, LocalDate ngayLap, double tongTien, double dinhMuc) {
        super(id, tenKhachHang, ngayLap, tongTien);
        this.dinhMuc = dinhMuc;
    }

    @Override
    public double tinhTien() {
        return tongTien * dinhMuc;
    }
}

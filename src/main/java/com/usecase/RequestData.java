package com.usecase;

import java.time.LocalDate;

public class RequestData {

    public String id;
    public String tenKhachHang;
    public double tongTien;
    public double heSo;
    public LocalDate ngayLap;

    public RequestData(String id, String tenKhachHang, double tongTien, double heSo) {
        this.id = id;
        this.tenKhachHang = tenKhachHang;
        this.tongTien = tongTien;
        this.heSo = heSo;
    }

    public RequestData(String id, String tenKhachHang, double tongTien, double heSo, LocalDate ngayLap) {
        this(id, tenKhachHang, tongTien, heSo);
        this.ngayLap = ngayLap;
    }

    public RequestData() {
    }
}

package com.model;

import java.time.LocalDate;

/**
 * Lớp cơ sở (base class) cho Hóa Đơn
 */
public abstract class HoaDon {

    protected String id;
    protected String tenKhachHang;
    protected LocalDate ngayLap;
    protected double tongTien;

    public HoaDon(String id, String tenKhachHang, LocalDate ngayLap, double tongTien) {
        this.id = id;
        this.tenKhachHang = tenKhachHang;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
    }

    // ----- Getter -----
    public String getId() {
        return id;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public LocalDate getNgayLap() {
        return ngayLap;
    }

    public double getTongTien() {
        return tongTien;
    }

    // ----- Setter -----
    public void setId(String id) {
        this.id = id;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public void setNgayLap(LocalDate ngayLap) {
        this.ngayLap = ngayLap;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public abstract double tinhTien();
}

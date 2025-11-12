package com.usecase;

import com.model.HoaDon;

public class ResponseData {

    public String message;
    public double tongTien;
    public HoaDon created;
    public Object data;

    public ResponseData(String message, double tongTien, HoaDon created) {
        this.message = message;
        this.tongTien = tongTien;
        this.created = created;
    }

    public ResponseData(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public ResponseData(String message, double tongTien) {
        this.message = message;
        this.tongTien = tongTien;
    }

    public ResponseData(String message) {
        this.message = message;
    }
}

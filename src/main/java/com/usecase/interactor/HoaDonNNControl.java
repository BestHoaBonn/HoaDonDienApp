package com.usecase.interactor;

import com.model.HoaDonNN;
import com.usecase.OutputBoundaryHoaDon;
import com.usecase.RequestData;
import com.usecase.ResponseData;
import java.time.LocalDate;

public class HoaDonNNControl extends HoaDonControl {

    private final OutputBoundaryHoaDon presenter;

    public HoaDonNNControl(OutputBoundaryHoaDon presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(RequestData request) {
        executeWithDate(request, LocalDate.now());
    }

    public void executeWithDate(RequestData request, LocalDate date) {
        HoaDonNN hoaDon = new HoaDonNN(
                request.id,
                request.tenKhachHang,
                date,
                request.tongTien,
                request.heSo
        );

        double total = hoaDon.tinhTien();
        presenter.present(new ResponseData("Hóa đơn Nước Ngoài đã tạo", total, hoaDon));
    }
}

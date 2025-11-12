package com.usecase.interactor;

import com.model.HoaDonVN;
import com.usecase.OutputBoundaryHoaDon;
import com.usecase.RequestData;
import com.usecase.ResponseData;
import java.time.LocalDate;

public class HoaDonVNControl extends HoaDonControl {

    private final OutputBoundaryHoaDon presenter;

    public HoaDonVNControl(OutputBoundaryHoaDon presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(RequestData request) {
        executeWithDate(request, LocalDate.now());
    }

    public void executeWithDate(RequestData request, LocalDate date) {
        HoaDonVN hoaDon = new HoaDonVN(
                request.id,
                request.tenKhachHang,
                date,
                request.tongTien,
                request.heSo
        );

        double total = hoaDon.tinhTien();
        presenter.present(new ResponseData("Hóa đơn Việt Nam đã tạo", total, hoaDon));
    }
}

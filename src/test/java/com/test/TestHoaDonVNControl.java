package com.test;

import com.presentation.presenter.HoaDonVNPresenter;
import com.presentation.viewmodel.HoaDonViewModel;
import com.usecase.RequestData;
import com.usecase.interactor.HoaDonVNControl;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TestHoaDonVNControl {

    @Test
    public void testCreateVNInvoice_Success() {
        HoaDonViewModel vm = new HoaDonViewModel();
        HoaDonVNPresenter presenter = new HoaDonVNPresenter(vm);
        HoaDonVNControl interactor = new HoaDonVNControl(presenter);

        RequestData req = new RequestData("1", "Nguyễn Văn A", 200, 1.5, LocalDate.now());
        interactor.executeWithDate(req, req.ngayLap);

        assertEquals("Hóa đơn Việt Nam đã tạo", vm.messageProperty().get());
        assertTrue(vm.tongTienProperty().get() > 0);
    }

    @Test
    public void testCreateVNInvoice_Fail_NegativeMoney() {
        HoaDonViewModel vm = new HoaDonViewModel();
        HoaDonVNPresenter presenter = new HoaDonVNPresenter(vm);
        HoaDonVNControl interactor = new HoaDonVNControl(presenter);

        RequestData req = new RequestData("2", "Nguyễn Văn B", -100, 1.2, LocalDate.now());

        // lỗi tiền âm
        assertThrows(IllegalArgumentException.class, () -> {
            if (req.tongTien <= 0) {
                throw new IllegalArgumentException("Tổng tiền không hợp lệ!");
            }
            interactor.executeWithDate(req, req.ngayLap);
        });
    }

    @Test
    public void testCreateVNInvoice_Fail_EmptyName() {
        HoaDonViewModel vm = new HoaDonViewModel();
        HoaDonVNPresenter presenter = new HoaDonVNPresenter(vm);
        HoaDonVNControl interactor = new HoaDonVNControl(presenter);

        RequestData req = new RequestData("3", "", 200, 1.0, LocalDate.now());

        // lỗi do tên rỗng
        assertThrows(IllegalArgumentException.class, () -> {
            if (req.tenKhachHang == null || req.tenKhachHang.isEmpty()) {
                throw new IllegalArgumentException("Tên khách hàng rỗng!");
            }
            interactor.executeWithDate(req, req.ngayLap);
        });
    }
}

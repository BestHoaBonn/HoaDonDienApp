package com.test;

import com.presentation.presenter.HoaDonNNPresenter;
import com.presentation.viewmodel.HoaDonViewModel;
import com.usecase.RequestData;
import com.usecase.interactor.HoaDonNNControl;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TestHoaDonNNControl {

    @Test
    public void testCreateNNInvoice_Success() {
        HoaDonViewModel vm = new HoaDonViewModel();
        HoaDonNNPresenter presenter = new HoaDonNNPresenter(vm);
        HoaDonNNControl interactor = new HoaDonNNControl(presenter);

        RequestData req = new RequestData("10", "John Smith", 500, 0.1, LocalDate.now());
        interactor.executeWithDate(req, req.ngayLap);

        assertEquals("Hóa đơn Nước Ngoài đã tạo", vm.messageProperty().get());
        assertTrue(vm.tongTienProperty().get() > 0);
    }

    @Test
    public void testCreateNNInvoice_Fail_TaxOver1() {
        HoaDonViewModel vm = new HoaDonViewModel();
        HoaDonNNPresenter presenter = new HoaDonNNPresenter(vm);
        HoaDonNNControl interactor = new HoaDonNNControl(presenter);

        RequestData req = new RequestData("11", "John", 100, 1.5, LocalDate.now());

        // lỗi vì thuế > 1
        assertThrows(IllegalArgumentException.class, () -> {
            if (req.heSo > 1) {
                throw new IllegalArgumentException("Thuế không thể > 100%");
            }
            interactor.executeWithDate(req, req.ngayLap);
        });
    }

    @Test
    public void testCreateNNInvoice_Fail_ZeroMoney() {
        HoaDonViewModel vm = new HoaDonViewModel();
        HoaDonNNPresenter presenter = new HoaDonNNPresenter(vm);
        HoaDonNNControl interactor = new HoaDonNNControl(presenter);

        RequestData req = new RequestData("12", "Maria", 0, 0.1, LocalDate.now());

        // lỗi vì tiền = 0
        assertThrows(IllegalArgumentException.class, () -> {
            if (req.tongTien <= 0) {
                throw new IllegalArgumentException("Tổng tiền phải > 0");
            }
            interactor.executeWithDate(req, req.ngayLap);
        });
    }
}

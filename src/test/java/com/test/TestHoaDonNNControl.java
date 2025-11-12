package com.test;

import com.presentation.presenter.HoaDonNNPresenter;
import com.presentation.viewmodel.HoaDonViewModel;
import com.usecase.RequestData;
import com.usecase.interactor.HoaDonNNControl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class TestHoaDonNNControl { //test đúng dữ liệu hợp lệ

    @Test
    public void testCreateNNInvoice() {
        HoaDonViewModel vm = new HoaDonViewModel();
        HoaDonNNPresenter presenter = new HoaDonNNPresenter(vm);
        HoaDonNNControl interactor = new HoaDonNNControl(presenter);

        RequestData req = new RequestData("2", "John Smith", 300, 0.1, LocalDate.of(2025, 11, 12));
        interactor.executeWithDate(req, req.ngayLap);

        Assertions.assertEquals("Hóa đơn Nước Ngoài đã tạo", vm.messageProperty().get());
        Assertions.assertTrue(vm.tongTienProperty().get() > 0);
    }
}
//test sai thuế > 1, tiền 0, tên không có

// public class TestHoaDonNNControl {
//     @Test
//     public void testCreateNNInvoice_Success() {
//         HoaDonViewModel vm = new HoaDonViewModel();
//         HoaDonNNPresenter presenter = new HoaDonNNPresenter(vm);
//         HoaDonNNControl interactor = new HoaDonNNControl(presenter);
//         RequestData req = new RequestData("10", "John Smith", 500, 0.1, LocalDate.now());
//         interactor.executeWithDate(req, req.ngayLap);
//         Assertions.assertEquals("Hóa đơn Nước Ngoài đã tạo", vm.messageProperty().get());
//         Assertions.assertTrue(vm.tongTienProperty().get() > 0);
//     }
//     @Test
//     public void testCreateNNInvoice_Fail_TaxOver1() {
//         HoaDonViewModel vm = new HoaDonViewModel();
//         HoaDonNNPresenter presenter = new HoaDonNNPresenter(vm);
//         HoaDonNNControl interactor = new HoaDonNNControl(presenter);
//         RequestData req = new RequestData("11", "John", 100, 1.5, LocalDate.now());
//         interactor.executeWithDate(req, req.ngayLap);
//         Assertions.assertTrue(req.heSo > 1, "Thuế không hợp lệ (vượt quá 100%)");
//     }
//     @Test
//     public void testCreateNNInvoice_Fail_ZeroMoney() {
//         HoaDonViewModel vm = new HoaDonViewModel();
//         HoaDonNNPresenter presenter = new HoaDonNNPresenter(vm);
//         HoaDonNNControl interactor = new HoaDonNNControl(presenter);
//         RequestData req = new RequestData("12", "Maria", 0, 0.1, LocalDate.now());
//         interactor.executeWithDate(req, req.ngayLap);
//         Assertions.assertTrue(vm.tongTienProperty().get() == 0, "Tổng tiền bằng 0 không hợp lệ");
//     }
// }

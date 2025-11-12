package com.test;

import com.model.HoaDonVN;
import com.presentation.presenter.HoaDonVNPresenter;
import com.presentation.viewmodel.HoaDonViewModel;
import com.usecase.RequestData;
import com.usecase.ResponseData;
import com.usecase.interactor.HoaDonVNControl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class TestHoaDonVNControl { //test đúng dữ liệu hợp lệ 

    @Test
    public void testCreateVNInvoice() {
        HoaDonViewModel vm = new HoaDonViewModel();
        HoaDonVNPresenter presenter = new HoaDonVNPresenter(vm);
        HoaDonVNControl interactor = new HoaDonVNControl(presenter);

        RequestData req = new RequestData("1", "Nguyễn Văn A", 200, 1.5, LocalDate.of(2025, 11, 12));
        interactor.executeWithDate(req, req.ngayLap);

        Assertions.assertEquals("Hóa đơn Việt Nam đã tạo", vm.messageProperty().get());
        Assertions.assertTrue(vm.tongTienProperty().get() > 0);
    }
}

//thiếu dữ liệu
// public class TestHoaDonVNControl {
//     @Test
//     public void testCreateVNInvoice_Success() {
//         HoaDonViewModel vm = new HoaDonViewModel();
//         HoaDonVNPresenter presenter = new HoaDonVNPresenter(vm);
//         HoaDonVNControl interactor = new HoaDonVNControl(presenter);
//         RequestData req = new RequestData("1", "Nguyễn Văn A", 200, 1.5, LocalDate.now());
//         interactor.executeWithDate(req, req.ngayLap);
//         Assertions.assertEquals("Hóa đơn Việt Nam đã tạo", vm.messageProperty().get());
//         Assertions.assertTrue(vm.tongTienProperty().get() > 0);
//     }
//     @Test
//     public void testCreateVNInvoice_Fail_NegativeMoney() {
//         HoaDonViewModel vm = new HoaDonViewModel();
//         HoaDonVNPresenter presenter = new HoaDonVNPresenter(vm);
//         HoaDonVNControl interactor = new HoaDonVNControl(presenter);
//         RequestData req = new RequestData("2", "Nguyễn Văn B", -100, 1.2, LocalDate.now());
//         interactor.executeWithDate(req, req.ngayLap);
//         // logic của bạn chưa có kiểm tra, ta kiểm tra bằng giá trị âm
//         Assertions.assertTrue(vm.tongTienProperty().get() <= 0, "Tổng tiền không được âm!");
//     }
//     @Test
//     public void testCreateVNInvoice_Fail_EmptyName() {
//         HoaDonViewModel vm = new HoaDonViewModel();
//         HoaDonVNPresenter presenter = new HoaDonVNPresenter(vm);
//         HoaDonVNControl interactor = new HoaDonVNControl(presenter);
//         RequestData req = new RequestData("3", "", 200, 1.0, LocalDate.now());
//         interactor.executeWithDate(req, req.ngayLap);
//         Assertions.assertNotEquals("Hóa đơn Việt Nam đã tạo", vm.messageProperty().get(),
//                 "Tên khách hàng rỗng phải bị từ chối");
//     }
// }

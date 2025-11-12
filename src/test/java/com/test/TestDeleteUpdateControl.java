package com.test;

import com.infrastructure.InMemoryInvoiceStore;
import com.model.HoaDonVN;
import com.presentation.presenter.DeleteInvoicePresenter;
import com.presentation.presenter.UpdateInvoicePresenter;
import com.presentation.viewmodel.HoaDonViewModel;
import com.usecase.interactor.DeleteInvoiceControl;
import com.usecase.interactor.UpdateInvoiceControl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

public class TestDeleteUpdateControl {

    private InMemoryInvoiceStore store;

    @BeforeEach
    void setup() {
        store = InMemoryInvoiceStore.getInstance();
        store.clear();
    }

    @Test
    public void testDeleteInvoice() {
        // tạo hóa đơn mẫu
        HoaDonVN hd = new HoaDonVN(UUID.randomUUID().toString(), "A", LocalDate.now(), 100, 1);
        store.add(hd);

        HoaDonViewModel vm = new HoaDonViewModel();
        DeleteInvoicePresenter presenter = new DeleteInvoicePresenter(vm);
        DeleteInvoiceControl control = new DeleteInvoiceControl(presenter);
        control.execute(hd.getId());

        Assertions.assertEquals("Đã xóa hóa đơn có ID: " + hd.getId(), vm.messageProperty().get());
    }

    @Test
    public void testUpdateInvoice() {
        HoaDonVN hd = new HoaDonVN(UUID.randomUUID().toString(), "B", LocalDate.now(), 200, 1);
        store.add(hd);

        hd.setTenKhachHang("Khách Hàng Mới");
        HoaDonViewModel vm = new HoaDonViewModel();
        UpdateInvoicePresenter presenter = new UpdateInvoicePresenter(vm);
        UpdateInvoiceControl control = new UpdateInvoiceControl(presenter);
        control.execute(hd);

        Assertions.assertEquals("Cập nhật hóa đơn thành công", vm.messageProperty().get());
        Assertions.assertEquals("Khách Hàng Mới", store.findById(hd.getId()).get().getTenKhachHang());
    }
}

//xóa ID sai sửa hóa đơn không tồn tại
// public class TestDeleteUpdateControl {
//     private InMemoryInvoiceStore store;
//     @BeforeEach
//     void setup() {
//         store = InMemoryInvoiceStore.getInstance();
//         store.clear();
//     }
//     @Test
//     public void testDeleteInvoice_Success() {
//         HoaDonVN hd = new HoaDonVN(UUID.randomUUID().toString(), "A", LocalDate.now(), 100, 1);
//         store.add(hd);
//         HoaDonViewModel vm = new HoaDonViewModel();
//         DeleteInvoicePresenter presenter = new DeleteInvoicePresenter(vm);
//         DeleteInvoiceControl control = new DeleteInvoiceControl(presenter);
//         control.execute(hd.getId());
//         Assertions.assertTrue(vm.messageProperty().get().contains("Đã xóa"));
//     }
//     @Test
//     public void testDeleteInvoice_Fail_NotFound() {
//         HoaDonViewModel vm = new HoaDonViewModel();
//         DeleteInvoicePresenter presenter = new DeleteInvoicePresenter(vm);
//         DeleteInvoiceControl control = new DeleteInvoiceControl(presenter);
//         control.execute("abc123"); // không có ID này
//         Assertions.assertEquals("Không tìm thấy hóa đơn để xóa", vm.messageProperty().get());
//     }
//     @Test
//     public void testUpdateInvoice_Success() {
//         HoaDonVN hd = new HoaDonVN(UUID.randomUUID().toString(), "Old Name", LocalDate.now(), 200, 1);
//         store.add(hd);
//         hd.setTenKhachHang("New Name");
//         HoaDonViewModel vm = new HoaDonViewModel();
//         UpdateInvoicePresenter presenter = new UpdateInvoicePresenter(vm);
//         UpdateInvoiceControl control = new UpdateInvoiceControl(presenter);
//         control.execute(hd);
//         Assertions.assertEquals("Cập nhật hóa đơn thành công", vm.messageProperty().get());
//     }
//     @Test
//     public void testUpdateInvoice_Fail_NotInStore() {
//         HoaDonVN hd = new HoaDonVN(UUID.randomUUID().toString(), "Ghost", LocalDate.now(), 150, 1);
//         HoaDonViewModel vm = new HoaDonViewModel();
//         UpdateInvoicePresenter presenter = new UpdateInvoicePresenter(vm);
//         UpdateInvoiceControl control = new UpdateInvoiceControl(presenter);
//         control.execute(hd); // không add vào store
//         Assertions.assertTrue(store.findAll().isEmpty(), "Store vẫn rỗng, không thể update");
//     }
// }

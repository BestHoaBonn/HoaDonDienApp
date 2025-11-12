package com.test;

import com.infrastructure.InMemoryInvoiceStore;
import com.model.HoaDonVN;
import com.presentation.presenter.DeleteInvoicePresenter;
import com.presentation.presenter.UpdateInvoicePresenter;
import com.presentation.viewmodel.HoaDonViewModel;
import com.usecase.interactor.DeleteInvoiceControl;
import com.usecase.interactor.UpdateInvoiceControl;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TestDeleteUpdateControl {

    private InMemoryInvoiceStore store;

    @BeforeEach
    void setup() {
        store = InMemoryInvoiceStore.getInstance();
        store.clear();
    }

    @Test
    public void testDeleteInvoice_Success() {
        HoaDonVN hd = new HoaDonVN(UUID.randomUUID().toString(), "A", LocalDate.now(), 100, 1);
        store.add(hd);

        HoaDonViewModel vm = new HoaDonViewModel();
        DeleteInvoicePresenter presenter = new DeleteInvoicePresenter(vm);
        DeleteInvoiceControl control = new DeleteInvoiceControl(presenter);

        control.execute(hd.getId());
        assertTrue(vm.messageProperty().get().contains("Đã xóa"));
    }

    @Test
    public void testDeleteInvoice_Fail_NotFound() {
        HoaDonViewModel vm = new HoaDonViewModel();
        DeleteInvoicePresenter presenter = new DeleteInvoicePresenter(vm);
        DeleteInvoiceControl control = new DeleteInvoiceControl(presenter);

        //  Không tồn tại ID => phải hiện lỗi
        control.execute("abc123");
        assertEquals("Không tìm thấy hóa đơn để xóa", vm.messageProperty().get());
    }

    @Test
    public void testUpdateInvoice_Fail_NotInStore() {
        HoaDonVN hd = new HoaDonVN(UUID.randomUUID().toString(), "Ghost", LocalDate.now(), 150, 1);
        HoaDonViewModel vm = new HoaDonViewModel();
        UpdateInvoicePresenter presenter = new UpdateInvoicePresenter(vm);
        UpdateInvoiceControl control = new UpdateInvoiceControl(presenter);

        // hóa đơn chưa add
        control.execute(hd);
        assertTrue(store.findById(hd.getId()).isEmpty(), "Không thể cập nhật hóa đơn chưa tồn tại");
    }
}

package com.test;

import com.infrastructure.InMemoryInvoiceStore;
import com.model.HoaDonNN;
import com.model.HoaDonVN;
import com.presentation.presenter.FilterInvoicePresenter;
import com.presentation.presenter.StatsPresenter;
import com.presentation.viewmodel.HoaDonViewModel;
import com.usecase.interactor.FilterInvoiceControl;
import com.usecase.interactor.StatsInvoiceControl;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestStatsAndFilter {

    private InMemoryInvoiceStore store;

    @BeforeEach
    void setup() {
        store = InMemoryInvoiceStore.getInstance();
        store.clear();
        store.add(new HoaDonVN("1", "A", LocalDate.of(2025, 1, 5), 100, 1.2));
        store.add(new HoaDonVN("2", "A", LocalDate.of(2025, 1, 10), 200, 1.2));
        store.add(new HoaDonNN("3", "B", LocalDate.of(2025, 2, 15), 300, 0.1));
    }

    @Test
    public void testFilterByMonth_Success() {
        HoaDonViewModel vm = new HoaDonViewModel();
        FilterInvoicePresenter presenter = new FilterInvoicePresenter(vm);
        FilterInvoiceControl control = new FilterInvoiceControl(presenter);
        control.execute(1);

        List<?> result = vm.getFilteredList();
        assertEquals(2, result.size());
    }

    @Test
    public void testFilterByMonth_Fail_NoData() {
        HoaDonViewModel vm = new HoaDonViewModel();
        FilterInvoicePresenter presenter = new FilterInvoicePresenter(vm);
        FilterInvoiceControl control = new FilterInvoiceControl(presenter);
        control.execute(12);

        // tháng 12 không có hóa đơn
        assertTrue(vm.getFilteredList().isEmpty(), "Không có hóa đơn tháng 12");
    }

    @Test
    public void testStatsReport_Fail_EmptyStore() {
        store.clear();
        HoaDonViewModel vm = new HoaDonViewModel();
        StatsPresenter presenter = new StatsPresenter(vm);
        StatsInvoiceControl control = new StatsInvoiceControl(presenter);
        control.execute();

        // không có dữ liệu thống kê
        assertTrue(vm.messageProperty().get() == null || vm.messageProperty().get().isEmpty(),
                "Không có dữ liệu thống kê khi store trống");
    }
}

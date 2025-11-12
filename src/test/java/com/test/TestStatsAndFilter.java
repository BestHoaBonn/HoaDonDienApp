package com.test;

import com.infrastructure.InMemoryInvoiceStore;
import com.model.HoaDonNN;
import com.model.HoaDonVN;
import com.presentation.presenter.StatsPresenter;
import com.presentation.presenter.FilterInvoicePresenter;
import com.presentation.viewmodel.HoaDonViewModel;
import com.usecase.interactor.StatsInvoiceControl;
import com.usecase.interactor.FilterInvoiceControl;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

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
    public void testStatsReport() {
        HoaDonViewModel vm = new HoaDonViewModel();
        StatsPresenter presenter = new StatsPresenter(vm);
        StatsInvoiceControl control = new StatsInvoiceControl(presenter);
        control.execute();

        Assertions.assertTrue(vm.messageProperty().get().contains("A:"));
        Assertions.assertTrue(vm.messageProperty().get().contains("Trung bình KH nước ngoài"));
    }

    @Test
    public void testFilterByMonth() {
        HoaDonViewModel vm = new HoaDonViewModel();
        FilterInvoicePresenter presenter = new FilterInvoicePresenter(vm);
        FilterInvoiceControl control = new FilterInvoiceControl(presenter);
        control.execute(1);

        List<?> result = vm.getFilteredList();
        Assertions.assertEquals(2, result.size());
    }
}

//tháng không có dữ liệu trung bình nước ngoài = 0
// public class TestStatsAndFilter {
//     private InMemoryInvoiceStore store;
//     @BeforeEach
//     void setup() {
//         store = InMemoryInvoiceStore.getInstance();
//         store.clear();
//         store.add(new HoaDonVN("1", "A", LocalDate.of(2025, 1, 5), 100, 1.2));
//         store.add(new HoaDonVN("2", "A", LocalDate.of(2025, 1, 10), 200, 1.2));
//         store.add(new HoaDonNN("3", "B", LocalDate.of(2025, 2, 15), 300, 0.1));
//     }
//     @Test
//     public void testStatsReport_Success() {
//         HoaDonViewModel vm = new HoaDonViewModel();
//         StatsPresenter presenter = new StatsPresenter(vm);
//         StatsInvoiceControl control = new StatsInvoiceControl(presenter);
//         control.execute();
//         Assertions.assertTrue(vm.messageProperty().get().contains("A:"));
//         Assertions.assertTrue(vm.messageProperty().get().contains("Trung bình KH nước ngoài"));
//     }
//     @Test
//     public void testStatsReport_Fail_EmptyStore() {
//         store.clear();
//         HoaDonViewModel vm = new HoaDonViewModel();
//         StatsPresenter presenter = new StatsPresenter(vm);
//         StatsInvoiceControl control = new StatsInvoiceControl(presenter);
//         control.execute();
//         Assertions.assertTrue(vm.messageProperty().get().contains("Trung bình KH nước ngoài: 0")
//                 || vm.messageProperty().get().isEmpty());
//     }
//     @Test
//     public void testFilterByMonth_Success() {
//         HoaDonViewModel vm = new HoaDonViewModel();
//         FilterInvoicePresenter presenter = new FilterInvoicePresenter(vm);
//         FilterInvoiceControl control = new FilterInvoiceControl(presenter);
//         control.execute(1);
//         List<?> result = vm.getFilteredList();
//         Assertions.assertEquals(2, result.size());
//     }
//     @Test
//     public void testFilterByMonth_NoResults() {
//         HoaDonViewModel vm = new HoaDonViewModel();
//         FilterInvoicePresenter presenter = new FilterInvoicePresenter(vm);
//         FilterInvoiceControl control = new FilterInvoiceControl(presenter);
//         control.execute(12); // tháng 12 không có dữ liệu
//         List<?> result = vm.getFilteredList();
//         Assertions.assertTrue(result.isEmpty(), "Không có hóa đơn tháng 12");
//     }
// }

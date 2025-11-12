package com.usecase.interactor;

import com.infrastructure.InMemoryInvoiceStore;
import com.model.HoaDon;
import com.usecase.OutputBoundaryHoaDon;
import com.usecase.ResponseData;

import java.util.List;

public class FilterInvoiceControl {

    private final OutputBoundaryHoaDon presenter;

    public FilterInvoiceControl(OutputBoundaryHoaDon presenter) {
        this.presenter = presenter;
    }

    public void execute(int month) {
        InMemoryInvoiceStore store = InMemoryInvoiceStore.getInstance();
        List<HoaDon> filtered = store.findByMonth(month);
        presenter.present(new ResponseData("Hóa đơn trong tháng " + month, filtered));
    }
}

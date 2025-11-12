package com.usecase.interactor;

import com.infrastructure.InMemoryInvoiceStore;
import com.model.HoaDon;
import com.usecase.OutputBoundaryHoaDon;
import com.usecase.ResponseData;

public class UpdateInvoiceControl {

    private final OutputBoundaryHoaDon presenter;

    public UpdateInvoiceControl(OutputBoundaryHoaDon presenter) {
        this.presenter = presenter;
    }

    public void execute(HoaDon updated) {
        InMemoryInvoiceStore store = InMemoryInvoiceStore.getInstance();
        store.update(updated);
        presenter.present(new ResponseData("Cập nhật hóa đơn thành công"));
    }
}

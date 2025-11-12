package com.usecase.interactor;

import com.infrastructure.InMemoryInvoiceStore;
import com.usecase.OutputBoundaryHoaDon;
import com.usecase.ResponseData;

public class DeleteInvoiceControl {

    private final OutputBoundaryHoaDon presenter;

    public DeleteInvoiceControl(OutputBoundaryHoaDon presenter) {
        this.presenter = presenter;
    }

    public void execute(String id) {
        InMemoryInvoiceStore store = InMemoryInvoiceStore.getInstance();
        boolean exists = store.findById(id).isPresent();
        if (exists) {
            store.delete(id);
            presenter.present(new ResponseData("Đã xóa hóa đơn có ID: " + id));
        } else {
            presenter.present(new ResponseData("Không tìm thấy hóa đơn để xóa"));
        }
    }
}

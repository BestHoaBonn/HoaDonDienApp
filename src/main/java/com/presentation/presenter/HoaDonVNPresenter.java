package com.presentation.presenter;

import com.infrastructure.InMemoryInvoiceStore;
import com.presentation.viewmodel.HoaDonViewModel;
import com.usecase.OutputBoundaryHoaDon;
import com.usecase.ResponseData;

public class HoaDonVNPresenter implements OutputBoundaryHoaDon {

    private final HoaDonViewModel viewModel;

    public HoaDonVNPresenter(HoaDonViewModel vm) {
        this.viewModel = vm;
    }

    @Override
    public void present(ResponseData response) {
        viewModel.setMessage(response.message);
        viewModel.setTongTien(response.tongTien);

        if (response.created != null) {
            InMemoryInvoiceStore.getInstance().add(response.created);
        }
    }
}

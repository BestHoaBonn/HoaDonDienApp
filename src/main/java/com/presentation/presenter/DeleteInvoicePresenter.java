package com.presentation.presenter;

import com.presentation.viewmodel.HoaDonViewModel;
import com.usecase.OutputBoundaryHoaDon;
import com.usecase.ResponseData;

public class DeleteInvoicePresenter implements OutputBoundaryHoaDon {

    private final HoaDonViewModel viewModel;

    public DeleteInvoicePresenter(HoaDonViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(ResponseData response) {
        viewModel.setMessage(response.message);
    }
}

package com.presentation.presenter;

import com.presentation.viewmodel.HoaDonViewModel;
import com.usecase.OutputBoundaryHoaDon;
import com.usecase.ResponseData;

public class StatsPresenter implements OutputBoundaryHoaDon {

    private final HoaDonViewModel viewModel;

    public StatsPresenter(HoaDonViewModel vm) {
        this.viewModel = vm;
    }

    @Override
    public void present(ResponseData response) {
        viewModel.setMessage((String) response.data);
    }
}

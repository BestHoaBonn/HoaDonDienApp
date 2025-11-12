package com.usecase.interactor;

import com.usecase.InputBoundary;
import com.usecase.RequestData;

public abstract class HoaDonControl implements InputBoundary<RequestData> {

    @Override
    public abstract void execute(RequestData request);
}

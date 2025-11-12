package com.usecase.interactor;

import com.infrastructure.InMemoryInvoiceStore;
import com.usecase.OutputBoundaryHoaDon;
import com.usecase.ResponseData;

import java.util.Map;
import java.util.stream.Collectors;

public class StatsInvoiceControl {

    private final OutputBoundaryHoaDon presenter;

    public StatsInvoiceControl(OutputBoundaryHoaDon presenter) {
        this.presenter = presenter;
    }

    public void execute() {
        InMemoryInvoiceStore store = InMemoryInvoiceStore.getInstance();

        Map<String, Double> totalByCustomer = store.totalByCustomer();
        double avgForeign = store.averageForeign();

        String report = totalByCustomer.entrySet()
                .stream()
                .map(e -> e.getKey() + ": " + e.getValue())
                .collect(Collectors.joining("\n"));
        report += "\n\nTrung bình KH nước ngoài: " + String.format("%.2f", avgForeign);

        presenter.present(new ResponseData("Thống kê hoàn tất", report));
    }
}

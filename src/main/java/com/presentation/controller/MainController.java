package com.presentation.controller;

import com.infrastructure.InMemoryInvoiceStore;
import com.model.HoaDon;
import com.presentation.presenter.*;
import com.presentation.viewmodel.HoaDonViewModel;
import com.usecase.RequestData;
import com.usecase.interactor.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

public class MainController {

    @FXML
    private TextField txtNameVN;
    @FXML
    private TextField txtTienVN;
    @FXML
    private TextField txtHeSoVN;

    @FXML
    private TextField txtNameNN;
    @FXML
    private TextField txtTienNN;
    @FXML
    private TextField txtThueNN;

    @FXML
    private TextField txtSearch;
    @FXML
    private TextField txtMonth;

    @FXML
    private TableView<HoaDon> tableInvoices;
    @FXML
    private TableColumn<HoaDon, String> colId;
    @FXML
    private TableColumn<HoaDon, String> colName;
    @FXML
    private TableColumn<HoaDon, String> colDate;
    @FXML
    private TableColumn<HoaDon, Number> colTotal;
    @FXML
    private TableColumn<HoaDon, String> colType;

    @FXML
    private ChoiceBox<String> choiceFilter;
    @FXML
    private TextArea txtDetail;
    @FXML
    private DatePicker dateVN;
    @FXML
    private DatePicker dateNN;

    private final InMemoryInvoiceStore store = InMemoryInvoiceStore.getInstance();
    private final ObservableList<HoaDon> obsList = FXCollections.observableArrayList();
    private final DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    public void initialize() {
        choiceFilter.getItems().addAll("Tất cả", "VN", "NN");
        choiceFilter.setValue("Tất cả");

        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getId()));
        colName.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTenKhachHang()));
        colDate.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNgayLap().format(df)));
        colTotal.setCellValueFactory(c -> new javafx.beans.property.SimpleDoubleProperty(c.getValue().tinhTien()));
        colType.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getClass().getSimpleName()));

        tableInvoices.setItems(obsList);
        tableInvoices.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            if (n != null) {
                showDetail(n);
            }
        });

        refreshList();
    }

    @FXML
    private void onCreateVN() {
        try {
            String id = UUID.randomUUID().toString();
            String name = txtNameVN.getText().trim();
            double tien = Double.parseDouble(txtTienVN.getText());
            double heSo = Double.parseDouble(txtHeSoVN.getText());
            LocalDate ngayLap = (dateVN.getValue() != null) ? dateVN.getValue() : LocalDate.now();

            HoaDonViewModel vm = new HoaDonViewModel();
            HoaDonVNPresenter presenter = new HoaDonVNPresenter(vm);
            HoaDonVNControl interactor = new HoaDonVNControl(presenter);

            RequestData req = new RequestData(id, name, tien, heSo, ngayLap);
            interactor.executeWithDate(req, ngayLap);

            refreshList();
            clearVNForm();
            showTempMessage("Tạo hóa đơn VN thành công");
        } catch (Exception e) {
            showError("Lỗi nhập liệu: " + e.getMessage());
        }
    }

    @FXML
    private void onCreateNN() {
        try {
            String id = UUID.randomUUID().toString();
            String name = txtNameNN.getText().trim();
            double tien = Double.parseDouble(txtTienNN.getText());
            double thue = Double.parseDouble(txtThueNN.getText());
            LocalDate ngayLap = (dateNN.getValue() != null) ? dateNN.getValue() : LocalDate.now();

            HoaDonViewModel vm = new HoaDonViewModel();
            HoaDonNNPresenter presenter = new HoaDonNNPresenter(vm);
            HoaDonNNControl interactor = new HoaDonNNControl(presenter);

            RequestData req = new RequestData(id, name, tien, thue, ngayLap);
            interactor.executeWithDate(req, ngayLap);

            refreshList();
            clearNNForm();
            showTempMessage("Tạo hóa đơn NN thành công");
        } catch (Exception e) {
            showError("Lỗi nhập liệu: " + e.getMessage());
        }
    }

    @FXML
    private void onDelete() {
        HoaDon selected = tableInvoices.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Chưa chọn hóa đơn để xóa!");
            return;
        }

        HoaDonViewModel vm = new HoaDonViewModel();
        DeleteInvoicePresenter presenter = new DeleteInvoicePresenter(vm);
        DeleteInvoiceControl interactor = new DeleteInvoiceControl(presenter);

        interactor.execute(selected.getId());
        refreshList();
        showTempMessage(vm.messageProperty().get());
    }

    @FXML
    private void onEdit() {
        HoaDon selected = tableInvoices.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Chưa chọn hóa đơn để sửa!");
            return;
        }

        TextInputDialog dialog = new TextInputDialog(selected.getTenKhachHang());
        dialog.setHeaderText("Sửa tên khách hàng");
        dialog.setContentText("Tên mới:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name -> {
            selected.setTenKhachHang(name);
            HoaDonViewModel vm = new HoaDonViewModel();
            UpdateInvoicePresenter presenter = new UpdateInvoicePresenter(vm);
            UpdateInvoiceControl interactor = new UpdateInvoiceControl(presenter);
            interactor.execute(selected);
            refreshList();
            showTempMessage(vm.messageProperty().get());
        });
    }

    @FXML
    private void onFilterByMonth() {
        try {
            int month = Integer.parseInt(txtMonth.getText());
            HoaDonViewModel vm = new HoaDonViewModel();
            FilterInvoicePresenter presenter = new FilterInvoicePresenter(vm);
            FilterInvoiceControl interactor = new FilterInvoiceControl(presenter);
            interactor.execute(month);

            obsList.setAll(vm.getFilteredList());
            showTempMessage("Đã lọc hóa đơn tháng " + month);
        } catch (NumberFormatException e) {
            showError("Nhập tháng hợp lệ (1-12)");
        }
    }

    @FXML
    private void onStats() {
        HoaDonViewModel vm = new HoaDonViewModel();
        StatsPresenter presenter = new StatsPresenter(vm);
        StatsInvoiceControl interactor = new StatsInvoiceControl(presenter);
        interactor.execute();
        showTempMessage(vm.messageProperty().get());
    }

    @FXML
    private void onSearch() {
        String keyword = txtSearch.getText().trim();
        if (keyword.isEmpty()) {
            refreshList();
        } else {
            obsList.setAll(store.search(keyword));
        }
    }

    @FXML
    private void onRefresh() {
        refreshList();
    }

    private void refreshList() {
        obsList.clear();
        String filter = choiceFilter.getValue();
        if (filter.equals("Tất cả")) {
            obsList.addAll(store.findAll());
        } else if (filter.equals("VN")) {
            store.findAll().stream().filter(h -> h.getClass().getSimpleName().equals("HoaDonVN")).forEach(obsList::add);
        } else if (filter.equals("NN")) {
            store.findAll().stream().filter(h -> h.getClass().getSimpleName().equals("HoaDonNN")).forEach(obsList::add);
        }
    }

    private void clearVNForm() {
        txtNameVN.clear();
        txtTienVN.clear();
        txtHeSoVN.clear();
    }

    private void clearNNForm() {
        txtNameNN.clear();
        txtTienNN.clear();
        txtThueNN.clear();
    }

    private void showDetail(HoaDon h) {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(h.getId()).append("\n");
        sb.append("Khách hàng: ").append(h.getTenKhachHang()).append("\n");
        sb.append("Ngày: ").append(h.getNgayLap().format(df)).append("\n");
        sb.append("Tổng tiền: ").append(String.format("%.2f", h.tinhTien())).append("\n");
        sb.append("Loại: ").append(h.getClass().getSimpleName());
        txtDetail.setText(sb.toString());
    }

    private void showTempMessage(String msg) {
        txtDetail.setText(msg);
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        a.showAndWait();
    }
}

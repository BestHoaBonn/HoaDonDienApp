package com.presentation.viewmodel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.model.HoaDon;

public class HoaDonViewModel {

    private final StringProperty message = new SimpleStringProperty();
    private final DoubleProperty tongTien = new SimpleDoubleProperty();
    private final ObservableList<HoaDon> filteredList = FXCollections.observableArrayList();

    public StringProperty messageProperty() {
        return message;
    }

    public DoubleProperty tongTienProperty() {
        return tongTien;
    }

    public void setMessage(String msg) {
        message.set(msg);
    }

    public void setTongTien(double t) {
        tongTien.set(t);
    }

    public ObservableList<HoaDon> getFilteredList() {
        return filteredList;
    }

    public void setFilteredList(Object data) {
        if (data instanceof java.util.List<?>) {
            filteredList.setAll((java.util.List<HoaDon>) data);
        }
    }
}

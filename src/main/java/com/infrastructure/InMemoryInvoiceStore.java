package com.infrastructure;

import com.model.HoaDon;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryInvoiceStore {

    private final List<HoaDon> data = new ArrayList<>();
    private static final InMemoryInvoiceStore INSTANCE = new InMemoryInvoiceStore();

    private InMemoryInvoiceStore() {
    }

    public static InMemoryInvoiceStore getInstance() {
        return INSTANCE;
    }

    public synchronized void add(HoaDon h) {
        data.add(h);
    }

    public synchronized void delete(String id) {
        data.removeIf(h -> h.getId().equals(id));
    }

    public synchronized void update(HoaDon updated) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId().equals(updated.getId())) {
                data.set(i, updated);
                break;
            }
        }
    }

    public synchronized List<HoaDon> findAll() {
        return new ArrayList<>(data);
    }

    public synchronized Optional<HoaDon> findById(String id) {
        return data.stream().filter(h -> h.getId().equals(id)).findFirst();
    }

    public synchronized List<HoaDon> search(String keyword) {
        String lower = keyword.toLowerCase();
        return data.stream()
                .filter(h -> h.getTenKhachHang().toLowerCase().contains(lower)
                || h.getId().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    public synchronized Map<String, Double> totalByCustomer() {
        return data.stream().collect(Collectors.groupingBy(
                HoaDon::getTenKhachHang,
                Collectors.summingDouble(HoaDon::tinhTien)
        ));
    }

    public synchronized double averageForeign() {
        return data.stream()
                .filter(h -> h.getClass().getSimpleName().equals("HoaDonNN"))
                .mapToDouble(HoaDon::tinhTien)
                .average().orElse(0.0);
    }

    public synchronized List<HoaDon> findByMonth(int month) {
        return data.stream()
                .filter(h -> h.getNgayLap().getMonthValue() == month)
                .collect(Collectors.toList());
    }

    public synchronized void clear() {
        data.clear();
    }
}

package com.example.project.DTO;

import java.util.List;

public class QrHistoryResponse {
    private int page;
    private int limit;
    private long total;
    private List<QrHistoryItem> qrcodes;

    public QrHistoryResponse(int page, int limit, long total, List<QrHistoryItem> qrcodes) {
        this.page = page;
        this.limit = limit;
        this.total = total;
        this.qrcodes = qrcodes;
    }

    public int getPage() { return page; }
    public int getLimit() { return limit; }
    public long getTotal() { return total; }
    public List<QrHistoryItem> getQrcodes() { return qrcodes; }
}

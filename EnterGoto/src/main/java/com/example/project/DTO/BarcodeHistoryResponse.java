package com.example.project.DTO;
import java.util.List;

public class BarcodeHistoryResponse {
    private int page;
    private int limit;
    private long total;
    private List<BarcodeHistoryItem> barcodes;

    public BarcodeHistoryResponse(int page, int limit, long total, List<BarcodeHistoryItem> barcodes) {
        this.page = page;
        this.limit = limit;
        this.total = total;
        this.barcodes = barcodes;
    }

    public int getPage() { return page; }
    public int getLimit() { return limit; }
    public long getTotal() { return total; }
    public List<BarcodeHistoryItem> getBarcodes() { return barcodes; }
}


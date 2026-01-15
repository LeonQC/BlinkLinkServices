package com.example.project.DTO;

public class BarcodeHistoryItem {
    private String original_url;
    private String barcode_id;
    private String barcode_url;
    private String title;
    private int scans;
    private String created_at;

    public BarcodeHistoryItem(String originalUrl, String barcodeId, String barcodeUrl, String title, int scans, String createdAt) {
        this.original_url = originalUrl;
        this.barcode_id = barcodeId;
        this.barcode_url = barcodeUrl;
        this.title = title;
        this.scans = scans;
        this.created_at = createdAt;
    }

    public String getOriginal_url() { return original_url; }
    public String getBarcode_id() { return barcode_id; }
    public String getBarcode_url() { return barcode_url; }
    public String getTitle() { return title; }
    public int getScans() { return scans; }
    public String getCreated_at() { return created_at; }
}

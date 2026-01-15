package com.example.project.DTO;


public class QrHistoryItem {
    private String original_url;
    private String qr_code_id;
    private String qr_code_url;
    private String title;
    private int scans;
    private String created_at;

    public QrHistoryItem(String originalUrl, String qrCodeId, String qrCodeUrl, String title, int scans, String createdAt) {
        this.original_url = originalUrl;
        this.qr_code_id = qrCodeId;
        this.qr_code_url = qrCodeUrl;
        this.title = title;
        this.scans = scans;
        this.created_at = createdAt;
    }

    public String getOriginal_url() { return original_url; }
    public String getQr_code_id() { return qr_code_id; }
    public String getQr_code_url() { return qr_code_url; }
    public String getTitle() { return title; }
    public int getScans() { return scans; }
    public String getCreated_at() { return created_at; }
}

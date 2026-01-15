package com.example.project.DTO;

public class UrlHistoryItem {
    private String original_url;
    private String short_code;
    private String short_url;
    private String title;
    private long clicks;
    private String created_at;

    public UrlHistoryItem(String originalUrl, String shortCode, String shortUrl, String title, long clicks, String createdAt) {
        this.original_url = originalUrl;
        this.short_code = shortCode;
        this.short_url = shortUrl;
        this.title = title;
        this.clicks = clicks;
        this.created_at = createdAt;
    }

    public String getOriginal_url() { return original_url; }
    public String getShort_code() { return short_code; }
    public String getShort_url() { return short_url; }
    public String getTitle() { return title; }
    public long getClicks() { return clicks; }
    public String getCreated_at() { return created_at; }
}

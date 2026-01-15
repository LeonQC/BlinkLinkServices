package com.example.project.DTO;

import java.util.List;

public class UrlHistoryResponse {
    private int page;
    private int limit;
    private long total;
    private List<UrlHistoryItem> urls;

    public UrlHistoryResponse(int page, int limit, long total, List<UrlHistoryItem> urls) {
        this.page = page;
        this.limit = limit;
        this.total = total;
        this.urls = urls;
    }

    public int getPage() { return page; }
    public int getLimit() { return limit; }
    public long getTotal() { return total; }
    public List<UrlHistoryItem> getUrls() { return urls; }
}

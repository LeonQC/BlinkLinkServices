package com.example.project.DTO;

// automatically transfered to json

public class UrlResponse {
    private String url;
    private String short_code;
    private String alias;
    private String shortened_url;
    private String title;
    private int clicks;
    private long user_id;
    private String created_at;


    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getShort_code() {
        return short_code;
    }
    public void setShort_code(String short_code) {
        this.short_code = short_code;
    }
    public String getAlias() {
        return alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }
    public String getShortened_url() {
        return shortened_url;
    }
    public void setShortened_url(String shortened_url) {
        this.shortened_url = shortened_url;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getClicks() {
        return clicks;
    }
    public void setClicks(int clicks) {
        this.clicks = clicks;
    }
    public long getUser_id() {
        return user_id;
    }
    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
    public String getCreated_at() {
        return created_at;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }


}

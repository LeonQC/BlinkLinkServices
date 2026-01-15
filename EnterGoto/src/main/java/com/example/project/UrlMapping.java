package com.example.project;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(
        name = "url_mappings",
        indexes = {
                @Index(name = "idx_code", columnList = "code", unique = true),
                @Index(name = "idx_alias", columnList = "alias", unique = true),
                @Index(name = "idx_long_url", columnList = "long_url")
        }
)
public class UrlMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // DB primary key


    @Column(name = "short_code",nullable = false, unique = true)
    private String code;

    @Column(name = "original_url", nullable = false)
    private String longUrl;

    @Column(unique = true, length = 64)
    private String alias; // nullable

    @Column(nullable = false)
    private long clicks = 0L;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "title" ,nullable = true)
    private String title;

    @Column(name = "userId" ,nullable = true)
    private Long userId;



    protected UrlMapping() {}


    public UrlMapping(String code, String longUrl, String alias) {
        this.code = code;
        this.longUrl = longUrl;
        this.alias = alias;
        this.createdAt = Instant.now();
        this.clicks = 0L;
    }


    public Long getId() { return id; }

    public String getCode() { return code; }

    public String getLongUrl() { return longUrl; }

    public String getAlias() { return alias; }

    public long getClicks() { return clicks; }

    public Instant getCreatedAt() { return createdAt; }


    public void setAlias(String alias) { this.alias = alias; }

    public void setClicks(long clicks) { this.clicks = clicks; }

    public void incrementClicks() { this.clicks++; }
}


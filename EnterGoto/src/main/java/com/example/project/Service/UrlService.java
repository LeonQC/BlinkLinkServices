package com.example.project.Service;

import com.example.project.Exceptions.ApiException;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.project.UrlRequest;
import com.example.project.UrlMapping;
import com.example.project.Repository.InMemoryUrlRepository;
import com.example.project.Repository.UrlRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

        @Service
        public class UrlService {

            private final UrlRepository repo; // swap later to JPA
            private final AtomicLong counter = new AtomicLong(100_000);     // start non-trivial

            public UrlService(UrlRepository repo) {
                this.repo = repo;
            }

            // METHOD: long -> short
            public UrlMapping createShort(UrlRequest req) {
                String url = req.getUrl();
                String alias = normalize(req.getAlias());

                // if no alias provided, reuse existing mapping for same longUrl
                if (alias == null) {
                    Optional<UrlMapping> existing = repo.findByLongUrl(url);
                    if (existing.isPresent()) return existing.get();
                } else {
                    if (repo.existsByAlias(alias)) throw new ApiException(HttpStatus.CONFLICT, "Alias already exists");
                }

                String code = (alias != null) ? alias : nextCode();
                if (repo.existsByCode(code)) throw new ApiException(HttpStatus.CONFLICT, "Short code collision");

                UrlMapping mapping = new UrlMapping(code, url, alias);
                return repo.save(mapping);
            }

            // METHOD: short -> long (for redirect)
            public String resolveShort(String code) {
                UrlMapping m = repo.findByCode(code).orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Short code not found"));
                m.incrementClicks();
                repo.save(m);
                return m.getLongUrl();
            }

            // 2.3 Set or Update Alias
            public UrlMapping setOrUpdateAlias(String code, String newAliasRaw) {
                String newAlias = normalize(newAliasRaw);
                if (newAlias == null) {
                    throw new ApiException(HttpStatus.BAD_REQUEST, "Alias cannot be empty");
                }

                UrlMapping m = repo.findByCode(code)
                        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Short code not found"));

                // If alias already taken by someone else -> conflict
                Optional<UrlMapping> aliasOwner = repo.findByAlias(newAlias);
                if (aliasOwner.isPresent() && !aliasOwner.get().getCode().equals(m.getCode())) {
                    throw new ApiException(HttpStatus.CONFLICT, "Alias already exists");
                }

                m.setAlias(newAlias);
                return repo.save(m);
            }

            //2.4 Get Alias
            public UrlMapping getByCode(String shortCode) {
                return repo.findByCode(shortCode).orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Short code not found"));
            }

            //2.5 Remove Alias
            public void removeAlias(String shortCode) {
                UrlMapping m = repo.findByCode(shortCode).orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Short code not found"));
                m.setAlias(null);
                repo.save(m);
            }


            public String resolveAny(String key) {
                UrlMapping m = repo.findByCode(key).orElse(null);
                if (m == null) {
                    m = repo.findByAlias(key)
                            .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Short code not found"));
                }

                String longUrl = m.getLongUrl();
                if (longUrl == null || longUrl.trim().isEmpty()) {throw new ApiException(HttpStatus.BAD_REQUEST, "Long URL is missing");
                }

                m.incrementClicks();
                repo.save(m);
                return longUrl;
            }

            //5.5 Get url History
            public Page<UrlMapping> getUrlHistory(int page, int limit, String sort, String order) {
                int pageIndex = Math.max(page, 1) - 1;
                int size = Math.min(Math.max(limit, 1), 200);

                String sortField = switch (sort) {
                    case "clicks" -> "clicks";
                    case "created_at" -> "createdAt";
                    default -> "createdAt";
                };

                Sort.Direction dir = "asc".equalsIgnoreCase(order) ? Sort.Direction.ASC : Sort.Direction.DESC;
                Pageable pageable = PageRequest.of(pageIndex, size, Sort.by(dir, sortField));
                return repo.findAll(pageable);
            }



            // --------- Helpers ---------

            public boolean existsCodeOrAlias(String key) {
                return repo.existsByCode(key) || repo.existsByAlias(key);
            }

            private String nextCode() {
                long n = counter.getAndIncrement();
                return toBase62(n);
            }

            private String normalize(String s) {
                if (s == null) return null;
                String t = s.trim();
                return t.isEmpty() ? null : t;
            }

            // Tiny Base62 for nicer codes
            private static final char[] ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
            private static String toBase62(long v) {
                StringBuilder sb = new StringBuilder();
                while (v > 0) { sb.append(ALPHABET[(int)(v % 62)]); v /= 62; }
                return sb.reverse().toString();
            }
        }


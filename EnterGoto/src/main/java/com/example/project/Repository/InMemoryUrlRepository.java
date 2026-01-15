package com.example.project.Repository;

import com.example.project.UrlMapping;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryUrlRepository implements UrlRepository {

    private final Map<String, UrlMapping> byCode = new ConcurrentHashMap<>();
    private final Map<String, String> codeByAlias = new ConcurrentHashMap<>();
    private final Map<String, String> codeByLongUrl = new ConcurrentHashMap<>();

    @Override
    public Optional<UrlMapping> findByCode(String code) {  //optional： make sure not return null if searching result not exists
        return Optional.ofNullable(byCode.get(code));
    }

    @Override
    public Optional<UrlMapping> findByAlias(String alias) {
        String code = codeByAlias.get(alias);
        return code == null ? Optional.empty() : findByCode(code);
    }

    @Override
    public Optional<UrlMapping> findByLongUrl(String longUrl) {
        String code = codeByLongUrl.get(longUrl);
        return code == null ? Optional.empty() : findByCode(code);
    }

    @Override
    public boolean existsByCode(String code) {
        return byCode.containsKey(code);
    }

    @Override
    public boolean existsByAlias(String alias) {
        return codeByAlias.containsKey(alias);
    }

    @Override
    public UrlMapping save(UrlMapping mapping) {
        byCode.put(mapping.getCode(), mapping);

        if (mapping.getAlias() != null && !mapping.getAlias().isBlank()) {
            codeByAlias.put(mapping.getAlias(), mapping.getCode());
        }

        codeByLongUrl.put(mapping.getLongUrl(), mapping.getCode());

        return mapping;
    }
}


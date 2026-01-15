package com.example.project.Repository;

import com.example.project.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<UrlMapping, Long> {
    Optional<UrlMapping> findByCode(String code);
    Optional<UrlMapping> findByAlias(String alias);
    Optional<UrlMapping> findByLongUrl(String longUrl);
    boolean existsByCode(String code);
    boolean existsByAlias(String alias);
    //UrlMapping save(UrlMapping mapping);
}

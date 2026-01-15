package com.example.project.Controller;

import com.example.project.UrlRequest;
import com.example.project.DTO.UrlResponse;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.example.project.Service.UrlService;

import com.example.project.UrlMapping;

import java.util.Map;

/*
Main Controller:
1. accept request
2. call service to handle request
3. return a response

 */

@RestController
@RequestMapping("/api")   // this class is reachable through /api
public class UrlController {
    private final UrlService service;
    private final String hostBase = "http://localhost:8080/u/";

    public UrlController(UrlService service) {
        this.service = service;
    }

    // 2.1 create short URL
    // input: URL_request object;   output: URL_response object
    @PostMapping("/shorten")   // this method is reachable at POST /api/shorten
    // ResponseEntity: response body + status code + headers , while if return UrlResponse only allows one response body with status code always 200
    public ResponseEntity<UrlResponse> shorten(@Valid @RequestBody UrlRequest request) {  // take the json object from HTTP request body and convert to UrlRequest object
        UrlMapping mapping = service.createShort(request);

        UrlResponse resp = new UrlResponse();
        resp.setUrl(mapping.getLongUrl());
        resp.setShort_code(mapping.getCode());
        resp.setAlias(mapping.getAlias());
        resp.setShortened_url(hostBase + mapping.getCode());
        resp.setClicks((int) mapping.getClicks()); // change UrlResponse clicks to long OR cast to int
        resp.setCreated_at(mapping.getCreatedAt().toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    // 2.3 PATCH /urls/{shortCode}
    @PatchMapping("/urls/{shortCode}")
    public UrlResponse setOrUpdateAlias(@PathVariable String shortCode, @RequestBody Map<String, String> body) {
        String alias = body.get("alias");
        UrlMapping m = service.setOrUpdateAlias(shortCode, alias);
        return toResponse(m);
    }

    // 2.4 GET /urls/{shortCode}/alias
    @GetMapping("/urls/{shortCode}/alias")
    public Map<String, String> getAlias(@PathVariable String shortCode) {
        UrlMapping m = service.getByCode(shortCode);
        return Map.of("short_code", m.getCode(), "alias", m.getAlias()
        );
    }

    // 2.5 DELETE /urls/{shortCode}/alias
    @DeleteMapping("/urls/{shortCode}/alias")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAlias(@PathVariable String shortCode) {
        service.removeAlias(shortCode);
    }

    private UrlResponse toResponse(UrlMapping m) {
        UrlResponse r = new UrlResponse();
        r.setUrl(m.getLongUrl());
        r.setShort_code(m.getCode());
        r.setAlias(m.getAlias());

        String path = (m.getAlias() != null && !m.getAlias().trim().isEmpty()) ? m.getAlias() : m.getCode();
        r.setShortened_url(hostBase + path);
        r.setClicks((int) Math.min(Integer.MAX_VALUE, m.getClicks()));
        r.setUser_id(0L);

        r.setCreated_at(m.getCreatedAt().toString());
        return r;
    }

}

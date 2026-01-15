package com.example.project.Controller;



import com.example.project.Service.*;
import com.example.project.DTO.*;
import com.example.project.*;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HistoryController {

    private final UrlService service;

    // keep consistent with your project
    private final String hostBase = "http://localhost:8080";

    public HistoryController(UrlService service) {
        this.service = service;
    }

    // 5.1 Get URL History
    @GetMapping("/urls/history")
    public UrlHistoryResponse getUrlHistory(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "created_at") String sort,
            @RequestParam(defaultValue = "desc") String order
    ) {
        Page<UrlMapping> p = service.getUrlHistory(page, limit, sort, order);

        List<UrlHistoryItem> items = p.getContent().stream()
                .map(m -> new UrlHistoryItem(
                        m.getLongUrl(),
                        m.getCode(),
                        hostBase + "/u/" + (m.getAlias() != null && !m.getAlias().isBlank() ? m.getAlias() : m.getCode()),
                        null, // title not implemented yet
                        m.getClicks(),
                        m.getCreatedAt().toString()
                ))
                .toList();

        return new UrlHistoryResponse(page, limit, p.getTotalElements(), items);
    }

    // 5.2 Get QR Code History (derived for now)
    @GetMapping("/qrcodes/history")
    public QrHistoryResponse getQrHistory(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "created_at") String sort,
            @RequestParam(defaultValue = "desc") String order
    ) {
        Page<UrlMapping> p = service.getUrlHistory(page, limit, sort, order);

        List<QrHistoryItem> items = p.getContent().stream()
                .map(m -> new QrHistoryItem(
                        m.getLongUrl(),
                        "qr_" + m.getCode(),
                        hostBase + "/qr/" + m.getCode() + ".png",
                        null,
                        0, // scans not tracked yet
                        m.getCreatedAt().toString()
                ))
                .toList();

        return new QrHistoryResponse(page, limit, p.getTotalElements(), items);
    }

    // 5.3 Get Barcode History (derived for now)
    @GetMapping("/barcodes/history")
    public BarcodeHistoryResponse getBarcodeHistory(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "created_at") String sort,
            @RequestParam(defaultValue = "desc") String order
    ) {
        Page<UrlMapping> p = service.getUrlHistory(page, limit, sort, order);

        List<BarcodeHistoryItem> items = p.getContent().stream()
                .map(m -> new BarcodeHistoryItem(
                        m.getLongUrl(),
                        "bar_" + m.getCode(),
                        hostBase + "/barcode/" + m.getCode(),
                        null,
                        0,
                        m.getCreatedAt().toString()
                ))
                .toList();

        return new BarcodeHistoryResponse(page, limit, p.getTotalElements(), items);
    }
}

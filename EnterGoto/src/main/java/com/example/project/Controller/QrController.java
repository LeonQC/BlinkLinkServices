package com.example.project.Controller;



import com.example.project.Exceptions.ApiException;
import com.example.project.Service.UrlService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/qr")
public class QrController {

    private final UrlService service;

    // Keep consistent with your existing redirect base
    private final String hostBase = "http://localhost:8080";
    private final String redirectBase = hostBase + "/u/";

    public QrController(UrlService service) {
        this.service = service;
    }

    /**
     * 3.1 Generate QR Code (URL to  PNG)
     * POST /qr/abc123  -> { "short_code": "abc123", "qr_url": "http://.../qr/abc123.png" }
     */
    @PostMapping("/{key}")
    public Map<String, String> generateQr(@PathVariable String key) {
        if (!service.existsCodeOrAlias(key)) {
            throw new ApiException(org.springframework.http.HttpStatus.NOT_FOUND, "Short code not found");
        }

        Map<String, String> resp = new HashMap<>();
        resp.put("short_code", key);
        resp.put("qr_url", hostBase + "/qr/" + key + ".png");
        return resp;
    }

    /**
     * 3.2 Get QR Code Image (PNG)
     * Example: GET /qr/abc123.png
     */
    @GetMapping(value = "/{key}.png", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getQrPng(@PathVariable String key) throws IOException, WriterException {
        if (!service.existsCodeOrAlias(key)) {
            throw new ApiException(org.springframework.http.HttpStatus.NOT_FOUND, "Short code not found");
        }

        String qrContent = redirectBase + key;

        byte[] png = renderQrPng(qrContent, 300, 300);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(png);
    }

    // helper: render QR PNG bytes
    private byte[] renderQrPng(String content, int width, int height) throws WriterException, IOException {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "PNG", out);
        return out.toByteArray();
    }
}

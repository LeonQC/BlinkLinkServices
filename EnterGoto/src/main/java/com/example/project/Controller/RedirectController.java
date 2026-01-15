package com.example.project.Controller;
import com.example.project.Service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;


//redirecting is not an API JSON response
// User clicks a link → server responds with an HTTP redirect → browser navigates to the long URL
// instead of calling the API, it's calling the URL
// navigate directly instead of returning a JSON response

@RestController
@RequestMapping
public class RedirectController {
    private final UrlService service;

    public RedirectController(UrlService service) {
        this.service = service;
    }

    // Redirect from Short URL
    // GET /{short_code}
    @GetMapping({"/u/{code}", "/r/{code}", "/s/{code}"})
    public ResponseEntity<Void> redirect(@PathVariable String code) {
        String longUrl = service.resolveAny(code);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(longUrl))
                .build();
    }

}

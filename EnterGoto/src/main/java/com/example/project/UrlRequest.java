package com.example.project;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public class UrlRequest {

    // URL and alias sanity check using Bean Validation
    @NotBlank(message = "url is required")
    @URL(message = "url must be a valid URL (include http/https)")
    private String url;

    @Size(max = 64, message = "alias must be <= 64 chars")
    @Pattern(regexp = "^[a-zA-Z0-9_-]*$", message = "alias can only contain letters, numbers, _ and -")
    private String alias;

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getAlias() {
        return alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }
}

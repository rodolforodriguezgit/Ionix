package com.ionix.application.service.impl;

import com.ionix.application.service.ExternalApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExternalApiServiceImpl implements ExternalApiService {

    private final RestTemplate restTemplate;

    @Value("${app.external.api.url}")
    private String apiUrl;

    @Value("${app.external.api.key}")
    private String apiKey;

    @Override
    public List<Map<String, Object>> search(String encryptedParam) {
        log.info("Calling external API with encrypted param");
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-Key", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        String url = apiUrl + "/" + encryptedParam;
        log.debug("Calling URL: {}", url);
        
        try {
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<Map<String, Object>>>() {}
            );
            
            List<Map<String, Object>> result = response.getBody();
            log.info("External API call successful. Records received: {}", 
                    result != null ? result.size() : 0);
            
            return result != null ? result : List.of();
        } catch (Exception e) {
            log.error("Error calling external API", e);
            throw new RuntimeException("Error calling external API: " + e.getMessage(), e);
        }
    }
}

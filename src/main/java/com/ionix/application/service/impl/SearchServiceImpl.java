package com.ionix.application.service.impl;

import com.ionix.application.service.EncryptionService;
import com.ionix.application.service.ExternalApiService;
import com.ionix.application.service.SearchService;
import com.ionix.domain.dto.SearchRequest;
import com.ionix.domain.dto.SearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchServiceImpl implements SearchService {

    private final EncryptionService encryptionService;
    private final ExternalApiService externalApiService;

    @Override
    public SearchResponse search(SearchRequest request) {
        long startTime = System.currentTimeMillis();
        log.info("Processing search request with param: {}", request.getParam());
        
        try {
            // Encrypt the parameter
            String encryptedParam = encryptionService.encrypt(request.getParam());
            log.debug("Parameter encrypted successfully");
            
            // Call external API
            var results = externalApiService.search(encryptedParam);
            
            // Calculate elapsed time
            long elapsedTime = System.currentTimeMillis() - startTime;
            
            // Build response
            SearchResponse response = SearchResponse.builder()
                    .responseCode(0)
                    .description("OK")
                    .elapsedTime(elapsedTime)
                    .result(SearchResponse.Result.builder()
                            .registerCount(results.size())
                            .build())
                    .build();
            
            log.info("Search completed successfully. Elapsed time: {} ms, Records: {}", 
                    elapsedTime, results.size());
            
            return response;
        } catch (Exception e) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            log.error("Error processing search request. Elapsed time: {} ms", elapsedTime, e);
            throw new RuntimeException("Error processing search: " + e.getMessage(), e);
        }
    }
}

package com.ionix.infrastructure.controller;

import com.ionix.application.service.SearchService;
import com.ionix.domain.dto.SearchRequest;
import com.ionix.domain.dto.SearchResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Slf4j
public class SearchController {

    private final SearchService searchService;

    @PostMapping
    public ResponseEntity<SearchResponse> search(@Valid @RequestBody SearchRequest request) {
        log.info("POST /api/search - Processing search request");
        SearchResponse response = searchService.search(request);
        return ResponseEntity.ok(response);
    }
}

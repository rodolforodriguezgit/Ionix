package com.ionix.application.service;

import com.ionix.domain.dto.SearchRequest;
import com.ionix.domain.dto.SearchResponse;

public interface SearchService {
    
    SearchResponse search(SearchRequest request);
}

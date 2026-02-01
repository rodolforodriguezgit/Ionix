package com.ionix.application.service;

import java.util.List;
import java.util.Map;

public interface ExternalApiService {
    
    List<Map<String, Object>> search(String encryptedParam);
}

package com.ionix.application.service.impl;

import com.ionix.application.service.EncryptionService;
import com.ionix.application.service.ExternalApiService;
import com.ionix.domain.dto.SearchRequest;
import com.ionix.domain.dto.SearchResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchServiceImplTest {

    @Mock
    private EncryptionService encryptionService;

    @Mock
    private ExternalApiService externalApiService;

    @InjectMocks
    private SearchServiceImpl searchService;

    private SearchRequest searchRequest;

    @BeforeEach
    void setUp() {
        searchRequest = SearchRequest.builder()
                .param("1-9")
                .build();
    }

    @Test
    void testSearch_Success() {
        String encryptedParam = "encrypted123";
        Map<String, Object> record1 = new HashMap<>();
        record1.put("id", 1);
        record1.put("name", "Test 1");
        
        Map<String, Object> record2 = new HashMap<>();
        record2.put("id", 2);
        record2.put("name", "Test 2");

        List<Map<String, Object>> apiResults = Arrays.asList(record1, record2);

        when(encryptionService.encrypt("1-9")).thenReturn(encryptedParam);
        when(externalApiService.search(encryptedParam)).thenReturn(apiResults);

        SearchResponse response = searchService.search(searchRequest);

        assertNotNull(response);
        assertEquals(0, response.getResponseCode());
        assertEquals("OK", response.getDescription());
        assertNotNull(response.getElapsedTime());
        assertTrue(response.getElapsedTime() >= 0);
        assertNotNull(response.getResult());
        assertEquals(2, response.getResult().getRegisterCount());

        verify(encryptionService, times(1)).encrypt("1-9");
        verify(externalApiService, times(1)).search(encryptedParam);
    }

    @Test
    void testSearch_EmptyResults() {
        String encryptedParam = "encrypted123";
        List<Map<String, Object>> emptyResults = List.of();

        when(encryptionService.encrypt("1-9")).thenReturn(encryptedParam);
        when(externalApiService.search(encryptedParam)).thenReturn(emptyResults);

        SearchResponse response = searchService.search(searchRequest);

        assertNotNull(response);
        assertEquals(0, response.getResponseCode());
        assertEquals(0, response.getResult().getRegisterCount());
    }

    @Test
    void testSearch_ExceptionHandling() {
        when(encryptionService.encrypt("1-9")).thenThrow(new RuntimeException("Encryption error"));

        assertThrows(RuntimeException.class, () -> searchService.search(searchRequest));
    }
}

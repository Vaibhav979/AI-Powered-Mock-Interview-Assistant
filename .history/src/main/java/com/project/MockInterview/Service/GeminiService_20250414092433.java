package com.project.MockInterview.Service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private static final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=AIzaSyDyAwoGZeM0c-UkdX-_6J6ww72W200Kd1A";

    private final RestTemplate restTemplate = new RestTemplate();

    public String callGemini(String prompt) {
        // String url = String.format(GEMINI_URL, apiKey);
        Map<String, Object> request = Map.of(
                "contents", new Object[] {
                        Map.of("parts", new Object[] {
                                Map.of("text", prompt)
                        })
                });
    }

}

package com.project.MockInterview.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HumeService {
        private static final String hume_tts_url = "https://api.hume.ai/v0/tts/stream/json";
        @Value("${hume.api.key}")
        private String humeApiKey;
        private final RestTemplate restTemplate = new RestTemplate();

        public byte[] callHumeTTS(String text) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                
                headers.setBearerAuth(humeApiKey);
                // No need to format URL with API key; it’s passed in headers
                String url = hume_tts_url;

                Map<String, Object> requestBody = Map.of(
                                "utterances", List.of(
                                                Map.of(
                                                                "text", text,
                                                                "description",
                                                                "Professional interviewer voice, confident and encouraging tone",
                                                                "voice", Map.of(
                                                                                "name", "professional HR manager voice",
                                                                                "provider", "HUME_AI"))),
                                "format", Map.of("type", "mp3"));

                HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
                ResponseEntity<byte[]> response = restTemplate.postForEntity(url, entity, byte[].class);

                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                        return response.getBody();
                } else {
                        throw new RuntimeException("Hume TTS failed with status: " + response.getStatusCode());
                }
        }

}

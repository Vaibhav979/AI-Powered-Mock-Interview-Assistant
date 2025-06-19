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
        private static final String HUME_TTS_URL = "https://api.hume.ai/v0/tts/stream/json";

        @Value("${hume.api.key}")
        private String humeApiKey;

        private final RestTemplate restTemplate = new RestTemplate();

        public byte[] callHumeTTS(String text) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                

                Map<String, Object> requestBody = Map.of(
                                "utterances", List.of(
                                                Map.of(
                                                                "text", text,
                                                                "description",
                                                                "Professional interviewer voice, confident and encouraging tone")),
                                "format", Map.of("type", "mp3"),
                                "numGenerations", 1);

                HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
                ResponseEntity<byte[]> response = restTemplate.postForEntity(HUME_TTS_URL, entity, byte[].class);

                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                        return response.getBody();
                } else {
                        throw new RuntimeException("Hume TTS failed with status: " + response.getStatusCode());
                }
        }
}
// This service class interacts with the Hume TTS API to convert text to speech.
// It constructs the request with the necessary headers and body, sends it, and returns the audio data as a byte array.
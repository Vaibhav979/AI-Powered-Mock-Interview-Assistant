package com.project.MockInterview.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

public class HumeService {
    private static final String hume_tts_url = "https://api.hume.ai/v0/tts/stream/json";
    @Value("${hume.api.key}")
    private String humeApiKey;

    public byte[] callHumeTTS(String text){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(humeApiKey);

        Map<String, Object> requestBody = Map.of(
        "utterances", List.of(
            Map.of(
                "text", text,
                "description", "Professional interviewer voice, confident and encouraging tone",
                "voice", Map.of(
                    "name", voiceName,
                    "provider", "HUME_AI"
                )
            )
        ),
        "format", Map.of(
            "type", "mp3"
        )
    );
    }
}

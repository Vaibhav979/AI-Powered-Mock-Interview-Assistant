package com.project.MockInterview.Service;

import org.springframework.beans.factory.annotation.Value;

public class HumeService {
    private static final String hume_tts_url = "wss://api.hume.ai/v0/tts/stream/json";
    @Value("${hume.api.key}")
    private String humeApiKey;

    public byte[] callHumeTTS(String text){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(humeApiKey);


    }
}

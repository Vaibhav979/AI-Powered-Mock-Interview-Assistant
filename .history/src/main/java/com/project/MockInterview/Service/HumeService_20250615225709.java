package com.project.MockInterview.Service;

import org.springframework.beans.factory.annotation.Value;

public class HumeService {
    private static final String hume_tts_url = "wss://api.hume.ai/v0/evi/chat";
    @Value("${hume.api.key}")
    private String humeApiKey;

    public byte[] callHumeTTS(String text){
        HttpHe
    }
}

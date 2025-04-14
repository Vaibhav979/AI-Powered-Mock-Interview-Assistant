package com.project.MockInterview.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private static final String GEMINI_URL = https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key="";

    public String callGemini(String prompt) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'callGemini'");
    }

}

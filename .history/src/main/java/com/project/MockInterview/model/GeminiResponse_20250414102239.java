package com.project.MockInterview.model;

import java.util.List;

public class GeminiResponse {
    public List<Candidate> candidates;

    public static class Candidate {
        public Content content;
    }

    public static class Content {
        public List<Part> parts;
    }

    public static class Part {
        public String text;
    }

    public String getFirstText() {
        if (candidates != null && !candidates.isEmpty()) {
            List<Part> parts = candidates.get(0).content.parts;
            return (parts != null && !parts.isEmpty()) ? parts.get(0).text : "No response from Gemini.";
        }
        return "No response from Gemini.";
    }
}

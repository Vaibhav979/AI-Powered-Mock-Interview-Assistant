package com.project.MockInterview.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.MockInterview.Service.GeminiService;
import com.project.MockInterview.model.FeedbackRequest;
import com.project.MockInterview.model.QuestionRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@CrossOrigin
public class GeminiController {

    @Autowired
    private GeminiService geminiService;

    @PostMapping("/generate-questions")
    public ResponseEntity<Map<String, List<String>>> generateQuestion(@RequestBody QuestionRequest req) {
        String prompt = String.format(
                "Act as a technical interviewer. Generate 3 mock interview questions for a candidate applying for the role of '%s' with skills: %s. Be crisp.",
                req.role, req.skills);

        String geminiResponse = geminiService.callGemini(prompt);
        List<String> extractedQuestions = extractQuestions(geminiResponse);

        Map<String, List<String>> response = new HashMap<>();
        response.put("questions", extractedQuestions);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/feedback")
    public String giveFeedback(@RequestBody FeedbackRequest req) {
        String prompt = String.format(
                "You're a mock interview evaluator. Analyze the following:\nQuestion: %s\nCandidate's Answer: %s\nGive constructive, concise feedback highlighting strengths, issues, and suggestions.",
                req.question, req.userAnswer);
        return geminiService.callGemini(prompt);
    }

    private List<String> extractQuestions(String response) {
        List<String> questions = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d+\\.\\s+\\*\\*\\\"(.*?)\\\"\\*\\*");
        Matcher matcher = pattern.matcher(response);

        while (matcher.find()) {
            questions.add(matcher.group(1));
        }

        return questions;
    }
}

package com.project.MockInterview.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.MockInterview.Service.GeminiService;
import com.project.MockInterview.Service.HumeService;
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

    @Autowired
    private HumeService humeService;

    @PostMapping("/generate-questions")
    public ResponseEntity<Map<String, List<String>>> generateQuestion(@RequestBody QuestionRequest req) {
        String prompt = String.format(
                "Act as a technical interviewer. Generate 3 entry-level mock interview questions for a candidate applying for the role of '%s' with skills: %s. Be crisp.",
                req.role, req.skills);

        String geminiResponse = geminiService.callGemini(prompt);
        List<String> extractedQuestions = extractQuestions(geminiResponse);

        Map<String, List<String>> response = new HashMap<>();
        response.put("questions", extractedQuestions);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/feedback")
    public String giveFeedback(@RequestBody FeedbackRequest req) {

        String promptTemplate = "You're a mock interview evaluator. Analyze the following:\n\n" +
                "Question: %s\n" +
                "Answer: %s\n\n" +
                "Give a clear and concise evaluation of the answer in plain English in 500 characters. Talk directly to the user using 'you'. No headings, no formatting, no markdown, no quotes. Just write as if you're giving honest helpful feedback.";

        String finalPrompt = String.format(promptTemplate, req.question, req.userAnswer);

        String rawResponse = geminiService.callGemini(finalPrompt);
        String cleanedResponse = rawResponse
                .replaceAll("\\*\\*", "") // remove all asterisks
                .replaceAll("\"", "") // remove double quotes
                .replaceAll("’", "'") // normalize apostrophes
                .replaceAll("\\s*\\n\\s*", " ") // collapse newlines into spaces
                .replaceAll("\\s{2,}", " ") // remove extra spaces
                .trim();
        return cleanedResponse;
    }

    @PostMapping("/speak")
    public ResponseEntity<byte[]> speak(@RequestBody Map<String, String> request) {
        String text = request.get("text");
        byte[] audioBytes = humeService.callHumeTTS(text);
        HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.valueOf("audio/mpeg"));
    headers.setContentDispositionFormData("inline", "speech.mp3");

    return new ResponseEntity<>(mp3Bytes, headers, HttpStatus.OK);

    }

    public List<String> extractBulletPoints(String text, String sectionTitle) {
        List<String> results = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\*\\*" + sectionTitle + ":\\*\\*\\s*(.*?)\\n\\n", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String sectionText = matcher.group(1).trim();
            Matcher bulletMatcher = Pattern.compile("\\*\\s+(.*?)\\n").matcher(sectionText + "\n");
            while (bulletMatcher.find()) {
                results.add(bulletMatcher.group(1).trim());
            }
        }
        return results;
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

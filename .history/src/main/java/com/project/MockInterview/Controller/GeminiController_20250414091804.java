package com.project.MockInterview.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class GeminiController {

    @PostMapping("/generate-questions")
    public String generateQuestion(@RequestBody QuestionRequest req) {
        // logic to generate question
        String prompt = String.format(
                "Act as a technical interviewer. Generate 3 mock interview questions for a candidate applying for the role of '%s' with skills: %s. Be crisp.",
                req.role, req.skills);

                return geminiService.callGemini(prompt)
    }
}

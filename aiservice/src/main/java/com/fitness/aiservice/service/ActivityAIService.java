package com.fitness.aiservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.Recommendation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAIService {

    private final GeminiService geminiService;

    public Recommendation generateRecommendation(Activity activity) {
        String prompt = createPromptForActivity(activity);
        String aiResponse = geminiService.getAnswer(prompt);
        log.info("RESPONSE FROM AI: {}", aiResponse);
        return processAiResponse(activity, aiResponse);
    }

    private Recommendation processAiResponse(Activity activity, String aiResponse){
        try {
            ObjectMapper mapper = new ObjectMapper();

            JsonNode rootNode = mapper.readTree(aiResponse);

            // Extract “text” that contains the JSON
            JsonNode textNode = rootNode.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text");

            String rawText = textNode.asText();

            // Remove ```json ... ``` wrapper if present
            String cleaned = rawText
                    .replace("```json", "")
                    .replace("```", "")
                    .trim();

            JsonNode json = mapper.readTree(cleaned);

            // 🧩 Extract analysis sections
            JsonNode analysisNode = json.path("analysis");

            StringBuilder fullAnalysis = new StringBuilder();
            addAnalysisSection(fullAnalysis, analysisNode, "overall", "Overall:");
            addAnalysisSection(fullAnalysis, analysisNode, "pace", "Pace:");
            addAnalysisSection(fullAnalysis, analysisNode, "heartRate", "Heart Rate:");
            addAnalysisSection(fullAnalysis, analysisNode, "caloriesBurned", "Calories Burned:");

            // 🧩 Extract improvements
            List<String> improvements = extractImprovements(json.path("improvements"));

            // 🧩 Extract suggestions
            List<String> suggestions = extractSuggestions(json.path("suggestions"));

            // 🧩 Extract safety
            List<String> safety = extractSafetyGuideLines(json.path("safety"));

            // Build Recommendation object
            return Recommendation.builder()
                    .activityId(activity.getId())
                    .userId(activity.getUserId())
                    .activityType(activity.getType())
                    .recommendation(fullAnalysis.toString().trim())
                    .improvements(improvements)
                    .suggestions(suggestions)
                    .safety(safety)
                    .createdAt(LocalDateTime.now())
                    .build();

        } catch (Exception e) {
            log.error("Failed to parse AI response", e);
            return createDefaultRecommendation(activity);
        }
    }

    private Recommendation createDefaultRecommendation(Activity activity) {
        // default Build Recommendation object
        return Recommendation.builder()
                .activityId(activity.getId())
                .userId(activity.getUserId())
                .activityType(activity.getType())
                .recommendation("Unable to generate detailed analysis")
                .improvements(Collections.singletonList("Continue with your current routine"))
                .suggestions(Collections.singletonList("Consider consulting a fitness professional"))
                .safety(Arrays.asList(
                        "Always warm up before exercise",
                        "Stay hydrated",
                        "Listen to your body"
                ))
                .createdAt(LocalDateTime.now())
                .build();
    }

    private List<String> extractImprovements(JsonNode improvementsNode){
        List<String> improvements = new ArrayList<>();

        if (improvementsNode.isArray()) {
            improvementsNode.forEach(node -> {
                String area = node.path("area").asText();
                String detail = node.path("recommendation").asText();  // FIXED key
                improvements.add(area + ": " + detail);
            });
        }

        return improvements.isEmpty() ?
                Collections.singletonList("No improvements provided") :
                improvements;
    }

    private List<String> extractSuggestions(JsonNode suggestionsNode){
        List<String> suggestions = new ArrayList<>();

        if (suggestionsNode.isArray()) {
            suggestionsNode.forEach(node -> {
                String workout = node.path("workout").asText();
                String detail = node.path("description").asText();
                suggestions.add(workout + ": " + detail);
            });
        }

        return suggestions.isEmpty() ?
                Collections.singletonList("No suggestions provided") :
                suggestions;
    }

    private List<String> extractSafetyGuideLines(JsonNode safetyNode){
        List<String> safety = new ArrayList<>();

        if (safetyNode.isArray()) {
            safetyNode.forEach(item -> safety.add(item.asText()));
        }

        return safety.isEmpty() ?
                Collections.singletonList("Follow general safety guidelines.") :
                safety;
    }

    private void addAnalysisSection(StringBuilder sb, JsonNode node, String key, String title){
        if (!node.path(key).isMissingNode()) {
            sb.append(title)
                    .append(" ")
                    .append(node.path(key).asText())
                    .append("\n\n");
        }
    }

    private String createPromptForActivity(Activity activity) {
        return String.format(""" 
                  Analyze this fitness activity and provide detailed recommendations in the following JSON format:
                  {
                      "analysis": {
                          "overall": "...",
                          "pace": "...",
                          "heartRate": "...",
                          "caloriesBurned": "..."
                      },
                      "improvements": [
                          {
                              "area": "...",
                              "recommendation": "..."
                          }
                      ],
                      "suggestions": [
                          {
                              "workout": "...",
                              "description": "..."
                          }
                      ],
                      "safety": [
                          "point 1",
                          "point 2"
                      ]
                  }
                
                  Activity Type: %s
                  Duration: %d minutes
                  Calories Burned: %d
                  Additional Metrics: %s
                
                  Make sure the response is VALID JSON inside ```json ... ``` fencing.
                """,
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getAdditionalMetrics()
        );
    }
}
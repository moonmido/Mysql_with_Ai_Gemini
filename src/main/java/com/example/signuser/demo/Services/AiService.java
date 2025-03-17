package com.example.signuser.demo.Services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final WebClient webClient = WebClient.create("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent");

    public String generateSQL(String userQuery) {
        String prompt = "Generate a valid MySQL query ONLY for a table named 'regesters'. "
                      + "Do not include markdown formatting, explanations, or extra text. "+
                      "the table includs in the collumns only email and id and full_name and password "     
                      + "The table 'regesters' contains user information.";
    
        String requestBody = "{ \"contents\": [{ \"parts\": [{ \"text\": \"" + prompt + userQuery + "\" }] }] }";
    
        String response = webClient.post()
                .uri("?key=" + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    
        return extractSQLQuery(response);  // Extract only the SQL query from the response
    }
    

    private String extractSQLQuery(String jsonResponse) {
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray candidates = jsonObject.getJSONArray("candidates");
            JSONObject content = candidates.getJSONObject(0).getJSONObject("content");
            JSONArray parts = content.getJSONArray("parts");
            String text = parts.getJSONObject(0).getString("text");

            // Remove markdown code blocks and explanations
            text = text.replaceAll("```sql", "").replaceAll("```", "").trim();

            return text;
        } catch (Exception e) {
            return "ERROR: Could not extract SQL query.";
        }
    }
}

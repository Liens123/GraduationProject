package org.example.graduation_project.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode; // Use Jackson
import com.fasterxml.jackson.databind.ObjectMapper; // Use Jackson
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.List; // If passing messages list

@Slf4j
@Component
public class KimiClient {

    @Value("${kimi.api.url}")
    private String kimiApiUrl;

    @Value("${kimi.api.key}")
    private String kimiApiKey;

    @Value("${kimi.api.model}")
    private String defaultModel;

    @Value("${kimi.api.temperature}")
    private double defaultTemperature;

    @Value("${kimi.api.max-tokens}")
    private int defaultMaxTokens;

    @Resource
    private final HttpUtil httpUtil;
    @Resource
    private final ObjectMapper objectMapper; // Use Jackson ObjectMapper

    // Constructor Injection
    public KimiClient(HttpUtil httpUtil, ObjectMapper objectMapper) {
        this.httpUtil = httpUtil;
        this.objectMapper = objectMapper;
    }

    /**
     * Calls Kimi Chat Completions API.
     *
     * @param systemPrompt The system prompt content.
     * @param userContent The user prompt content.
     * @return The content string from Kimi's response, or null/error string if failed.
     * @throws IOException If network error occurs or response is unsuccessful.
     * @throws JsonProcessingException If response parsing fails.
     */
    public String callKimiChatCompletion(String systemPrompt, String userContent) throws IOException, JsonProcessingException {
        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("model", this.defaultModel);
        requestBody.put("temperature", this.defaultTemperature);
        requestBody.put("max_tokens", this.defaultMaxTokens);
        // Add response_format if you want to strictly enforce JSON (check if Kimi supports it)
        // requestBody.putObject("response_format").put("type", "json_object");

        ArrayNode messages = objectMapper.createArrayNode();
        messages.addObject().put("role", "system").put("content", systemPrompt);
        messages.addObject().put("role", "user").put("content", userContent);
        requestBody.set("messages", messages);

        String requestJson = objectMapper.writeValueAsString(requestBody);
        log.debug("Sending request to Kimi: {}", requestJson); // Log request for debugging

        String result = httpUtil.postJson(this.kimiApiUrl, requestJson, this.kimiApiKey);
        log.debug("Received raw response from Kimi: {}", result);

        // Attempt to parse as JSON
        try {
            JsonNode responseNode = objectMapper.readTree(result);
            JsonNode choices = responseNode.path("choices");
            if (!choices.isMissingNode() && choices.isArray() && !choices.isEmpty()) {
                JsonNode firstChoice = choices.get(0);
                JsonNode message = firstChoice.path("message");
                JsonNode content = message.path("content");
                if (!content.isMissingNode() && content.isTextual()) {
                    return content.asText();
                } else {
                    log.warn("Kimi response JSON structure invalid or missing content field: {}", result);
                }
            } else {
                log.warn("Kimi response JSON missing 'choices' array or it's empty: {}", result);
            }
            // Check for error field in response
            JsonNode error = responseNode.path("error");
            if (!error.isMissingNode()) {
                log.error("Kimi API returned an error: {}", error.toString());
                throw new IOException("Kimi API error: " + error.path("message").asText("Unknown error"));
            }

        } catch (JsonProcessingException e) {
            log.warn("Failed to parse Kimi response as JSON, returning raw response. Error: {}, Response: {}", e.getMessage(), result);
            // Optionally, try regex parsing here as a fallback if needed
            // return parseMarkdownFallback(result);
            throw e; // Re-throw if JSON was expected
        }
        return null; // Or throw exception / return error string
    }
}
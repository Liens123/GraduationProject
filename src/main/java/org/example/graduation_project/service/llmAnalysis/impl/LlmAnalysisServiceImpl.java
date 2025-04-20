package org.example.graduation_project.service.llmAnalysis.impl; // 修正包名

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.graduation_project.dao.LlmAnalysis.LlmAnalysisDao;
import org.example.graduation_project.dao.conversationLog.ConversationLogDao;
import org.example.graduation_project.model.AnalysisLlmDaily;
import org.example.graduation_project.model.ConversationLog;
import org.example.graduation_project.service.llmAnalysis.LlmAnalysisService;
import org.example.graduation_project.util.KimiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.example.graduation_project.service.llmAnalysis.impl.prompt.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class LlmAnalysisServiceImpl implements LlmAnalysisService {

    private final ConversationLogDao conversationLogDao;
    private final LlmAnalysisDao llmAnalysisDao;
    private final KimiClient kimiClient;
    private final ObjectMapper objectMapper;

    // --- Status Constants ---
    private static final String STATUS_PENDING = "PENDING";
    private static final String STATUS_PROCESSING_ANALYSIS = "PROCESSING_ANALYSIS";
    private static final String STATUS_PROCESSING_SCORING = "PROCESSING_SCORING";
    private static final String STATUS_COMPLETED = "COMPLETED";
    private static final String STATUS_FAILED = "FAILED";

    // --- Prompts from Config ---
    //105
    @Value("${kimi.api.analysis-prompt}")
    private String analysisPromptTemplate;

    //124
    @Value("${kimi.api.scoring-prompt}")
    private String scoringPromptTemplate;

    // Define weights for scoring
    private static final BigDecimal WEIGHT_PEAK_HOURS = new BigDecimal("0.20");
    private static final BigDecimal WEIGHT_SENTIMENT = new BigDecimal("0.25");
    private static final BigDecimal WEIGHT_TOPIC_INTEREST = new BigDecimal("0.30");
    private static final BigDecimal WEIGHT_SUGGESTIONS = new BigDecimal("0.25");


    @Override
    public void performDailyAnalysisAndScoring(LocalDate date) {
        log.info("开始处理日期 {} 的 LLM 分析与评分任务", date);

        // 1. 检查是否已有已完成记录，若已完成则跳过
        AnalysisLlmDaily latestCompleted = llmAnalysisDao.findLatestCompletedOrFailedByDate(date);
        if (latestCompleted != null && STATUS_COMPLETED.equals(latestCompleted.getStatus())) {
            log.info("日期 {} 的分析任务已成功完成 (ID: {}), 跳过。", date, latestCompleted.getAnalysisId());
            return;
        }

        // 2. 处理任何“处理中”状态但无结果的旧任务
        handleStaleProcessingTasks(date);

        // 3. 插入分析任务的初始记录 (PENDING 状态)
        AnalysisLlmDaily analysis = new AnalysisLlmDaily();
        analysis.setAnalysisDate(date);
        analysis.setStatus(STATUS_PENDING);
        analysis.setCreatedAt(LocalDateTime.now());
        Long analysisId = insertInitialRecord(analysis);
        if (analysisId == null) {
            log.error("无法为日期 {} 创建初始分析记录，任务中止。", date);
            return;
        }
        analysis.setAnalysisId(analysisId);
        log.info("为日期 {} 创建了新的分析记录, ID: {}", date, analysisId);

        try {
            // 4. 更新状态为 PROCESSING_ANALYSIS
            updateStatus(analysisId, STATUS_PROCESSING_ANALYSIS, LocalDateTime.now(), null);

            // 5. 获取当天所有对话，如果空则标记任务完成
            List<ConversationLog> logs = conversationLogDao.findAllByDate(date);
            if (logs == null || logs.isEmpty()) {
                log.warn("日期 {} 没有找到对话日志，标记为完成。", date);
                // 不再设置 analysis.setSummary
                updateStatusAndEnd(analysisId, STATUS_COMPLETED, LocalDateTime.now(), null);
                // 更新记录时不再传递 summary
                updateAnalysisContent(analysisId, null, null, null, null, "当天无对话记录"); // 把信息放到某个字段，或者只日志记录
                return;
            }

            // 6. 格式化对话文本，准备调用 LLM
            String formattedLogs = formatLogsForKimi(logs);
            System.out.println(formattedLogs + "修正后数据");

            // 7. 构造分析 Prompt（但目前传了 P1/systemPrompt 与 formattedLogs）
            String analysisPrompt = analysisPromptTemplate.replace("{log_data}", formattedLogs);
            log.info("调用 Kimi 进行分析 (ID: {})...", analysisId);
            //analysisPrompt
            String analysisResponse = kimiClient.callKimiChatCompletion(P1, formattedLogs);
            log.debug("Kimi 分析原始响应 (ID: {}): {}", analysisId, analysisResponse);

            // 8. 解析分析结果
            Map<String, Object> analysisResultMap = parseJsonResponse(analysisResponse);
            String peakHoursJson = safeGetJsonString(analysisResultMap, "peak_hours_analysis");
            String sentimentJson = safeGetJsonString(analysisResultMap, "user_sentiment_analysis");
            String topicInterestJson = safeGetJsonString(analysisResultMap,"topic_interest_analysis");
            String suggestionsJson = safeGetJsonString(analysisResultMap, "ai_suggestion_analysis");

            // 9. 存储分析结果
            updateAnalysisContent(analysisId, peakHoursJson, sentimentJson, topicInterestJson, suggestionsJson, analysisResponse);

            // 10. 更新状态为 PROCESSING_SCORING
            updateStatus(analysisId, STATUS_PROCESSING_SCORING, null, null);


            // 11. 构造评分 Prompt，调用 LLM 得到评分
            String analysisReportJson = objectMapper.writeValueAsString(analysisResultMap);
            String scoringPrompt = scoringPromptTemplate.replace("{analysis_report}", analysisReportJson);
            log.info("调用 Kimi 进行评分 (ID: {})...", analysisId);
            //scoringPrompt
            String scoringResponse = kimiClient.callKimiChatCompletion(P2, R2);
            log.debug("Kimi 评分原始响应 (ID: {}): {}", analysisId, scoringResponse);

            Map<String, Object> scoringResultMap = parseJsonResponse(scoringResponse);
            BigDecimal scorePeak = safeGetBigDecimal(scoringResultMap, "score_peak_hours");
            BigDecimal scoreSentimentVal = safeGetBigDecimal(scoringResultMap, "score_sentiment");
            BigDecimal scoreTopic = safeGetBigDecimal(scoringResultMap, "score_topic_interest");
            BigDecimal scoreSuggest = safeGetBigDecimal(scoringResultMap, "score_suggestions");
            String reasoning = safeGetString(scoringResultMap, "scoring_reasoning");
            // 加权平均分
            BigDecimal scoreWeighted = calculateWeightedAverage(scorePeak, scoreSentimentVal, scoreTopic, scoreSuggest);

            // 12. 存储评分结果
            updateScoringResult(analysisId, scorePeak, scoreSentimentVal, scoreTopic, scoreSuggest, scoreWeighted, reasoning, scoringResponse);

            // 13. 一切正常，标记为 COMPLETED
            updateStatusAndEnd(analysisId, STATUS_COMPLETED, LocalDateTime.now(), null);
            log.info("日期 {} 的 LLM 分析与评分任务成功完成 (ID: {})", date, analysisId);

        } catch (Exception e) {
            // 异常处理，标记为 FAILED，并记录错误信息
            log.error("处理日期 {} 的 LLM 任务时 (ID: {}) 出错: {}", date, analysisId, e.getMessage(), e);
            String errorMessage = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
            updateStatusAndEnd(analysisId, STATUS_FAILED, LocalDateTime.now(), errorMessage.substring(0, Math.min(errorMessage.length(), 1000)));
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Long insertInitialRecord(AnalysisLlmDaily analysis) {
        try {
            llmAnalysisDao.insert(analysis);
            return analysis.getAnalysisId();
        } catch (Exception e) {
            log.error("插入初始分析记录失败: {}", e.getMessage(), e);
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateStatus(Long analysisId, String status, LocalDateTime startTime, String errorMessage) {
        try {
            AnalysisLlmDaily update = new AnalysisLlmDaily();
            update.setAnalysisId(analysisId);
            update.setStatus(status);
            if (startTime != null) update.setProcessingStartedAt(startTime);
            // Clear error message when progressing
            update.setErrorMessage(errorMessage);
            llmAnalysisDao.update(update);
        } catch (Exception e) {
            log.error("更新分析记录状态失败 (ID: {}): {}", analysisId, e.getMessage(), e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateStatusAndEnd(Long analysisId, String status, LocalDateTime endTime, String errorMessage) {
        try {
            AnalysisLlmDaily update = new AnalysisLlmDaily();
            update.setAnalysisId(analysisId);
            update.setStatus(status);
            update.setProcessingEndedAt(endTime);
            update.setErrorMessage(errorMessage);
            llmAnalysisDao.update(update);
        } catch (Exception e) {
            log.error("更新最终分析记录状态失败 (ID: {}): {}", analysisId, e.getMessage(), e);
        }
    }

    // 修改了方法签名，移除了 summary 参数
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateAnalysisContent(Long analysisId, String peakHoursJson, String sentimentJson,
                                      String topicInterestJson, String suggestionsJson, String rawResponse) {
        try {
            AnalysisLlmDaily update = new AnalysisLlmDaily();
            update.setAnalysisId(analysisId);
            // 不再设置 summary
            if (peakHoursJson != null) update.setPeakHoursAnalysis(peakHoursJson);
            if (sentimentJson != null) update.setUserSentimentAnalysis(sentimentJson);
            if (topicInterestJson != null) update.setTopicInterestAnalysis(topicInterestJson);
            if (suggestionsJson != null) update.setAiSuggestionAnalysis(suggestionsJson);
            if (rawResponse != null) update.setLlmAnalysisRawResponse(rawResponse);
            llmAnalysisDao.update(update);
        } catch (Exception e) {
            log.error("更新分析内容失败 (ID: {}): {}", analysisId, e.getMessage(), e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateScoringResult(Long analysisId, BigDecimal scorePeak, BigDecimal scoreSentiment,
                                    BigDecimal scoreTopic, BigDecimal scoreSuggest, BigDecimal scoreWeighted,
                                    String reasoning, String rawResponse) {
        try {
            AnalysisLlmDaily update = new AnalysisLlmDaily();
            update.setAnalysisId(analysisId);
            update.setScorePeakHours(scorePeak);
            update.setScoreSentiment(scoreSentiment);
            update.setScoreTopicInterest(scoreTopic);
            update.setScoreSuggestions(scoreSuggest);
            update.setScoreWeightedAverage(scoreWeighted);
            update.setScoringReasoning(reasoning);
            if (rawResponse != null) update.setLlmScoringRawResponse(rawResponse);
            llmAnalysisDao.update(update);
        } catch (Exception e) {
            log.error("更新评分结果失败 (ID: {}): {}", analysisId, e.getMessage(), e);
        }
    }

    private void handleStaleProcessingTasks(LocalDate date) {
        List<AnalysisLlmDaily> processingTasks = llmAnalysisDao.findProcessingByDate(date);
        if (processingTasks != null && !processingTasks.isEmpty()) {
            log.warn("发现日期 {} 存在 {} 个未完成的任务，将标记为失败。", date, processingTasks.size());
            for (AnalysisLlmDaily task : processingTasks) {
                updateStatusAndEnd(task.getAnalysisId(), STATUS_FAILED, LocalDateTime.now(), "任务超时或被新任务覆盖");
            }
        }
    }

    private String formatLogsForKimi(List<ConversationLog> logs) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return logs.stream()
                .map(log -> String.format("时间: %s\t角色: %s\t内容: %s\t---",
                        log.getTime() != null ? log.getTime().format(formatter) : "N/A",
                        log.getRole() != null ? log.getRole() : "未知",
                        log.getContent() != null ? log.getContent() : ""))
                .collect(Collectors.joining("\n"));
    }

    private Map<String, Object> parseJsonResponse(String jsonResponse) throws JsonProcessingException {
        if (jsonResponse == null || jsonResponse.isBlank()) {
            // 更具体的异常或返回空Map可能更好
            throw new JsonProcessingException("LLM响应为空") {};
        }
        jsonResponse = jsonResponse.trim();
        if (jsonResponse.startsWith("```json")) {
            jsonResponse = jsonResponse.substring(7).trim(); // Trim after removing prefix
        }
        if (jsonResponse.endsWith("```")) {
            jsonResponse = jsonResponse.substring(0, jsonResponse.length() - 3).trim(); // Trim after removing suffix
        }

        // Handle potential leading/trailing commas or other invalid chars if necessary before parsing
        // jsonResponse = sanitizeJsonResponse(jsonResponse); // Implement if needed

        try {
            return objectMapper.readValue(jsonResponse, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            log.error("解析LLM JSON响应失败: {}", jsonResponse, e);
            throw e;
        }
    }

    private String safeGetString(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return (value instanceof String) ? (String) value : (value != null ? value.toString() : null);
    }

    private String safeGetJsonString(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) return null;
        try {
            if (value instanceof String) return (String) value;
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            log.warn("无法将键 '{}' 的值序列化为 JSON 字符串: {}", key, value, e);
            return value.toString();
        }
    }

    private BigDecimal safeGetBigDecimal(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) return null;
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        } else if (value instanceof Number) {
            // Handle potential infinite or NaN values if necessary
            double doubleValue = ((Number) value).doubleValue();
            if (Double.isFinite(doubleValue)) {
                return BigDecimal.valueOf(doubleValue);
            } else {
                log.warn("数值 '{}' for key '{}' is not finite, returning null", doubleValue, key);
                return null;
            }
        } else if (value instanceof String) {
            try {
                return new BigDecimal(((String) value).trim()); // Trim string before parsing
            } catch (NumberFormatException e) {
                log.warn("无法将字符串 '{}' 解析为 BigDecimal for key '{}'", value, key);
                return null;
            }
        }
        log.warn("值 '{}' (类型: {}) for key '{}' 无法转换为 BigDecimal", value, value.getClass().getSimpleName(), key);
        return null;
    }

    private BigDecimal calculateWeightedAverage(BigDecimal scorePeak, BigDecimal scoreSentiment,
                                                BigDecimal scoreTopic, BigDecimal scoreSuggest) {
        BigDecimal totalScore = BigDecimal.ZERO;
        if (scorePeak != null) totalScore = totalScore.add(scorePeak.multiply(WEIGHT_PEAK_HOURS));
        if (scoreSentiment != null) totalScore = totalScore.add(scoreSentiment.multiply(WEIGHT_SENTIMENT));
        if (scoreTopic != null) totalScore = totalScore.add(scoreTopic.multiply(WEIGHT_TOPIC_INTEREST));
        if (scoreSuggest != null) totalScore = totalScore.add(scoreSuggest.multiply(WEIGHT_SUGGESTIONS));
        return totalScore.setScale(2, RoundingMode.HALF_UP);
    }
}
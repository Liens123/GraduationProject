package org.example.graduation_project.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal; // Use BigDecimal for scores
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Schema(description = "LLM每日对话详细分析及评分")
public class AnalysisLlmDaily {

    @Schema(description = "分析记录主键ID")
    private Long analysisId;

    @Schema(description = "分析对应的日期")
    private LocalDate analysisDate;

    // --- Analysis Content ---
    @Schema(description = "高峰时段分析结果 (JSON 字符串)")
    private String peakHoursAnalysis;

    @Schema(description = "用户情感分析结果 (JSON 字符串)")
    private String userSentimentAnalysis;

    @Schema(description = "用户话题兴趣分析结果")
    private String topicInterestAnalysis;

    @Schema(description = "AI回答改进建议")
    private String aiSuggestionAnalysis;

    // --- Scoring ---
    @Schema(description = "高峰时段分析评分 (0-10)")
    private BigDecimal scorePeakHours;

    @Schema(description = "情感分析评分 (0-10)")
    private BigDecimal scoreSentiment;

    @Schema(description = "话题兴趣分析评分 (0-10)")
    private BigDecimal scoreTopicInterest;

    @Schema(description = "AI建议评分 (0-10)")
    private BigDecimal scoreSuggestions;

    @Schema(description = "加权平均总分 (0-10)")
    private BigDecimal scoreWeightedAverage;

    @Schema(description = "LLM 对评分的简要说明")
    private String scoringReasoning;

    // --- LLM Raw Responses ---
    @Schema(description = "第一次LLM分析调用的原始响应体")
    private String llmAnalysisRawResponse;

    @Schema(description = "第二次LLM评分调用的原始响应体")
    private String llmScoringRawResponse;

    // --- Metadata ---
    @Schema(description = "分析任务状态")
    private String status; // e.g., PENDING, PROCESSING_ANALYSIS, PROCESSING_SCORING, COMPLETED, FAILED

    @Schema(description = "错误信息")
    private String errorMessage;

    @Schema(description = "任务开始处理时间")
    private LocalDateTime processingStartedAt;

    @Schema(description = "任务处理结束时间")
    private LocalDateTime processingEndedAt;

    @Schema(description = "记录创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "记录最后更新时间")
    private LocalDateTime updatedAt;
}
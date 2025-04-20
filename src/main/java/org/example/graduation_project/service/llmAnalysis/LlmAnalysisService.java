package org.example.graduation_project.service.llmAnalysis;

import java.time.LocalDate;

public interface LlmAnalysisService {
    /**
     * 执行指定日期的对话日志 LLM 分析和评分任务
     * @param date 要分析的日期
     */
    void performDailyAnalysisAndScoring(LocalDate date);
}

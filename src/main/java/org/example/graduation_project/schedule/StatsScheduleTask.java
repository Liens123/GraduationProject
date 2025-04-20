package org.example.graduation_project.schedule;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.graduation_project.dao.LlmAnalysis.LlmAnalysisDao;
import org.example.graduation_project.service.llmAnalysis.LlmAnalysisService;
import org.example.graduation_project.service.stats.ConversationStatsService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
public class StatsScheduleTask {

    @Resource
    private ConversationStatsService conversationStatsService;
    @Resource
    private LlmAnalysisService llmAnalysisService;

    /**
     * 每天凌晨 00:10 执行昨日数据统计
     */
    @Scheduled(cron = "0 20 11 * * ?")
    public void runDailyAndHourlyStatsTask() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        log.info("开始统计日期：{}", yesterday);

        conversationStatsService.generateAndSaveDailyStats(yesterday);
        conversationStatsService.generateAndSaveHourlyStats(yesterday);

        log.info("统计完成：{}", yesterday);
    }

    /**
     * Cron: 秒 分 时 日 月 周
     * 0 20 0 * * ? -> 每天 00:20 执行 LLM 分析 (稍后执行)
     */
    @Scheduled(cron = "0 20 0 * * ?") // Run LLM analysis later
    @Async // Run in a separate thread, especially important for long LLM calls
    public void runLlmAnalysisTask() {
        LocalDate yesterday = LocalDate.now().minusDays(2);
        LocalDate targetDate = LocalDate.of(2024, 12, 14);
        log.info("开始执行日期 {} 的 [LLM分析与评分] 定时任务...", targetDate);

        try {
            llmAnalysisService.performDailyAnalysisAndScoring(targetDate);
            // Service handles its own completion/failure logging and DB updates
        } catch (Exception e) {
            // Catch top-level errors just in case service logic fails catastrophically
            log.error("执行日期 {} 的 LLM 分析与评分任务时遇到未捕获的顶层异常: {}", yesterday, e.getMessage(), e);
        }
        log.info("日期 {} 的 [LLM分析与评分] 定时任务调用结束。", yesterday); // Note: Async task might still be running
    }
}

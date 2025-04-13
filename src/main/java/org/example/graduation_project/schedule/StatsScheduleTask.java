package org.example.graduation_project.schedule;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.graduation_project.service.stats.ConversationStatsService;
import org.example.graduation_project.util.KimiClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
public class StatsScheduleTask {

    @Resource
    private ConversationStatsService conversationStatsService;

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
}

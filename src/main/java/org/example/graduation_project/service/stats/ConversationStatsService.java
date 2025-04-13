package org.example.graduation_project.service.stats;

import org.example.graduation_project.api.inner.resp.stats.EchartsDataResp;

import java.time.LocalDate;

public interface ConversationStatsService {
    void generateAndSaveDailyStats(LocalDate date);
    void generateAndSaveHourlyStats(LocalDate date);

    EchartsDataResp getDailyStatsForEcharts(LocalDate startDate, LocalDate endDate);
    EchartsDataResp getHourlyStatsForEcharts(LocalDate date);
}

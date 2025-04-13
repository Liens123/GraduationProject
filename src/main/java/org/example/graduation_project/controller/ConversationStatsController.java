package org.example.graduation_project.controller;

import jakarta.annotation.Resource;
import org.example.graduation_project.api.Result;
import org.example.graduation_project.api.inner.ConversationStatsApi;
import org.example.graduation_project.api.inner.resp.stats.EchartsDataResp;
import org.example.graduation_project.service.stats.ConversationStatsService;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class ConversationStatsController implements ConversationStatsApi {

    @Resource
    private ConversationStatsService conversationStatsService;

    @Override
    public Result<EchartsDataResp> getDailyStats(LocalDate starDate, LocalDate endDate) {
        return Result.success(conversationStatsService.getDailyStatsForEcharts(starDate, endDate));
    }

    @Override
    public Result<EchartsDataResp> getHourlyStats(LocalDate date) {
        return Result.success(conversationStatsService.getHourlyStatsForEcharts(date));
    }
}

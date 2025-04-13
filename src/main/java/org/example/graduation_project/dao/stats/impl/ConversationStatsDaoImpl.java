package org.example.graduation_project.dao.stats.impl;

import jakarta.annotation.Resource;
import org.example.graduation_project.dao.stats.ConversationStatsDao;
import org.example.graduation_project.dto.DailyStatsDTO;
import org.example.graduation_project.dto.HourlyStatsDTO;
import org.example.graduation_project.mapper.ConversationStatsMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ConversationStatsDaoImpl implements ConversationStatsDao {

    @Resource
    private ConversationStatsMapper conversationStatsMapper;

    @Override
    public int calculateDailyCount(LocalDate date) {
        return conversationStatsMapper.calculateDailyCount(date);
    }

    @Override
    public List<HourlyStatsDTO> calculateHourlyCount(LocalDate date) {
        return conversationStatsMapper.calculateHourlyCount(date);
    }

    @Override
    public void upsertDailyStats(LocalDate date, int count) {
        conversationStatsMapper.upsertDailyStat(date, count);
    }

    @Override
    public void upsertHourlyStats(LocalDate date, List<HourlyStatsDTO> hourlyList) {
        conversationStatsMapper.batchUpsertHourlyStats(date, hourlyList);
    }

    @Override
    public List<DailyStatsDTO> findDailyStatsRange(LocalDate starDate, LocalDate endDate) {
        return conversationStatsMapper.findDailyStatsRange(starDate, endDate);
    }

    @Override
    public List<HourlyStatsDTO> findHourlyStats(LocalDate Date) {
        return conversationStatsMapper.findHourlyStats(Date);
    }
}

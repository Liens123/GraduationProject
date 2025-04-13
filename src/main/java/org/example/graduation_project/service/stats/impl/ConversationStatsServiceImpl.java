package org.example.graduation_project.service.stats.impl;

import jakarta.annotation.Resource;
import org.example.graduation_project.api.inner.resp.stats.EchartsDataResp;
import org.example.graduation_project.dao.stats.ConversationStatsDao;
import org.example.graduation_project.dto.DailyStatsDTO;
import org.example.graduation_project.dto.HourlyStatsDTO;
import org.example.graduation_project.service.stats.ConversationStatsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationStatsServiceImpl implements ConversationStatsService {

    @Resource
    private ConversationStatsDao statsDao;

    @Override
    @Transactional
    public void generateAndSaveDailyStats(LocalDate date) {
        int count = statsDao.calculateDailyCount(date);
        statsDao.upsertDailyStats(date, count);
    }

    @Override
    @Transactional
    public void generateAndSaveHourlyStats(LocalDate date) {
        List<HourlyStatsDTO> hourlyList = statsDao.calculateHourlyCount(date);
        statsDao.upsertHourlyStats(date, hourlyList);
    }

    @Override
    @Transactional
    public EchartsDataResp getDailyStatsForEcharts(LocalDate startDate, LocalDate endDate) {
        List<DailyStatsDTO> list = statsDao.findDailyStatsRange(startDate, endDate);
        List<String> categories = new ArrayList<>();
        List<Integer> values = new ArrayList<>();

        for (DailyStatsDTO dto : list) {
            categories.add(dto.getStatDate().toString()); // 转为 yyyy-MM-dd
            values.add(dto.getTotalCount());
        }

        return new EchartsDataResp(categories, values);
    }

    @Override
    @Transactional
    public EchartsDataResp getHourlyStatsForEcharts(LocalDate date) {
        List<HourlyStatsDTO> list = statsDao.findHourlyStats(date);
        List<String> categories = new ArrayList<>();
        List<Integer> values = new ArrayList<>();

        for (HourlyStatsDTO dto : list) {
            categories.add(dto.getHour().toString());
            values.add(dto.getHourlyCount());
        }
        return new EchartsDataResp(categories, values);
    }
}

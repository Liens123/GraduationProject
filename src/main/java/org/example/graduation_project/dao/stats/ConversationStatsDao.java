package org.example.graduation_project.dao.stats;

import org.example.graduation_project.dto.DailyStatsDTO;
import org.example.graduation_project.dto.HourlyStatsDTO;

import java.time.LocalDate;
import java.util.List;

public interface ConversationStatsDao {

    int calculateDailyCount(LocalDate date);

    List<HourlyStatsDTO> calculateHourlyCount(LocalDate date);

    void upsertDailyStats(LocalDate date, int count);

    void upsertHourlyStats(LocalDate date, List<HourlyStatsDTO> hourlyList);

    List<DailyStatsDTO> findDailyStatsRange(LocalDate starDate, LocalDate endDate);

    List<HourlyStatsDTO> findHourlyStats(LocalDate Date);
}

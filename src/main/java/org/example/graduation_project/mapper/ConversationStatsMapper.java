package org.example.graduation_project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.graduation_project.dto.DailyStatsDTO;
import org.example.graduation_project.dto.HourlyStatsDTO;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ConversationStatsMapper {
    int calculateDailyCount(@Param("date") LocalDate date);

    List<HourlyStatsDTO> calculateHourlyCount(@Param("date") LocalDate date);

    void upsertDailyStat(@Param("date") LocalDate date, @Param("count") int count);

    void batchUpsertHourlyStats(@Param("date") LocalDate date, @Param("list") List<HourlyStatsDTO> hourlyList);

    List<DailyStatsDTO> findDailyStatsRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    List<HourlyStatsDTO> findHourlyStats(@Param("date") LocalDate date);
}

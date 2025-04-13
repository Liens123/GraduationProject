package org.example.graduation_project.api.inner;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.graduation_project.api.Result;
import org.example.graduation_project.api.constant.InnerApiConstant;
import org.example.graduation_project.api.inner.resp.stats.EchartsDataResp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Tag(name = "数据统计接口", description = "每日统计 + 每时统计")
@RequestMapping(InnerApiConstant.INNER_STATS_API)
public interface ConversationStatsApi {

    @Operation(summary = "每日统计")
    @GetMapping("/dailyChart")
    public Result<EchartsDataResp> getDailyStats(@RequestParam("starDate") @Valid LocalDate starDate,
                                                 @RequestParam("endDate") @Valid LocalDate endDate);
    @Operation(summary = "每时统计")
    @GetMapping("/hourlyChart")
    public Result<EchartsDataResp> getHourlyStats(@RequestParam("Date") @Valid LocalDate Date);

}

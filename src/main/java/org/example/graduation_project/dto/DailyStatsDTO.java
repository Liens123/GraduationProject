package org.example.graduation_project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyStatsDTO {

    @Schema(description = "统计日期")
    private LocalDate statDate;

    @Schema(description = "统计对话次数")
    private int totalCount;
}

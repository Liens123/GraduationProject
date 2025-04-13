package org.example.graduation_project.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HourlyStatsDTO {

    @Schema(description = "小时")
    private Integer hour;
    @Schema(description = "小时对话总数")
    private Integer hourlyCount;
}

package org.example.graduation_project.model;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.Date;

public class StatConversationDaily {

    @Schema(description = "统计日期")
    private LocalDate statDate;

    @Schema(description = "每日总对话数")
    private Integer totalCount;

    @Schema(description = "最后更新时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private Date lastUpdate;

    public StatConversationDaily() {
    }

    public StatConversationDaily(Date lastUpdate, LocalDate statDate, Integer totalCount) {
        this.lastUpdate = lastUpdate;
        this.statDate = statDate;
        this.totalCount = totalCount;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public LocalDate getStatDate() {
        return statDate;
    }

    public void setStatDate(LocalDate statDate) {
        this.statDate = statDate;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}

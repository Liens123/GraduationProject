package org.example.graduation_project.model;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

public class StatConversationHourly {

    @Schema(description = "统计日期和小时")
    @DateTimeFormat("yyyy-MM-dd HH:00:00")
    private Date statDateTime;

    @Schema(description = "该小时对话次数")
    private Integer hourlyCount;

    @Schema(description = "最后更新时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private Date lastUpdate;

    public StatConversationHourly() {
    }

    public StatConversationHourly(Integer hourlyCount, Date lastUpdate, Date statDateTime) {
        this.hourlyCount = hourlyCount;
        this.lastUpdate = lastUpdate;
        this.statDateTime = statDateTime;
    }

    public Integer getHourlyCount() {
        return hourlyCount;
    }

    public void setHourlyCount(Integer hourlyCount) {
        this.hourlyCount = hourlyCount;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Date getStatDateTime() {
        return statDateTime;
    }

    public void setStatDateTime(Date statDateTime) {
        this.statDateTime = statDateTime;
    }
}

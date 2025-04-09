package org.example.graduation_project.model;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

public class ExcelData {
    @ExcelProperty("对话内容Id")
    @Schema(description = "对话内容Id")
    private Long id;

    @ExcelProperty
    @Schema(description = "对话内容时间")
    private Date time;

    @ExcelProperty
    @Schema(description = "对话者身份")
    private String role;

    @ExcelProperty
    @Schema(description = "对话验证状态")
    private String status;

    @ExcelProperty
    @Schema(description = "对话内容")
    private String content;

    @ExcelProperty
    @Schema(description = "对话音频")
    private String urlVoice;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getUrlVoice() {
        return urlVoice;
    }

    public void setUrlVoice(String urlVoice) {
        this.urlVoice = urlVoice;
    }
}

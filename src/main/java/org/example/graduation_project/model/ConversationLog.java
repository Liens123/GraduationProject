package org.example.graduation_project.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ConversationLog {

    @Schema(description = "对话内容Id")
    @ExcelProperty("对话内容Id")
    private Long id;

    @Schema(description = "对话内容时间")
    @ExcelProperty("消息时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDate time;

    @Schema(description = "对话者身份")
    @ExcelProperty("角色")
    private String role;

    @Schema(description = "对话验证状态")
    @ExcelProperty("内容审核")
    private String status;

    @Schema(description = "对话内容")
    @ExcelProperty("内容")
    private String content;

    @Schema(description = "对话音频")
    @ExcelProperty("语音")
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

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public String getUrlVoice() {
        return urlVoice;
    }

    public void setUrlVoice(String urlVoice) {
        this.urlVoice = urlVoice;
    }
}

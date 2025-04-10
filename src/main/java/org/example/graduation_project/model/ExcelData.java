package org.example.graduation_project.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class ExcelData {

    @Schema(description = "对话内容Id")
    @ExcelProperty("对话内容Id")
    private Long id;

    @Schema(description = "对话内容时间")
    @ExcelProperty("消息时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private Date time;

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
}

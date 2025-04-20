package org.example.graduation_project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "会话日志DTO")
public class ConversationLogDTO {

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "对话时间")
    private LocalDateTime time;

    @Schema(description = "角色")
    private String role;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "语音URL")
    private String urlVoice;
}
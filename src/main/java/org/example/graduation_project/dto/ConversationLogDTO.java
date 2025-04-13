package org.example.graduation_project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversationLogDTO {
    private Long id;
    private LocalDate logTime;
    private String role;
    private String content;
}

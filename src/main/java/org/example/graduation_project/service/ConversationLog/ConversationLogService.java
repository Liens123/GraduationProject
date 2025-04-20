package org.example.graduation_project.service.ConversationLog;

import org.example.graduation_project.dto.ConversationLogDTO;

import java.time.LocalDate;
import java.util.List;

public interface ConversationLogService {

    List<ConversationLogDTO> findLogs(LocalDate date, String role, Long searchId, Integer pageNum, Integer pageSize);

    int countLogs(LocalDate date, String role, Long searchId, Integer pageNum, Integer pageSize);
}

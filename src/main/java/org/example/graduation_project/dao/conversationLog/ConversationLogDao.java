package org.example.graduation_project.dao.conversationLog;

import org.example.graduation_project.dto.ConversationLogDTO;
import org.example.graduation_project.dto.ConversationQueryDTO;
import org.example.graduation_project.model.ConversationLog;

import java.time.LocalDate;
import java.util.List;

public interface ConversationLogDao {
    List<ConversationLogDTO> findLogs(ConversationQueryDTO dto);
    int countLogs(ConversationQueryDTO dto);
    List<ConversationLog> findAllByDate(LocalDate logDate);
}

package org.example.graduation_project.dao.conversationLog.impl;

import jakarta.annotation.Resource;
import org.example.graduation_project.dao.conversationLog.ConversationLogDao;
import org.example.graduation_project.dto.ConversationLogDTO;
import org.example.graduation_project.dto.ConversationQueryDTO;
import org.example.graduation_project.mapper.ConversationLogMapper;
import org.example.graduation_project.model.ConversationLog;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ConversationLogDaoImpl implements ConversationLogDao {
    @Resource
    private ConversationLogMapper conversationLogMapper;

    @Override
    public List<ConversationLogDTO> findLogs(ConversationQueryDTO dto) {
        return conversationLogMapper.findLogs(dto);
    }

    @Override
    public int countLogs(ConversationQueryDTO dto) {
        return conversationLogMapper.countLogs(dto);
    }

    @Override
    public List<ConversationLog> findAllByDate(LocalDate logDate) {
        return conversationLogMapper.findAllByDate(logDate);
    }
}

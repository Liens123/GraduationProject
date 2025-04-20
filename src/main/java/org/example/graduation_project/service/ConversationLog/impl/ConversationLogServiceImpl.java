package org.example.graduation_project.service.ConversationLog.impl;

import jakarta.annotation.Resource;
import org.example.graduation_project.dao.conversationLog.ConversationLogDao;
import org.example.graduation_project.dto.ConversationLogDTO;
import org.example.graduation_project.dto.ConversationQueryDTO;
import org.example.graduation_project.service.ConversationLog.ConversationLogService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ConversationLogServiceImpl implements ConversationLogService {

    @Resource
    private ConversationLogDao conversationLogDao;

    @Override
    public List<ConversationLogDTO> findLogs(LocalDate date, String role, Long searchId, Integer pageNum, Integer pageSize) {
        ConversationQueryDTO query = new ConversationQueryDTO();
        query.setDate(date);
        query.setRole(role);
        query.setSearchId(searchId);
        query.setPageNum(pageNum);
        query.setPageSize(pageSize);

        return conversationLogDao.findLogs(query);
    }
    @Override
    public int countLogs(LocalDate date, String role, Long searchId, Integer pageNum, Integer pageSize) {
        ConversationQueryDTO query = new ConversationQueryDTO();
        query.setDate(date);
        query.setRole(role);
        query.setSearchId(searchId);
        query.setPageNum(pageNum);
        query.setPageSize(pageSize);

        return conversationLogDao.countLogs(query);
    }

}

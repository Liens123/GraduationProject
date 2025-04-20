package org.example.graduation_project.controller;

import jakarta.annotation.Resource;
import org.example.graduation_project.api.PageResult;
import org.example.graduation_project.api.Result;
import org.example.graduation_project.api.inner.ConversationLogApi;
import org.example.graduation_project.dto.ConversationLogDTO;
import org.example.graduation_project.service.ConversationLog.ConversationLogService;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ConversationLogController implements ConversationLogApi {

    @Resource
    private ConversationLogService conversationLogService;

    @Override
    public Result<PageResult<ConversationLogDTO>> getLogs(LocalDate date, String role, Long searchId, Integer pageNum, Integer pageSize) {
        List<ConversationLogDTO> list = conversationLogService.findLogs(date,role,searchId,pageNum,pageSize);
        int total = conversationLogService.countLogs(date,role,searchId,pageNum,pageSize);
        PageResult<ConversationLogDTO> pageResult = new PageResult<>(list, total, pageNum, pageSize);
        return Result.success(pageResult);
    }
}

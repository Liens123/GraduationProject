package org.example.graduation_project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.graduation_project.dto.ConversationLogDTO;
import org.example.graduation_project.dto.ConversationQueryDTO;
import org.example.graduation_project.model.ConversationLog;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ConversationLogMapper {

    /**
     * findLogs - 动态条件 + 分页
     * @param dto 包含:
     *               date - LocalDate,
     *               role - String,
     *               searchId - Long,
     *               offset - int,
     *               pageSize - int
     */
    List<ConversationLogDTO> findLogs(ConversationQueryDTO dto);

    int countLogs(ConversationQueryDTO dto);

    /**
     * 获取指定日期的所有对话日志 (不分页)
     * 返回 ConversationLog 模型，包含 LocalDateTime
     */
    List<ConversationLog> findAllByDate(@Param("logDate") LocalDate logDate);
}

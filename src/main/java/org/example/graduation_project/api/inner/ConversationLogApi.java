package org.example.graduation_project.api.inner;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.graduation_project.api.PageResult;
import org.example.graduation_project.api.Result;
import org.example.graduation_project.api.constant.InnerApiConstant;
import org.example.graduation_project.dto.ConversationLogDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Tag(name = "对话日志查询", description = "按需查询日志 + 统计查询日志总数")
@RequestMapping(InnerApiConstant.INNER_CONVERSATION_LOG_API)
public interface ConversationLogApi {

    @Operation(summary = "分页查询对话日志")
    @GetMapping("/logs")
    Result<PageResult<ConversationLogDTO>> getLogs(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                                   @RequestParam(required = false) String role,
                                                   @RequestParam(required = false) Long searchId,
                                                   @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                   @RequestParam(required = false, defaultValue = "20") Integer pageSize);


}

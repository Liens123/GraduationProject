package org.example.graduation_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationQueryDTO {

    private LocalDate date;
    private String role;
    private Long searchId;

    private Integer pageNum;
    private Integer pageSize;

    /**
     * 用于MyBatis查询limit
     */
    @Setter
    private Integer offset;

    // 手动计算 offset
    public Integer getOffset() {
        // 如果 pageNum/pageSize为空，自己写默认或做防御
        int pNum = (pageNum == null || pageNum <= 0) ? 1 : pageNum;
        int pSize = (pageSize == null || pageSize <= 0) ? 20 : pageSize;
        return (pNum - 1) * pSize;
    }

}

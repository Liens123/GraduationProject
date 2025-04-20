package org.example.graduation_project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.graduation_project.model.AnalysisLlmDaily;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface LlmAnalysisMapper {

    /** 插入新记录，并返回生成的 ID 到对象中 */
    int insert(AnalysisLlmDaily analysis);

    /** 根据 ID 更新记录 */
    int update(AnalysisLlmDaily analysis);

    /** 根据 ID 查找记录 */
    AnalysisLlmDaily findById(@Param("analysisId") Long analysisId);

    /** 查找指定日期最新的已完成或失败的记录 */
    AnalysisLlmDaily findLatestCompletedOrFailedByDate(@Param("analysisDate") LocalDate analysisDate);

    /** 查找指定日期正在处理中的记录 */
    List<AnalysisLlmDaily> findProcessingByDate(@Param("analysisDate") LocalDate analysisDate);
}

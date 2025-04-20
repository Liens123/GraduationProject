package org.example.graduation_project.dao.LlmAnalysis;

import org.example.graduation_project.model.AnalysisLlmDaily;

import java.time.LocalDate;
import java.util.List;

public interface LlmAnalysisDao {

    void insert(AnalysisLlmDaily analysis);

    void update(AnalysisLlmDaily analysis);

    AnalysisLlmDaily findById(Long analysisId);

    AnalysisLlmDaily findLatestCompletedOrFailedByDate(LocalDate analysisDate);

    List<AnalysisLlmDaily> findProcessingByDate(LocalDate analysisDate);

}

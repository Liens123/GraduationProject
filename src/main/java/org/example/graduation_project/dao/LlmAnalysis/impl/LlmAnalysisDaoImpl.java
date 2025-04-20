package org.example.graduation_project.dao.LlmAnalysis.impl;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.example.graduation_project.dao.LlmAnalysis.LlmAnalysisDao;
import org.example.graduation_project.mapper.LlmAnalysisMapper;
import org.example.graduation_project.model.AnalysisLlmDaily;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LlmAnalysisDaoImpl implements LlmAnalysisDao {

    @Resource
    private LlmAnalysisMapper llmAnalysisMapper;

    @Override
    public AnalysisLlmDaily findById(Long analysisId) {
        return llmAnalysisMapper.findById(analysisId);
    }

    @Override
    public void insert(AnalysisLlmDaily analysis) {
        llmAnalysisMapper.insert(analysis);
    }

    @Override
    public void update(AnalysisLlmDaily analysis) {
        llmAnalysisMapper.update(analysis);
    }

    @Override
    public AnalysisLlmDaily findLatestCompletedOrFailedByDate(LocalDate analysisDate) {
        return llmAnalysisMapper.findLatestCompletedOrFailedByDate(analysisDate);
    }

    @Override
    public List<AnalysisLlmDaily> findProcessingByDate(LocalDate analysisDate) {
        return llmAnalysisMapper.findProcessingByDate(analysisDate);
    }
}

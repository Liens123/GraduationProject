package org.example.graduation_project.dao.excelupload.impl;

import jakarta.annotation.Resource;
import org.example.graduation_project.dao.excelupload.ExcelDataDao;
import org.example.graduation_project.mapper.ExcelDataMapper;
import org.example.graduation_project.model.ConversationLog;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExcelDataDaoImpl implements ExcelDataDao {
    @Resource
    private ExcelDataMapper excelDataMapper;

    @Override
    public void save(List<ConversationLog> dataList) {
        if(dataList != null && !dataList.isEmpty()){
            excelDataMapper.batchInsert(dataList);
        }
    }
}

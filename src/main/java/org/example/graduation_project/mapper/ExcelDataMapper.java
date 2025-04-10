package org.example.graduation_project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.graduation_project.model.ExcelData;

import java.util.List;

@Mapper
public interface ExcelDataMapper {

    /**
     * @param dataList
     */
    void batchInsert(List<ExcelData> dataList);
}

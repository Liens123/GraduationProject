package org.example.graduation_project.dao.excelupload;

import org.example.graduation_project.model.ConversationLog;

import java.util.List;

public interface ExcelDataDao {

    /**
     *
     * @param dataList
     */
    void save(List<ConversationLog> dataList);
}

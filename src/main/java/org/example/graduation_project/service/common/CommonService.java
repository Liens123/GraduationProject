package org.example.graduation_project.service.common;

import org.example.graduation_project.model.ExcelData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CommonService {

    /**
     * @param file
     * @param userId
     * @return 导入的数据行数
     */
    int importExcelData(MultipartFile file, Long userId);
}

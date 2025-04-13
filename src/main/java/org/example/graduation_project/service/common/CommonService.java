package org.example.graduation_project.service.common;

import org.springframework.web.multipart.MultipartFile;

public interface CommonService {

    /**
     * @param file
     * @param userId
     * @return 导入的数据行数
     */
    int importExcelData(MultipartFile file, Long userId);
}

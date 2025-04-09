package org.example.graduation_project.service.common.impl;

import com.alibaba.excel.EasyExcel;
import jakarta.annotation.Resource;
import org.apache.poi.ss.usermodel.*;
import org.example.graduation_project.dao.excelupload.ExcelDataDao;
import org.example.graduation_project.exception.BizException;
import org.example.graduation_project.model.ExcelData;
import org.example.graduation_project.service.common.CommonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.example.graduation_project.service.constant.MAX_FILE_SIZE;

@Service
public class CommonServiceImpl implements CommonService {
    @Resource
    private ExcelDataDao excelDataDao;

    List<ExcelData> dataList = new ArrayList<>();

    @Override
    @Transactional
    public int importExcelData(MultipartFile file, Long userId) {
        if (file == null || file.isEmpty()) {
            throw new BizException("上传文件不能为空");
        }

        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null) {
            originalFilename = "";
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new BizException("文件大小超过20MB");
        }

        if (!originalFilename.endsWith(".xls") || originalFilename.endsWith(".xlsx")) {
            throw new BizException("文件格式不支持，请上传Excel文件（.xls或.xlsx）");
        }

        try (InputStream is = file.getInputStream()) {
            dataList = EasyExcel.read(is)
                    .head(ExcelData.class)
                    .sheet()
                    .doReadSync();
        } catch (Exception e) {
            throw new BizException(originalFilename + "Excel解析失败，" + e.getMessage());
        }

        if (dataList != null && !dataList.isEmpty()) {
            excelDataDao.save(dataList);
        }
        if (dataList != null) {
            return dataList.size();
        }
        else
            return -1;
    }
}

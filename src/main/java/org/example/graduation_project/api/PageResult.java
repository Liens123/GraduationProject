package org.example.graduation_project.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.graduation_project.api.constant.PageConstant;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "分页返回")
public class PageResult<T> {

    @Schema(description = "当前页记录")
    private List<T> records;

    @Schema(description = "总记录数")
    private int total;

    @Schema(description = "页数")
    private Integer pageNum;

    @Schema(description = "条数")
    private Integer pageSize;


    /**
     *
     * @return
     */
    public Integer getPageNum() {
        if(pageNum == null || pageNum <= 0) {
            pageNum = PageConstant.DEFAULT_PAGE_NUM;
        }
        return pageNum;
    }

    /**
     * s
     * @return
     */
    public Integer getPageSize() {
        if(pageSize == null) {
            pageSize = PageConstant.DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }
}

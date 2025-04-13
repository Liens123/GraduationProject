package org.example.graduation_project.api.inner.resp.stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EchartsDataResp {
    private List<String> categories; // x 轴
    private List<Integer> values;    // y 轴
}

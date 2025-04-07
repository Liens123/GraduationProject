package org.example.graduation_project.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "通用返回结构")
public class Result<T> implements Serializable {

    public enum ResultCodeEnum {
        A00000("请求成功"),
        A00001("请求参数无效"),
        A00002("用户未登录"),
        A00003("内部服务异常"),
        A00004("请求方法无效"),
        A00005("非正常请求"),
        A00006("请求限流");

        ResultCodeEnum(String value) { this.value = value;}
        private final String value;

        public String getValue() { return value; }
    }
    @Schema(description = "标识码")
    private String resultCode = ResultCodeEnum.A00000.name();
    @Schema(description = "提示信息")
    private String resultMsg;
    @Schema(description = "请求唯一标识")
    private String requestId;
    @Schema(description = "响应时间")
    private Long spendTime;
    @Schema(description = "响应内容")
    private T data;

    /**
     *
     * @return
     * @param <T>
     */
    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.setResultCode(ResultCodeEnum.A00000.name());
        result.setResultMsg(ResultCodeEnum.A00000.getValue());
        return result;
    }

    /**
     *
     * @param t
     * @return
     * @param <T>
     */
    public static <T> Result<T> success(T t) {
        Result<T> result = success();
        result.setData(t);
        return result;
    }

    /**
     * @param <T>
     * @return
     */
    public static <T> Result<T> error() {
        return error(ResultCodeEnum.A00003);
    }

    /**
     * @param resultCodeEnum
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(ResultCodeEnum resultCodeEnum) {
        Result<T> result = new Result<>();
        result.setResultCode(resultCodeEnum.name());
        result.setResultMsg(resultCodeEnum.getValue());
        return result;
    }

    /**
     * @param resultCode
     * @param resultMsg
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(String resultCode, String resultMsg) {
        Result<T> result = new Result<>();
        result.setResultCode(resultCode);
        result.setResultMsg(resultMsg);
        return result;
    }

}

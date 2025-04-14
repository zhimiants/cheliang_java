package com.atguigu.spzx.model.vo.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "响应结果实体类")
public class Result<T> {

    //返回码
    @Schema(description = "业务状态码")
    private Integer code;

    //返回消息
    @Schema(description = "响应消息")
    private String message;

    //返回数据
    @Schema(description = "业务数据")
    private T data;

    // 私有化构造
    private Result() {}

    // 返回数据
    public static <T> Result<T> build(T data, Integer code, String message) {
        Result<T> result = new Result<>();
        result.setData(data);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    // 通过枚举构造Result对象
    public static <T> Result build(T data , ResultCodeEnum resultCodeEnum) {
        return build(data , resultCodeEnum.getCode() , resultCodeEnum.getMessage()) ;
    }

    //ok方法
    public static <T> Result ok(T data){
        return build(data,ResultCodeEnum.SUCCESS);
    }

}

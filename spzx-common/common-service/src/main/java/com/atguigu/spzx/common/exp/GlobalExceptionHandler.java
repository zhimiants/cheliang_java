package com.atguigu.spzx.common.exp;


import com.atguigu.spzx.model.vo.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理类
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    //处理大的异常
    @ExceptionHandler(Exception.class)
    public Result handlerException(Exception e){
        e.printStackTrace();
        return Result.build(null,201,e.getMessage());
    }
    //处理算数异常
    @ExceptionHandler(ArithmeticException.class)
    public Result handlerArithmeticException(ArithmeticException e){
        e.printStackTrace();
        return Result.build(null,201, e.getMessage());
    }
    //处理自定义异常
    @ExceptionHandler(GuiguException.class)
    public Result handlerGuiguException(GuiguException e){
        e.printStackTrace();
        return Result.build(null,e.getCode(),e.getMessage());

    }
}
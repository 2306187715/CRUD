package com.example.config;

import com.example.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerException {
    /**
     * 全局异常处理
     */
    @ExceptionHandler
    public Result ControllerHandler(Exception e) {
        String message = e.getMessage();
        e.printStackTrace();
        if (message.contains("not a valid `java.lang.Integer` value"))
            return Result.error("输入格式有误，只能输入数字类型");
        if (message.contains("for key")) return Result.error("存在重复数据");
        if (message.contains("SQL")) return Result.error();
        if (message.contains("selectSemesterIdById")) return Result.error("课程id不能为空");
        return Result.error(message);
    }
}

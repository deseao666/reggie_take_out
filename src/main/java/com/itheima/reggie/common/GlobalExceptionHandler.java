package com.itheima.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @projectName: reggie_take_out
 * @package: com.itheima.reggie.common
 * @className: GlobalExceptionHandler
 * @author: Eric
 * @description: 全局异常处理，就是用AOP的代理对异常进行统一的处理
 * @date: 2023/4/29 3:30
 * @version: 1.0
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})//对标记了@RestController和@Controller注解的类进行异常的全局处理
@ResponseBody//将结果封装为JSON数据到响应体
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 异常处理方法
     * @param ex
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public  R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());

        if (ex.getMessage().contains("Duplicate entry")){//判断是否有名称重复的异常消息
            String[] split = ex.getMessage().split(" ");//按照空格拆分并返回一个String【】
            String msg = split[2]+"已存在";//消息第二个是名字
            return R.error(msg);
        }
        return R.error("未知错误");
    }
}

package com.itheima.reggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @projectName: reggie_take_out
 * @package: com.itheima.reggie
 * @className: ReggieApplication
 * @author: Eric
 * @description: SpringBoot的启动类
 * @date: 2023/4/28 0:29
 * @version: 1.0
 */
@Slf4j//lombok提供的注解，可以通过log变量调用方法来输出日志
@SpringBootApplication //SpringBoot启动类注解
@ServletComponentScan//扫描过滤器注解
public class ReggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class,args);
        log.info("项目启动成功");
    }
}

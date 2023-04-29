package com.itheima.reggie.config;

import com.itheima.reggie.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @projectName: reggie_take_out
 * @package: com.itheima.reggie.config
 * @className: WebMvcConfig
 * @author: Eric
 * @description: config包下都是静态资源的设置类
 * @date: 2023/4/28 0:52
 * @version: 1.0
 */
@Slf4j
@Configuration//配置类注释
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /**
     * 设置静态资源的映射，因为静态资源放在resources目录下默认访问不到
     * 所以重写这个方法来映射访问的静态资源对应的路径
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始进行静态资源映射");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
        super.addResourceHandlers(registry);
    }

    /**
     * 扩展MVC消息转换器
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器。。。");
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper( new JacksonObjectMapper());
        converters.add(0,messageConverter);

        super.extendMessageConverters(converters);
    }
}

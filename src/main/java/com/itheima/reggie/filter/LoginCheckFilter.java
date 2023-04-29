package com.itheima.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @projectName: reggie_take_out
 * @package: com.itheima.reggie.filter
 * @className: LoginCheckFilter
 * @author: Eric
 * @description: 检查用户是否登录
 * @date: 2023/4/28 9:26
 * @version: 1.0
 */
@Slf4j
@WebFilter(filterName = "LoginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    //路径比较器，支持通配符
    private  static  final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //获取Url请求
        String requestURL = request.getRequestURI();
        log.info("拦截到请求：{}",request.getRequestURI());
        //不需要拦截的请求
        String[] urls = new  String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "front/**"
        };
        //判断本次请求是否拦截
        boolean check = check(urls, requestURL);
        //如果登录，即在不需要拦截请求数组中，直接放行
        if (check){
            log.info("本次{}请求不需要处理",request.getRequestURI());
            filterChain.doFilter(request,response);
            return;
        }
        //判断状态，如果已登陆，放行
        if (request.getSession().getAttribute("employee") != null){
            log.info("用户已登录，用户ID为：{}",request.getSession().getAttribute("employee"));
            filterChain.doFilter(request,response);
            return;
        }
        log.info("用户未登录");
        //如果未登录，则将数据写回前端，让前端页面判断跳转
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;



    }

    /**
     * 路径匹配，检查本次请求是否放行
     * @param urls
     * @param requestURL
     * @return
     */

    public  boolean check(String[] urls,String requestURL){
        for (String url: urls) {
            boolean match = PATH_MATCHER.match(url, requestURL);
            if (match) {
                return true;
            }
        }
        return false;
    }
}

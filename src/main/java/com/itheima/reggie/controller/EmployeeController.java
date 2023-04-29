package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.time.LocalDateTime;

/**
 * @projectName: reggie_take_out
 * @package: com.itheima.reggie.controller
 * @className: EmployeeController
 * @author: Eric
 * @description: TODO
 * @date: 2023/4/28 2:44
 * @version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     *员工登录
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        //将传来的密码进行MD5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //根据用户名查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        if (emp == null){
            return  R.error("登录失败，用户名不存在");
        }
        if (!emp.getPassword().equals(password)){
            return R.error("登录失败,密码错误");
        }
        if (emp.getStatus() == 0){
            return  R.error("员工帐号已禁用");
        }

        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);

    }

    /**
     * 员工退出
     * @param request
     * @return
     */

    @PostMapping("/logout")
    public  R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return  R.success("退出成功");

    }

    @RequestMapping
    public  R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("员工增加，员工信息：{}",employee.toString());
        //设置初始密码为123456
        employee.setPassword(DigestUtils.md5DigestAsHex("12345".getBytes()));
        //设置时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        //获得主管ID
        Long empld = (Long) request.getSession().getAttribute("employee");
        employee.setCreateUser(empld);
        employee.setUpdateUser(empld);
        //调用service层将数据存入数据库，这里调用的是service继承的IService里提供的save方法
        employeeService.save(employee);

        return  R.success("新增员工成功");
    }

    /**
     * 员工信息的分页查询
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        log.info("page={},pageSize={},name={}",page,pageSize,name);

        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotBlank(name),Employee::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        //执行查询
        employeeService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 修改员工信息
     * @param employee
     * @return
     */
    @PutMapping
    public  R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        log.info(employee.toString());
        Long empid = (Long) request.getSession().getAttribute("employee");
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(empid);
        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }

    //修改完后数据回显
    @GetMapping("/{id}")
    public  R<Employee> getById(@PathVariable Long id){
        log.info("根据员工id查询员工信息");
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee);
        }
        return R.error("没有查到对应员工信息");

    }
}

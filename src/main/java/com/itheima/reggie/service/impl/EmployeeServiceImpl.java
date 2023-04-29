package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.mapper.EmployeeMapper;
import com.itheima.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @projectName: reggie_take_out
 * @package: com.itheima.reggie.service.impl
 * @className: EmployeeServiceImpl
 * @author: Eric
 * @description: TODO
 * @date: 2023/4/28 2:40
 * @version: 1.0
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl <EmployeeMapper, Employee> implements EmployeeService {
}

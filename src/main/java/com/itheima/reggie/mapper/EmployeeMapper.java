package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @projectName: reggie_take_out
 * @package: com.itheima.reggie.mapper
 * @className: EmployeeMapper
 * @author: Eric
 * @description: TODO
 * @date: 2023/4/28 2:24
 * @version: 1.0
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {//提供常见的增删改查方法
}

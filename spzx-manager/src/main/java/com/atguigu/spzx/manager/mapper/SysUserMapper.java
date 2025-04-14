package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface SysUserMapper {
    //根据用户名查询用户
    SysUser selectByUsername(String userName);

    List<SysUser> findAll(SysUserDto sysUserDto);

    Integer selectPhone(String phone);

    void addUser(SysUser sysUser);

    SysUser selectUserById(Long id);

    void updateById(SysUser sysUser);

    void deleteSysUser(Long id);
}

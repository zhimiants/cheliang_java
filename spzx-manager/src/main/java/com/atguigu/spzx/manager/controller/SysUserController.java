package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "用户管理")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Operation(summary = "查询用户列表")
    @GetMapping("/findByPage/{pageNum}/{pageSize}")
    public Result findByPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize,SysUserDto sysUserDto){
        PageInfo pageInfo = sysUserService.findByPage(pageNum,pageSize,sysUserDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "添加用户信息")
    @PostMapping("/addUser")
    public Result addUser(@RequestBody SysUser sysUser){
        sysUserService.addUser(sysUser);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "修改用户信息")
    @PutMapping("/updateSysUser")
    public Result updateSysUser(@RequestBody SysUser sysUser){
        sysUserService.updateSysUser(sysUser);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "根据id删除用户信息")
    @DeleteMapping("/deleteSysUser/{id}")
    public Result deleteSysUser(@PathVariable Long id){
        sysUserService.deleteSysUser(id);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "批量删除用户信息")
    @DeleteMapping("/deleteBatch")
    public Result deleteSysUser(@RequestBody List<Long> ids){
        sysUserService.deleteBatch(ids);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
}

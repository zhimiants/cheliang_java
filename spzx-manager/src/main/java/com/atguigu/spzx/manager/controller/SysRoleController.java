package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysRoleService;
import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Tag(name = "角色管理")
@RestController
@RequestMapping(value = "/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Operation(summary = "查询角色列表")
    //根据roleName模糊查询
    @PostMapping("/findByPage/{pageNum}/{pageSize}")
    public Result findByPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize, @RequestBody SysRoleDto sysRoleDto){
        PageInfo pageInfo = sysRoleService.findByPage(pageNum,pageSize,sysRoleDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);

    }
    @Operation(summary = "添加角色[roleName和roleCode不可重复]")
    @PostMapping("/addRole")
    public Result addRole(@RequestBody SysRole sysRole){
        sysRoleService.addRole(sysRole);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "修改角色")
    @PutMapping("/updateRole")
    public Result updateRole(@RequestBody SysRole sysRole){
        sysRoleService.updateRole(sysRole);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "删除角色")
    @DeleteMapping("/deleteRole/{roleId}")
    public Result deleteRole(@PathVariable Long roleId){
        sysRoleService.deleteRole(roleId);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "查询角色列表")
    @GetMapping("/findRoleList")
    public Result findRoleList(){
        List<SysRole> list = sysRoleService.findRoleList();
        return Result.build(list,ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "查询当前用户分配的角色id")
    @GetMapping("/getRoleListByUserId/{userId}")
    public Result getRoleListByUserId(@PathVariable Long userId){
        List<Long> roleIdList = sysRoleService.getRoleListByUserId(userId);
        return Result.build(roleIdList,ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "优化版:查询角色列表和用户对应角色id集合")
    @GetMapping("/getRoleListAndRoleIdList/{userId}")
    public Result getRoleListAndRoleIdList(@PathVariable Long userId){
        List<Long> roldIdList = sysRoleService.getRoleListByUserId(userId);
        List<SysRole> roleList = sysRoleService.findRoleList();

        Map map = new HashMap();
        map.put("roleIdList",roldIdList);
        map.put("roleList",roleList);
        return Result.build(map,ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "给用户分配角色")
    @PostMapping("doAssign")
    public Result doAssign(@RequestBody AssginRoleDto assginRoleDto){
        sysRoleService.doAssign(assginRoleDto);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
}

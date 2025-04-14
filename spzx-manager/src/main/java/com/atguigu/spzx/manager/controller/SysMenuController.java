package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysMenuService;

import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import com.atguigu.spzx.util.ThreadLocalUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;


@Tag(name = "菜单管理")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {

    @Autowired
    SysMenuService sysMenuService;

    @Operation(summary = "查询一级菜单")
    @GetMapping("/findMenuList")
    public Result findMenuList(){
        List<SysMenu> list = sysMenuService.findByParentId(0L);
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "添加菜单")
    @PostMapping("/addMenu")
    public Result addMenu(@RequestBody SysMenu sysMenu){
        sysMenuService.addMenu(sysMenu);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "修改菜单")
    @PutMapping("/update")
    public Result updateMenu(@RequestBody SysMenu sysMenu){
        sysMenuService.updateMenu(sysMenu);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }


    @Operation(summary = "删除菜单")
    @DeleteMapping("/deleteMenu/{menuId}")
    public Result deleteMenu(@PathVariable Long menuId){
        sysMenuService.deleteMenu(menuId);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "给角色分配菜单")
    @PostMapping("/assignMenuForRole")
    public Result assignMenuForRole(@RequestBody AssginMenuDto assginMenuDto){
        sysMenuService.assignMenuForRole(assginMenuDto);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "根据角色id查询已经分配的菜单id集合")
    @GetMapping("/findMenuIdListByRoleId/{roleId}")
    public Result findMenuIdListByRoleId(@PathVariable Long roleId){
        List<Long> menuIdList = sysMenuService.findMenuIdListByRoleId(roleId);
        return Result.build(menuIdList,ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "查询当前用户的菜单列表")
    @GetMapping("/menus")
    public Result menus(){
        //ThreadLocalUtil.get()可以创建一个SysUser对象,获取该对象的id
        Long userId = ThreadLocalUtil.get().getId();
        //OL? 该用户的一级菜单
        //SysMenuVo 有tile name对应vue组件 children
        List<SysMenuVo> menuVoList = sysMenuService.menus(userId,0L);
        return Result.build(menuVoList,ResultCodeEnum.SUCCESS);
    }

}

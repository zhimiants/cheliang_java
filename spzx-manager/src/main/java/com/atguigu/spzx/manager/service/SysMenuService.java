package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.system.SysMenuVo;

import java.util.List;



public interface SysMenuService {

    List<SysMenu> findByParentId(long parentId);

    void addMenu(SysMenu sysMenu);

    void deleteMenu(Long menuId);

    void updateMenu(SysMenu sysMenu);

    void assignMenuForRole(AssginMenuDto assginMenuDto);

    List<Long> findMenuIdListByRoleId(Long roleId);

    List<SysMenuVo> menus(Long userId, Long parentId);
}

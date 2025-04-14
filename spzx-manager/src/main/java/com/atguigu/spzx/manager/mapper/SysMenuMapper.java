package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface SysMenuMapper {

    List<SysMenu> findByParentId(long parentId);

    void addMenu(SysMenu sysMenu);

    void deleteMenu(Long menuId);

    void updateMenu(SysMenu sysMenu);

    void deleteByRoleId(Long roleId);

    void assignMenuForRole(AssginMenuDto assginMenuDto);

    List<Long> findMenuIdListByRoleId(Long roleId);

    List<SysMenu> menusByUserIdAndParentId(Long userId, Long parentId);
}

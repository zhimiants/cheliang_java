package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface SysRoleMapper {
    List<SysRole> selectByRoleName(String roleName);

    Integer selectCountByRoleName(String roleName);

    Integer selectCountByRoleCode(String roleCode);

    void addRole(SysRole sysRole);

    SysRole selectById(Long id);

    void updateById(SysRole sysRole);

    void deleteById(Long roleId);

    List<SysRole> findRoleList();

    List<Long> getRoleListByUserId(Long userId);

    void deleteRoleByUserId(Long userId);

    void doAssign(Long userId, Long roleId);

    void batchDoAssign(AssginRoleDto assginRoleDto);
}

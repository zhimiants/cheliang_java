package com.atguigu.spzx.manager.service.Impl;

import com.atguigu.spzx.common.exp.GuiguException;
import com.atguigu.spzx.manager.mapper.SysRoleMapper;
import com.atguigu.spzx.manager.service.SysRoleService;
import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Override
    public PageInfo findByPage(Integer pageNum, Integer pageSize, SysRoleDto sysRoleDto) {

        //1.设置分页参数
        PageHelper.startPage(pageNum,pageSize);
        //2.查询结果放在list
        List<SysRole> list = sysRoleMapper.selectByRoleName(sysRoleDto.getRoleName());
        //3.将list封装成pageinfo
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public void addRole(SysRole sysRole) {
        //1.非空校验
        String roleName = sysRole.getRoleName();
        String roleCode = sysRole.getRoleCode();

        if (StringUtils.isEmpty(roleName)){
            throw new GuiguException(200,"角色名称不能为空");
        }
        if (StringUtils.isEmpty(roleCode)){
            throw new GuiguException(201,"角色编号不能为空");
        }
        //2.校验roleName和roleCode是否重复
        Integer countByRoleName = sysRoleMapper.selectCountByRoleName(roleName);
        if (countByRoleName > 0){
            throw new GuiguException(202,"角色名重复");
        }

        Integer countByRoleCode = sysRoleMapper.selectCountByRoleCode(roleCode);
        if (countByRoleCode > 0){
            throw new GuiguException(203,"角色编号重复");
        }
        //添加角色
        sysRoleMapper.addRole(sysRole);
    }

    @Override
    public void updateRole(SysRole sysRole) {
        //1.非空校验  角色id 编号 角色名字
        Long id = sysRole.getId();
        String roleName = sysRole.getRoleName();
        String roleCode = sysRole.getRoleCode();
        if (id == null){
            throw new GuiguException(233,"角色id不能为空");
        }
        if (StringUtils.isEmpty(roleName)){
            throw new GuiguException(234,"角色名称不能为空");
        }
        if (StringUtils.isEmpty(roleCode)){
            throw new GuiguException(235,"角色编号不能为空");
        }
        //2.判断传来和修改的是否相同[同一个角色],不同角色就需要检查是否重复

        //点击修改按钮的对象,sysRoleFromDB
        SysRole sysRoleFromDB = sysRoleMapper.selectById(id);

        if (!sysRoleFromDB.getRoleName().equals(roleName)){
            //查数据库中也没有
            Integer countByRoleName = sysRoleMapper.selectCountByRoleName(roleName);
            if (countByRoleName > 0){
                throw new GuiguException(255,"该角色名已存在");
            }
        }

        if (!sysRoleFromDB.getRoleCode().equals(roleCode)){
            //查数据库中也没有
            Integer countByRoleCode = sysRoleMapper.selectCountByRoleCode(roleCode);
            if (countByRoleCode > 0){
                throw new GuiguException(256,"该角色编号已存在");
            }
        }

        //根据id修改角色  //参数需要不止一个id
        sysRoleMapper.updateById(sysRole);
    }

    @Override
    public void deleteRole(Long roleId) {
        sysRoleMapper.deleteById(roleId);
    }

    @Override
    public List<SysRole> findRoleList() {
        return sysRoleMapper.findRoleList();
    }

    @Override
    public List<Long> getRoleListByUserId(Long userId) {
        return sysRoleMapper.getRoleListByUserId(userId);
    }

    @Override
    public void doAssign(AssginRoleDto assginRoleDto) {
        Long userId = assginRoleDto.getUserId();
        //先删除
        sysRoleMapper.deleteRoleByUserId(userId);
        //再增加
        List<Long> roleIdList = assginRoleDto.getRoleIdList();
        for (Long roleId : roleIdList) {

            sysRoleMapper.doAssign(userId,roleId);
        }

        //遍历分配
        //sysRoleMapper.batchDoAssign(assginRoleDto);
    }
}

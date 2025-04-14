package com.atguigu.spzx.manager.service.Impl;

import com.atguigu.spzx.common.exp.GuiguException;
import com.atguigu.spzx.manager.mapper.SysMenuMapper;
import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> findByParentId(long parentId) {
        //根据父菜单查到所有children菜单
        List<SysMenu> list = sysMenuMapper.findByParentId(parentId);
        //遍历children菜单
        list.forEach((SysMenu sysMenu) -> {
            //子菜单的子菜单等于调用方法根据自己的id作为父菜单id
            sysMenu.setChildren(this.findByParentId(sysMenu.getId()));
        });
        return list;
    }

    @Override
    public void addMenu(SysMenu sysMenu) {
        sysMenuMapper.addMenu(sysMenu);
    }

    @Override
    public void deleteMenu(Long menuId) {
        //如果有下级菜单就不能删除
        //menuId就是菜单的id
        List<SysMenu> children = sysMenuMapper.findByParentId(menuId);
        if (children != null && children.size() > 0){
            throw new GuiguException(288,"该菜单有子菜单,不能直接删除");
        }
        //逻辑删除
        sysMenuMapper.deleteMenu(menuId);
    }

    @Override
    public void updateMenu(SysMenu sysMenu) {
        sysMenuMapper.updateMenu(sysMenu);
    }

    @Override
    public void assignMenuForRole(AssginMenuDto assginMenuDto) {
        //先删除原有的数据
        sysMenuMapper.deleteByRoleId(assginMenuDto.getRoleId());
        //再重新给角色建立菜单之间的关联 也要roleId 所以直接传assginMenuDto
        sysMenuMapper.assignMenuForRole(assginMenuDto);
    }

    @Override
    public List<Long> findMenuIdListByRoleId(Long roleId) {
        List<Long> menuIdList = sysMenuMapper.findMenuIdListByRoleId(roleId);
        return menuIdList;
    }

    @Override
    public List<SysMenuVo> menus(Long userId, Long parentId) {
        List<SysMenu> sysMenuList = this.getSysMenuList(userId, parentId);
        //2、将List<SysMenu> 泛型转成  List<SysMenuVo>
        List<SysMenuVo> sysMenuVoList = this.sysMenuToVo(sysMenuList);

        return sysMenuVoList;
    }


    //定义私有方法,返回所有一级菜单和对应的下级菜单
    private List<SysMenu> getSysMenuList(Long userId, Long parentId){
        List<SysMenu> list = sysMenuMapper.menusByUserIdAndParentId(userId,parentId);
        //设置SysMenu的children属性
        list.forEach(sysMenu -> {
            sysMenu.setChildren(this.getSysMenuList(userId,sysMenu.getId()));
        });

        return list;
    }
    //把集合泛型从SysMenu -> SysMenuVo
    private List<SysMenuVo> sysMenuToVo(List<SysMenu> sysMenuList) {
        List<SysMenuVo> sysMenuVoList = new ArrayList<>();
        sysMenuList.forEach(sysMenu -> {
            //每个sysMenu转成sysMenuVo
            SysMenuVo sysMenuVo = new SysMenuVo();
            sysMenuVo.setTitle(sysMenu.getTitle());
            sysMenuVo.setName(sysMenu.getComponent());
            if(sysMenu.getChildren()!=null && sysMenu.getChildren().size()>0){
                List<SysMenu> children = sysMenu.getChildren();
                List<SysMenuVo> childrenVoList = this.sysMenuToVo(children);
                sysMenuVo.setChildren(childrenVoList);
            }else{
                sysMenuVo.setChildren(null);
            }
            sysMenuVoList.add(sysMenuVo);
        });
        return sysMenuVoList;
    }

}

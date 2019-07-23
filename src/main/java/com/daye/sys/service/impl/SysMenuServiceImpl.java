package com.daye.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daye.common.annotation.RequiredLog;
import com.daye.common.vo.JsonResult;
import com.daye.common.vo.Node;
import com.daye.sys.entity.SysMenu;
import com.daye.sys.mapper.SysMenuMapper;
import com.daye.sys.service.SysMenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单管理 服务实现类
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    SysMenuMapper sysMenuMapper;

    @Override
    @RequiredLog(operation = "根据权限获得菜单信息")
    public JsonResult loadIntexMenus() {
        Subject subject = SecurityUtils.getSubject();

        List<SysMenu> menus = sysMenuMapper.findObject();
        List<SysMenu> menusCopy = menus;
        for(int i=menusCopy.size()-1;i>=0;i--) {
            if(!subject.isPermitted(menusCopy.get(i).getPermission())) {
                menus.remove(i);
            }
        }
        menusCopy = menus;
        for(int i=menusCopy.size()-1;i>=0;i--) {
            if(menusCopy.get(i).getHtmlid()==null || menusCopy.get(i).getHtmlid().equals("")) {
                menus.remove(i);
            }
        }
        return new JsonResult(menus);
    }

    @Override
    @RequiredLog(operation = "获得菜单树")
    public JsonResult findZtreeMenuNodes() {
        List<Node> list = sysMenuMapper.findZtreeMenuNodes();
        return new JsonResult(list);
    }
}

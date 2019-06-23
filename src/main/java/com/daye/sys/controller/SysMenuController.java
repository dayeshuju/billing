package com.daye.sys.controller;


import com.daye.common.vo.JsonResult;
import com.daye.sys.entity.SysMenu;
import com.daye.sys.mapper.SysMenuMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 菜单管理 前端控制器
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
@RestController
@RequestMapping("/sysMenu")
public class SysMenuController {

    @Autowired
    SysMenuMapper sysMenuMapper;

    @RequestMapping("/doLoadMenu")
    public JsonResult doLoadUI() {
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
}


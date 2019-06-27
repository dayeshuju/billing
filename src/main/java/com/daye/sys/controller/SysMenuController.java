package com.daye.sys.controller;


import com.daye.common.vo.JsonResult;
import com.daye.sys.entity.SysMenu;
import com.daye.sys.mapper.SysMenuMapper;
import com.daye.sys.service.SysMenuService;
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
    SysMenuService sysMenuService;

    @RequestMapping("/doFindZtreeMenuNodes")
    public JsonResult doFindZtreeMenuNodes(){
        return sysMenuService.findZtreeMenuNodes();
    }

    @RequestMapping("/doLoadMenu")
    public JsonResult doLoadUI() {
        return sysMenuService.loadIntexMenus();
    }
}


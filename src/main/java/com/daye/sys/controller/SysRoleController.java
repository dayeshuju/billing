package com.daye.sys.controller;


import com.daye.common.vo.JsonResult;
import com.daye.sys.entity.SysRole;
import com.daye.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
@RestController
@RequestMapping("/sysRoles")
public class SysRoleController {

    @Autowired
    SysRoleService sysRoleService;

    @RequestMapping("/getRolelist")
    public JsonResult getRolelist(){
        List<SysRole> sysRoleList = sysRoleService.getObject();
        return new JsonResult(sysRoleList);
    }

}


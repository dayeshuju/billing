package com.daye.sys.controller;


import com.daye.common.util.JsonToMap;
import com.daye.common.vo.JsonResult;
import com.daye.sys.entity.SysRole;
import com.daye.sys.service.SysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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
    @RequiresPermissions("sys:user")
    public JsonResult getRolelist(){
        List<SysRole> sysRoleList = sysRoleService.getObject();
        return new JsonResult(sysRoleList);
    }

    @RequestMapping("/getAuthoritylist")
    @RequiresPermissions("sys:permission")
    public Map<String,Object> getAuthoritylist(@RequestParam Map<String,String> aoData){
        aoData = JsonToMap.jsonToMap(aoData.get("aoData"));
        Map<String,Object> map = sysRoleService.getAuthoritylist(aoData);
        return map;
    }

    @RequestMapping("/addpername")
    @RequiresPermissions("sys:permission")
    public JsonResult getpername(SysRole sysRole,Integer... menuIds ){

        return sysRoleService.addAuthority(sysRole,menuIds);
    }

    @RequestMapping("/findOne")
    @RequiresPermissions("sys:permission")
    public JsonResult findOne(Integer id){

        return sysRoleService.findOne(id);
    }

    @RequestMapping("/getrolemenus")
    @RequiresPermissions("sys:permission")
    public JsonResult getrolemenus(Integer id ){
        return sysRoleService.getrolemenus(id);
    }

    @RequestMapping("/updateObject")
    @RequiresPermissions("sys:permission")
    public JsonResult updateObject(SysRole sysRole,Integer... menuIds){
        return sysRoleService.updateObject(sysRole,menuIds);
    }

/*    @RequestMapping("/deleteAuth")
    @RequiresPermissions("sys:permission")
    public JsonResult deleteAuth(Integer id){
        return sysRoleService.deleteAuth(id);
    }*/
}


package com.daye.sys.controller;


import com.daye.common.util.JsonToMap;
import com.daye.common.util.ShiroUtils;
import com.daye.common.vo.JsonResult;
import com.daye.sys.entity.SysUser;
import com.daye.sys.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 系统用户 前端控制器
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
@RestController
@RequestMapping("/sysUsers")
public class SysUserController {

    @Autowired
    SysUserService sysUserService;

    @RequestMapping("/resetStatus")
    @RequiresPermissions("sys:user")
    public JsonResult resetStatus(Integer id){
        return sysUserService.resetStatus(id);
    }

    @RequestMapping("/resetPassword")
    @RequiresPermissions("sys:user")
    public JsonResult resetPassword(Integer id){
        return sysUserService.resetPassword(id);
    }

    @RequestMapping("/deleteUser")
    @RequiresPermissions("sys:user")
    public JsonResult deleteUser(Integer id){
        return sysUserService.deleteUser(id);
    }

    @RequestMapping("/updateUser")
    @RequiresPermissions("sys:user")
    public JsonResult updateUser(SysUser user){
        return sysUserService.updateUser(user);
    }

    @RequestMapping("/getUser")
    @RequiresPermissions("sys:user")
    public JsonResult getUser(Integer id){
        return sysUserService.getUser(id);
    }

    @RequestMapping("/addUser")
    @RequiresPermissions("sys:user")
    public JsonResult addUser(SysUser user){
        return sysUserService.addUser(user);
    }

    @RequestMapping("/doLoadUser")
    public JsonResult doLoadUser(){
        SysUser sysUser = ShiroUtils.getPrincipal();
        sysUser.setPassword(null);
        sysUser.setSalt(null);
        sysUser.setName(null);
        return new JsonResult(sysUser);
    }

    @RequestMapping("/updatePwd")
    @RequiresPermissions("sys:userself")
    public JsonResult updatePassword(String oldpwd, String newpwd){
        sysUserService.updateObject(oldpwd,newpwd);
        return new JsonResult("修改成功");
    }

    @RequestMapping("/getUserList")
    @RequiresPermissions("sys:user")
    public Map<String,Object> getUserList(@RequestParam Map<String,String> aoData){
        aoData =JsonToMap.jsonToMap(aoData.get("aoData"));
        Map<String,Object> map = sysUserService.findObject(aoData);
        return map;
    }
}


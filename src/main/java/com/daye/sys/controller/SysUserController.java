package com.daye.sys.controller;


import com.daye.common.util.JsonToMap;
import com.daye.common.util.ShiroUtils;
import com.daye.common.vo.JsonResult;
import com.daye.common.vo.PageInfo;
import com.daye.sys.entity.SysUser;
import com.daye.sys.service.SysUserService;
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

    @RequestMapping("/doLoadUser")
    public JsonResult doLoadUser(){
        SysUser sysUser = ShiroUtils.getPrincipal();
        sysUser.setPassword(null);
        sysUser.setSalt(null);
        sysUser.setName(null);
        return new JsonResult(sysUser);
    }

    @RequestMapping("/updatePwd")
    public JsonResult updatePassword(String oldpwd, String newpwd){
        sysUserService.updateObject(oldpwd,newpwd);
        return new JsonResult("修改成功");
    }

    @RequestMapping("/getUserList")
    public PageInfo<SysUser> getUserList(@RequestParam Map<String,String> aoData){
        aoData =JsonToMap.jsonToMap(aoData.get("aoData"));
        PageInfo<SysUser> pageInfo = sysUserService.findObject(aoData);
        return pageInfo;
    }
}


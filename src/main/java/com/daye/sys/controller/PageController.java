package com.daye.sys.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {

    @RequestMapping("doLoginUI")
    public String doLoginUI() {
        return "login";
    }

    @RequestMapping("index")
    public String loadIndexUI() { return "index";}

    @RequestMapping("changePassword")
    public String loadChangePasswordUI() {
        return "changePassword";
    }

    @RequestMapping("sysUser")
    @RequiresPermissions("sys:user")
    public String loadSysUserUI() {
        return "sysUser";
    }

    @RequestMapping("sysPermission")
    @RequiresPermissions("sys:permission")
    public String loadSysPermissionUI(){
        return "sysPermission";
    }

    @RequestMapping("tbYhlx")
    @RequiresPermissions("sys:tbyhlx")
    public String loadTbYhlxUI(){
        return "tbYhlx";
    }

    @RequestMapping("tbJfjl")
    @RequiresPermissions("sys:tbjfjl")
    public  String loadTbJfjlUI(){
        return "tbJfjl";
    }

    @RequestMapping("tbYdyh")
    @RequiresPermissions("sys:tbydyh")
    public String loadTbYdyhUI(){
        return "tbYdyh";
    }

    @RequestMapping("tbCbjl")
    @RequiresPermissions("sys:tbcbjl")
    public String loadTbCbjlUI(){
        return "tbCbjl";
    }

    @RequestMapping("tbJlsb")
    @RequiresPermissions("sys:tbjlsb")
    public String loadTbJlsbUI(){
        return "tbJlsb";
    }

    @RequestMapping("tbSyt")
    @RequiresPermissions("sys:tbsyt")
    public String loadTbSytUI(){
        return "tbSyt";
    }

}

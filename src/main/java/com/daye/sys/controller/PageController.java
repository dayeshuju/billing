package com.daye.sys.controller;

import net.sf.ehcache.CacheManager;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class PageController {

    @RequestMapping("doLoginUI")
    public String doLoginUI(HttpServletRequest request) {
        //获取请求头并存入session
/*        String language = request.getHeader("Accept-Language").substring(0,2);
        request.getSession().setAttribute("language",language);*/
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

    @RequestMapping("tbSftj")
    @RequiresPermissions("sys:tbsftj")
    public String loadTbSftjUI(){
        return "tbSftj";
    }

}

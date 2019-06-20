package com.daye.sys.controller;

import com.daye.sys.entity.SysUser;
import com.daye.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value="/index",method= RequestMethod.GET)
public class IndexController {
    @Autowired
    SysUserService sysUserService;
    @RequestMapping(value="",method=RequestMethod.GET)
    public String index(HttpServletRequest request, Model model){
        List<Object> glfls=new ArrayList<Object>();
        model.addAttribute("gnfls",glfls);
        return "index";
    }

    @RequestMapping("/test")
    @ResponseBody
    public SysUser test(String id){
        SysUser user = sysUserService.getUser(id);
        return user;
    }
}

package com.daye.sys.controller;

import com.daye.sys.entity.SysMenus;
import com.daye.sys.service.SysMenusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value="/index",method= RequestMethod.GET)
public class IndexController {
    @Autowired
    private SysMenusService sysMenusService;
    @RequestMapping(value="",method=RequestMethod.GET)
    public String index(HttpServletRequest request, Model model){
        List<SysMenus> glfls=sysMenusService.getGnfls();
        model.addAttribute("gnfls",glfls);
        return "index";
    }
}

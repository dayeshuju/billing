package com.daye.sys.controller;

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
    @RequestMapping(value="",method=RequestMethod.GET)
    public String index(HttpServletRequest request, Model model){
        List<Object> glfls=new ArrayList<Object>();
        model.addAttribute("gnfls",glfls);
        return "index";
    }
}

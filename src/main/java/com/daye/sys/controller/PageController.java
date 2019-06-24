package com.daye.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {

    @RequestMapping("doLoginUI")
    public String login() {
        return "login";
    }

    @RequestMapping("{pageName}")
    public String index(@PathVariable String pageName) {
        return pageName;
    }

}

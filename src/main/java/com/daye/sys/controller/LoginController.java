package com.daye.sys.controller;

import com.daye.common.annotation.RequiredLog;
import com.daye.common.vo.JsonResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class LoginController {

    @RequestMapping("doLogin")
    @RequiredLog(operation="登录")
    public JsonResult doLogin(String nickname, String password){
        //1.获取Subject对象
        Subject subject= SecurityUtils.getSubject();
        //2.通过Subject提交用户信息,交给shiro框架进行认证操作
        //2.1对用户进行封装
        UsernamePasswordToken token=
                new UsernamePasswordToken(
                        nickname,//身份信息
                        password);//凭证信息
        //2.2对用户信息进行身份认证
        subject.login(token);
        return new JsonResult("登录成功");
    }
}

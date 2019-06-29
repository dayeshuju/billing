package com.daye.sys.controller;


import com.daye.common.util.JsonToMap;
import com.daye.sys.service.TbYhlxService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 用户类型 前端控制器
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
@RestController
@RequestMapping("/tbYhlx")
public class TbYhlxController {
    @Autowired
    TbYhlxService tbYhlxService;

    @RequestMapping("/getYhlxList")
    @RequiresPermissions("sys:tbyhlx")
    public Map<String,Object> getYhlxList(@RequestParam Map<String,String> aoData){
        aoData =JsonToMap.jsonToMap(aoData.get("aoData"));
        Map<String,Object> map = tbYhlxService.findObject(aoData);
        return map;
    }
}


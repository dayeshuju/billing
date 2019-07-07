package com.daye.sys.controller;

import com.daye.common.util.JsonToMap;
import com.daye.common.vo.JsonResult;
import com.daye.sys.service.SytService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/syt")
public class SytController {

    @Autowired
    SytService sytService;

    @RequestMapping("/getJfyhList")
    @RequiresPermissions("sys:tbsyt")
    public Map<String,Object> getJfyhList(@RequestParam Map<String,String> aoData){
        aoData = JsonToMap.jsonToMap(aoData.get("aoData"));
        Map<String,Object> map = sytService.findObject(aoData);
        return map;
    }

    @RequestMapping("/getJfyh")
    @RequiresPermissions("sys:tbsyt")
    public JsonResult getJfyh(Integer id){
        return sytService.getJfyh(id);
    }

    @RequestMapping("/saveJfyh")
    @RequiresPermissions("sys:tbsyt")
    public JsonResult saveJfyh(Integer id,Double actualAmount,String note){
        return sytService.saveJfyh(id,actualAmount,note);
    }
}

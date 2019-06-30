package com.daye.sys.controller;


import com.daye.common.util.JsonToMap;
import com.daye.sys.service.TbCbjlService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 抄表记录 前端控制器
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
@RestController
@RequestMapping("/tbCbjl")
public class TbCbjlController {

    @Autowired
    TbCbjlService tbCbjlService;

    @RequestMapping("getCbjlList")
    @RequiresPermissions("sys:tbcbjl")
    public Map<String,Object> getCbjlList(@RequestParam Map<String,String> aoData){
        aoData = JsonToMap.jsonToMap(aoData.get("aoData"));
        Map<String,Object> map = tbCbjlService.findObject(aoData);
        return map;
    }
}


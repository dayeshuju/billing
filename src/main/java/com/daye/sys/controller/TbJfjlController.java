package com.daye.sys.controller;


import com.daye.common.util.JsonToMap;
import com.daye.common.vo.JsonResult;
import com.daye.sys.service.TbJfjlService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 缴费记录 前端控制器
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
@RestController
@RequestMapping("/tbJfjl")
public class TbJfjlController {
@Autowired
    TbJfjlService tbJfjlService;

    @RequestMapping("getJfjlList")
    @RequiresPermissions("sys:tbJfjl")
    public Map<String,Object> getJfjlList(@RequestParam Map<String,String> aoData){
        aoData = JsonToMap.jsonToMap(aoData.get("aoData"));
        Map<String,Object> map = tbJfjlService.findObject(aoData);
        return map;
    }

    @RequestMapping("getHistoryJfjlList")
    @RequiresPermissions("sys:tbJfjl")
    public Map<String,Object> getHistoryJfjlList(String startTime,String endTime,String meterId,@RequestParam Map<String,String> aoData){
        aoData = JsonToMap.jsonToMap(aoData.get("aoData"));
        Map<String,Object> map = tbJfjlService.getHistoryJfjlList(startTime,endTime,meterId,aoData);
        return map;
    }
    @RequestMapping("deleteJfjl")
    @RequiresPermissions("sys:tbJfjl")
    public JsonResult deleteJfjl(Integer id ){
        return tbJfjlService.deleteById(id);
    }

    @RequestMapping("getJfjl")
    @RequiresPermissions("sys:tbJfjl")
    public JsonResult getJfjl(Long id ){
        return tbJfjlService.getJfjl(id);
    }

}


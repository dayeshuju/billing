package com.daye.sys.controller;


import com.daye.common.util.JsonToMap;
import com.daye.common.vo.JsonResult;
import com.daye.sys.entity.TbYdyh;
import com.daye.sys.service.TbYdyhService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 用电用户 前端控制器
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
@RestController
@RequestMapping("/tbYdyh")
public class TbYdyhController {

    @Autowired
    TbYdyhService tbYdyhService;

/*    @RequestMapping("/deleteYdyh")
    @RequiresPermissions("sys:tbydyh")
    public JsonResult deleteYdyh(Integer id){
        return tbYdyhService.deleteYdyh(id);
    }*/

    @RequestMapping("/addYdyh")
    @RequiresPermissions("sys:tbydyh")
    public JsonResult addYdyh(TbYdyh ydyh){
        return tbYdyhService.addYdyh(ydyh);
    }

    @RequestMapping("/updateYdyh")
    @RequiresPermissions("sys:tbydyh")
    public JsonResult updateYdyh(TbYdyh ydyh){
        return tbYdyhService.updateYdyh(ydyh);
    }

    @RequestMapping("/getYdyh")
    @RequiresPermissions("sys:tbydyh")
    public JsonResult getYdyh(Long id){
        return tbYdyhService.getYdyh(id);
    }

    @RequestMapping("/getYhlxlist")
    @RequiresPermissions("sys:tbydyh")
    public JsonResult getYhlxlist(){
        return tbYdyhService.getYhlxlist();
    }

    @RequestMapping("/getYdyhList")
    @RequiresPermissions("sys:tbydyh")
    public Map<String,Object> getYdyhList(@RequestParam Map<String,String> aoData){
        aoData = JsonToMap.jsonToMap(aoData.get("aoData"));
        Map<String,Object> map = tbYdyhService.getYdyhList(aoData);
        return map;
    }
}


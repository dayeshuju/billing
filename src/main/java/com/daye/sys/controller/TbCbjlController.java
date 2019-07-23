package com.daye.sys.controller;


import com.daye.common.util.JsonToMap;
import com.daye.common.vo.JsonResult;
import com.daye.sys.service.TbCbjlService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @RequestMapping("/doUpload")
    @RequiresPermissions("sys:tbcbjl")
    public JsonResult doUpload(@RequestParam MultipartFile file){
        return tbCbjlService.uploadCbjl(file);
    }

/*    @RequestMapping("deleteCbjl")
    @RequiresPermissions("sys:tbcbjl")
    public JsonResult deleteCbjl(Integer id){
        return tbCbjlService.deleteCbjl(id);
    }*/

    @RequestMapping("getHistoryCbjlList")
    @RequiresPermissions("sys:tbcbjl")
    public Map<String,Object> getHistoryCbjlList(String startTime,String endTime,String meterId,@RequestParam Map<String,String> aoData){
        aoData = JsonToMap.jsonToMap(aoData.get("aoData"));
        Map<String,Object> map = tbCbjlService.getHistoryCbjlList(startTime,endTime,meterId,aoData);
        return map;
    }

    @RequestMapping("getCbjlList")
    @RequiresPermissions("sys:tbcbjl")
    public Map<String,Object> getCbjlList(@RequestParam Map<String,String> aoData){
        aoData = JsonToMap.jsonToMap(aoData.get("aoData"));
        Map<String,Object> map = tbCbjlService.findObject(aoData);
        return map;
    }
}


package com.daye.sys.controller;


import com.daye.common.util.JsonToMap;
import com.daye.common.vo.JsonResult;
import com.daye.sys.entity.vt.VT_Jlsb;
import com.daye.sys.service.TbJlsbService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 计量设备 前端控制器
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
@RestController
@RequestMapping("/tbJlsb")
public class TbJlsbController {
    @Autowired
    TbJlsbService tbjlsbservice;

    @RequestMapping("/getJlsb")
    @RequiresPermissions("sys:tbjlsb")
    public JsonResult getJlsb(Long id){return tbjlsbservice.getJlsb(id);}

    @RequestMapping("/addJlsb")
    @RequiresPermissions("sys:tbjlsb")
    public JsonResult addJlsb(VT_Jlsb Jlsb){return tbjlsbservice.addJlsb(Jlsb);}

    @RequestMapping("/updateJlsb")
    @RequiresPermissions("sys:tbjlsb")
    public JsonResult updateJlsb(VT_Jlsb Jlsb){return tbjlsbservice.updateJlsb(Jlsb);}

/*    @RequestMapping("/deleteJlsb")
    @RequiresPermissions("sys:tbjlsb")
    public JsonResult deleteJlsb(Long id){return tbjlsbservice.deleteJlsb(id);}*/

    @RequestMapping("/getJlsbList")
    @RequiresPermissions("sys:tbjlsb")
    public Map<String,Object> getJlsbList(@RequestParam Map<String,String> aoData){
        aoData =JsonToMap.jsonToMap(aoData.get("aoData"));
        Map<String,Object> map = tbjlsbservice.findObject(aoData);
        return map;
    }
}


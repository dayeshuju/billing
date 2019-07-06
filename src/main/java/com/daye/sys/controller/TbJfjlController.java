package com.daye.sys.controller;


import com.daye.common.util.ExcelUtil;
import com.daye.common.util.JsonToMap;
import com.daye.common.vo.JsonResult;
import com.daye.sys.service.TbJfjlService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>
 * 缴费记录 前端控制器
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
@Controller
@RequestMapping("/tbJfjl")
public class TbJfjlController {
@Autowired
    TbJfjlService tbJfjlService;

    @RequestMapping("getJfjlList")
    @RequiresPermissions("sys:tbJfjl")
    @ResponseBody
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
    @ResponseBody
    public JsonResult deleteJfjl(Integer id ){
        return tbJfjlService.deleteById(id);
    }

    @RequestMapping("getJfjl")
    @RequiresPermissions("sys:tbJfjl")
    @ResponseBody
    public JsonResult getJfjl(Long id ){
        return tbJfjlService.getJfjl(id);
    }

    @RequestMapping("exportJfjl")
    @RequiresPermissions("sys:tbJfjl")
    public void exportJfjl(){
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes)ra;
        HttpServletRequest request = sra.getRequest();
        HttpServletResponse response = sra.getResponse();
        String payStatus = request.getParameter("payStatus");
        String meterId = request.getParameter("meterId");
        String idCode = request.getParameter("idCode");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        tbJfjlService.exportJfjl(request,response,payStatus,meterId,idCode,startTime,endTime);
    }
}


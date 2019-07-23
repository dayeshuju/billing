package com.daye.sys.controller;


import com.daye.common.util.ExcelUtil;
import com.daye.common.util.JsonToMap;
import com.daye.common.vo.JsonResult;
import com.daye.sys.entity.vt.VT_Jfjl;
import com.daye.sys.service.TbJfjlService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
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
    @ResponseBody
    public Map<String,Object> getHistoryJfjlList(String startTime,String endTime,String meterId,@RequestParam Map<String,String> aoData){
        aoData = JsonToMap.jsonToMap(aoData.get("aoData"));
        Map<String,Object> map = tbJfjlService.getHistoryJfjlList(startTime,endTime,meterId,aoData);
        return map;
    }
    /*@RequestMapping("deleteJfjl")
    @RequiresPermissions("sys:tbJfjl")
    @ResponseBody
    public JsonResult deleteJfjl(Integer id ){
        return tbJfjlService.deleteById(id);
    }*/

    @RequestMapping("getJfjl")
    @RequiresPermissions("sys:tbJfjl")
    @ResponseBody
    public JsonResult getJfjl(Long id ){
        return tbJfjlService.getJfjl(id);
    }

    @RequestMapping("exportJfjl")
    @RequiresPermissions("sys:tbJfjl")
    public String exportJfjl(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        String payStatus = request.getParameter("payStatus");
        String meterId = request.getParameter("meterId");
        String idCode = request.getParameter("idCode");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        List<VT_Jfjl> jfjlList = tbJfjlService.exportJfjl(payStatus,meterId,idCode,startTime,endTime);

        List<String> titles = new ArrayList<>();
        titles.add("序号");
        titles.add("表号");
        titles.add("姓名");
        titles.add("身份证号");
        titles.add("用户地址");
        titles.add("用户电话");
        titles.add("缴费状态");
        titles.add("用电量");
        titles.add("电费费率");
        titles.add("应缴电费");
        titles.add("实缴电费");
        titles.add("缴费记录创建时间");
        titles.add("缴费记录操作时间");
        titles.add("是否打印");
        titles.add("缴费备注");
        titles.add("用户状态");
        titles.add("用户创建时间");
        titles.add("操作时间");
        titles.add("用户备注");
        ExcelUtil.createExcel(request,response,jfjlList,startTime+"至"+endTime+"缴费记录",titles);

        return  null;
    }
}


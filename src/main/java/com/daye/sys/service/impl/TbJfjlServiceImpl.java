package com.daye.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daye.common.annotation.RequiredLog;
import com.daye.common.util.ExcelUtil;
import com.daye.common.vo.JsonResult;
import com.daye.sys.entity.TbJfjl;
import com.daye.sys.entity.vt.VT_Cbjl;
import com.daye.sys.entity.vt.VT_Jfjl;
import com.daye.sys.mapper.TbJfjlMapper;
import com.daye.sys.service.TbJfjlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 缴费记录 服务实现类
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
@Service
public class TbJfjlServiceImpl extends ServiceImpl<TbJfjlMapper, TbJfjl> implements TbJfjlService {
@Autowired
TbJfjlMapper tbJfjlMapper;
    @Override
    @RequiredLog(operation = "获取最近一期的缴费记录")
    public Map<String, Object> findObject(Map<String, String> aoData) {

        VT_Jfjl vt_jfjl = new VT_Jfjl();
        //姓名
        if(!StringUtils.isEmpty(aoData.get("sSearch_1").trim())) vt_jfjl.setName(aoData.get("sSearch_1"));
        //户号
        if(!StringUtils.isEmpty(aoData.get("sSearch_2").trim())) vt_jfjl.setIdCode(aoData.get("sSearch_2"));
        //表号
        if(!StringUtils.isEmpty(aoData.get("sSearch_3").trim())) vt_jfjl.setMeterId(aoData.get("sSearch_3"));
        //上次缴费时间
        if(!StringUtils.isEmpty(aoData.get("sSearch_4").trim())) vt_jfjl.setPayTime(aoData.get("sSearch_4"));
        //欠费金额
        if(!StringUtils.isEmpty(aoData.get("sSearch_5").trim())){
            String arrearsStr = aoData.get("sSearch_5");
            Double arrears = Double.valueOf(arrearsStr);
            vt_jfjl.setArrears(arrears);
        }

        String sSearch = aoData.get("sSearch");
        Object iDisplayStartObj = aoData.get("iDisplayStart");
        Integer iDisplayStart = (Integer) iDisplayStartObj;
        Object iDisplayLengthsObj = aoData.get("iDisplayLength");
        Integer iDisplayLength = (Integer) iDisplayLengthsObj;
        Object sEchoStr = aoData.get("sEcho");
        Integer sEcho = (Integer) sEchoStr;

        Integer count =tbJfjlMapper.findCount(vt_jfjl,sSearch);
        List<VT_Jfjl> jfjlList = tbJfjlMapper.findObject(vt_jfjl,iDisplayStart,iDisplayLength,sSearch);
        Map<String,Object> map = new HashMap<>();
        map.put("iTotalDisplayRecords",count);
        map.put("iTotalRecords",count);
        map.put("sEcho",sEcho);
        map.put("aaData",jfjlList);
        return map;

    }

    @Override
    @RequiredLog(operation = "根据主键id删除数据")
    public JsonResult deleteById(Integer id) {
        //删除的时候，欠费的，未缴费的不可以删
        TbJfjl jfjl=tbJfjlMapper.selectById(id);
        if(jfjl.getPayStatus()==0||jfjl.getPayStatus()==1){
         return  new JsonResult(new Throwable("欠费的，未缴费的记录不可以删除！！！"));
        }
        if (tbJfjlMapper.deleteById(id)==0) return  new JsonResult(new Throwable("删除失败！！！"));
        return new JsonResult("删除成功！！！");
    }

    @Override
    @RequiredLog(operation = "根据电表号获取该电表的历史数据")
    public Map<String, Object> getHistoryJfjlList(String startTime, String endTime, String meterId, Map<String, String> aoData) {

        Object iDisplayStartObj = aoData.get("iDisplayStart");
        Integer iDisplayStart = (Integer) iDisplayStartObj;
        Object iDisplayLengthsObj = aoData.get("iDisplayLength");
        Integer iDisplayLength = (Integer) iDisplayLengthsObj;
        Object sEchoStr = aoData.get("sEcho");
        Integer sEcho = (Integer) sEchoStr;

        Integer count =tbJfjlMapper.findCountByid(meterId,startTime,endTime);
        List<VT_Cbjl> cbjlList = tbJfjlMapper.findObjectById(meterId,startTime,endTime,iDisplayStart,iDisplayLength);
        Map<String,Object> map = new HashMap<>();
        map.put("iTotalDisplayRecords",count);
        map.put("iTotalRecords",count);
        map.put("sEcho",sEcho);
        map.put("aaData",cbjlList);
        return map;

    }

    @Override
    @RequiredLog(operation = "根据id号获取该详细的数据")
    public JsonResult getJfjl(Long id) {
        VT_Jfjl vt_jfjl= tbJfjlMapper.getJfjl(id);
        return new JsonResult(vt_jfjl);
    }

    @Override
    @RequiredLog(operation = "导出缴费记录",value=0)
    public void exportJfjl(HttpServletRequest request, HttpServletResponse response, String payStatus, String meterId, String idCode, String startTime, String endTime) {
        List<VT_Jfjl> jfjlList = tbJfjlMapper.selectByParameters(payStatus, meterId, idCode, startTime, endTime);
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
        //return new JsonResult("导出成功！！！");
    }

}


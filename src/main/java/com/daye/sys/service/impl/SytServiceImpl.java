package com.daye.sys.service.impl;

import com.daye.common.annotation.RequiredLog;
import com.daye.common.vo.JsonResult;
import com.daye.sys.entity.TbJfjl;
import com.daye.sys.entity.TbYdyh;
import com.daye.sys.entity.TbYhlx;
import com.daye.sys.entity.vt.VT_Jfjl;
import com.daye.sys.mapper.SytMapper;
import com.daye.sys.mapper.TbJfjlMapper;
import com.daye.sys.mapper.TbYdyhMapper;
import com.daye.sys.mapper.TbYhlxMapper;
import com.daye.sys.service.SytService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SytServiceImpl implements SytService {

    @Autowired
    SytMapper sytMapper;
    @Autowired
    TbJfjlMapper tbJfjlMapper;
    @Autowired
    TbYdyhMapper tbYdyhMapper;
    @Autowired
    TbYhlxMapper tbYhlxMapper;

    @Override
    @RequiredLog(operation = "获取未缴费及欠费用户")
    public Map<String, Object> findObject(Map<String, String> aoData) {
        VT_Jfjl vt_jfjl = new VT_Jfjl();
        //姓名
        if(!StringUtils.isEmpty(aoData.get("sSearch_1").trim())) vt_jfjl.setName(aoData.get("sSearch_1"));
        //户号
        if(!StringUtils.isEmpty(aoData.get("sSearch_2").trim())) vt_jfjl.setIdCode(aoData.get("sSearch_2"));
        //表号
        if(!StringUtils.isEmpty(aoData.get("sSearch_3").trim())) vt_jfjl.setMeterId(aoData.get("sSearch_3"));
        //上次缴费时间
        if(!StringUtils.isEmpty(aoData.get("sSearch_5").trim())) vt_jfjl.setRegisTime(aoData.get("sSearch_5"));

        String sSearch = aoData.get("sSearch");
        Object iDisplayStartObj = aoData.get("iDisplayStart");
        Integer iDisplayStart = (Integer) iDisplayStartObj;
        Object iDisplayLengthsObj = aoData.get("iDisplayLength");
        Integer iDisplayLength = (Integer) iDisplayLengthsObj;
        Object sEchoStr = aoData.get("sEcho");
        Integer sEcho = (Integer) sEchoStr;

        Integer count =sytMapper.findCount(vt_jfjl,sSearch);
        List<VT_Jfjl> jfyhList = sytMapper.findObject(vt_jfjl,iDisplayStart,iDisplayLength,sSearch);

        Map<String,Object> map = new HashMap<>();
        map.put("iTotalDisplayRecords",count);
        map.put("iTotalRecords",count);
        map.put("sEcho",sEcho);
        map.put("aaData",jfyhList);
        return map;
    }

    @Override
    @RequiredLog(operation = "根据缴费记录id获取缴费信息")
    public JsonResult getJfyh(Integer id) {
        VT_Jfjl jfyh = sytMapper.getJfyhById(id);
        return new JsonResult(jfyh);
    }

    @Override
    @RequiredLog(operation = "完成缴费")
    public JsonResult saveJfyh(Integer id, Double actualAmount, String note) {
        TbJfjl jfjl = tbJfjlMapper.selectById(id);
        if(actualAmount == null) return new JsonResult(new Throwable("请输入实缴电费"));
        if(Double.doubleToLongBits(jfjl.getAmountDue()) != Double.doubleToLongBits(actualAmount)) return new JsonResult(new Throwable("实缴电费与应缴电费金额不符"));
        jfjl.setActualAmount(actualAmount);
        jfjl.setNote(note);
        jfjl.setReceiptStatus(1);   // 1：已打印收据
        jfjl.setPayStatus(2);       // 2：已缴费
        jfjl.setPayTime(new Date());
        jfjl.setModifiedTime(new Date());
        if(tbJfjlMapper.updateById(jfjl) == 1) return new JsonResult("缴费成功");
        return new JsonResult(new Throwable("缴费失败"));
    }

    @Override
    @RequiredLog(operation = "打印收据")
    public Map<String, Object> printFactura(Integer id) {
        TbJfjl jfjl = tbJfjlMapper.selectById(id);
        TbYdyh ydyh = tbYdyhMapper.selectById(jfjl.getYdyhId());
        TbYhlx yhlx = tbYhlxMapper.selectById(ydyh.getUserTypeId());
        Map<String,Object> map = new HashMap<>();
        map.put("date",new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        map.put("name",ydyh.getName());
        map.put("address",ydyh.getAddress());
        map.put("userId",ydyh.getId());
        map.put("htj","SI");
        map.put("dy","NO");
        map.put("free","NO");
        map.put("dfy",new SimpleDateFormat("MM").format(jfjl.getRegisTime()));
        map.put("dfn",new SimpleDateFormat("yyyy").format(jfjl.getRegisTime()));
        map.put("dydl",new BigDecimal(jfjl.getPeriodElecNum()).toString());
        map.put("dffl",new BigDecimal(yhlx.getTate()).toString());
        map.put("dydf",new BigDecimal(jfjl.getAmountDue()).toString());
        map.put("dfze",new BigDecimal(jfjl.getAmountDue()).toString());
        map.put("meterId",jfjl.getMeterId());
        return map;
    }
}

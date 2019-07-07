package com.daye.sys.service.impl;

import com.daye.common.annotation.RequiredLog;
import com.daye.sys.entity.vt.VT_Jfjl;
import com.daye.sys.mapper.SytMapper;
import com.daye.sys.service.SytService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SytServiceImpl implements SytService {

    @Autowired
    SytMapper sytMapper;

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
}

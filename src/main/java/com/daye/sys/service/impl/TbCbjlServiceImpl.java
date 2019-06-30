package com.daye.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daye.common.annotation.RequiredLog;
import com.daye.sys.entity.TbCbjl;
import com.daye.sys.entity.vt.VT_Cbjl;
import com.daye.sys.mapper.TbCbjlMapper;
import com.daye.sys.service.TbCbjlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 抄表记录 服务实现类
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
@Service
public class TbCbjlServiceImpl extends ServiceImpl<TbCbjlMapper, TbCbjl> implements TbCbjlService {

    @Autowired
    TbCbjlMapper tbCbjlMapper;

    @Override
    @RequiredLog(operation = "获取最近一期的抄表记录")
    public Map<String, Object> findObject(Map<String, String> aoData) {
        VT_Cbjl vt_cbjl = new VT_Cbjl();
        if(!StringUtils.isEmpty(aoData.get("sSearch_1").trim())) vt_cbjl.setName(aoData.get("sSearch_1"));
        if(!StringUtils.isEmpty(aoData.get("sSearch_2").trim())) vt_cbjl.setIdCode(aoData.get("sSearch_2"));
        if(!StringUtils.isEmpty(aoData.get("sSearch_3").trim())) vt_cbjl.setMeterId(aoData.get("sSearch_3"));
        if(!StringUtils.isEmpty(aoData.get("sSearch_4").trim())) vt_cbjl.setRegisTime(aoData.get("sSearch_4"));
        if(!StringUtils.isEmpty(aoData.get("sSearch_5").trim())){
            String meterNumStr = aoData.get("sSearch_5");
            Long meterNum = Long.valueOf(meterNumStr);
            vt_cbjl.setMeterNum(meterNum);
        }

        String sSearch = aoData.get("sSearch");
        Object iDisplayStartObj = aoData.get("iDisplayStart");
        Integer iDisplayStart = (Integer) iDisplayStartObj;
        Object iDisplayLengthsObj = aoData.get("iDisplayLength");
        Integer iDisplayLength = (Integer) iDisplayLengthsObj;
        Object sEchoStr = aoData.get("sEcho");
        Integer sEcho = (Integer) sEchoStr;

        Integer count =tbCbjlMapper.findCount(vt_cbjl,sSearch);
        List<VT_Cbjl> cbjlList = tbCbjlMapper.findObject(vt_cbjl,iDisplayStart,iDisplayLength,sSearch);
        Map<String,Object> map = new HashMap<>();
        map.put("iTotalDisplayRecords",count);
        map.put("iTotalRecords",count);
        map.put("sEcho",sEcho);
        map.put("aaData",cbjlList);
        return map;
    }
}

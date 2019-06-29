package com.daye.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daye.common.annotation.RequiredLog;
import com.daye.sys.entity.TbYhlx;
import com.daye.sys.mapper.TbYhlxMapper;
import com.daye.sys.service.TbYhlxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户类型 服务实现类
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
@Service
public class TbYhlxServiceImpl extends ServiceImpl<TbYhlxMapper, TbYhlx> implements TbYhlxService {
    @Autowired
    TbYhlxMapper tbYhlxMapper;
    @Override
    @RequiredLog(value = 0, operation = "获得所有用户类型信息")
    public Map<String, Object> findObject(Map<String, String> aoData) {

        TbYhlx yhlx = new TbYhlx();
        if(!StringUtils.isEmpty(aoData.get("sSearch_0").trim())){
            Long id = Long.valueOf(aoData.get("sSearch_0"));
            yhlx.setId(id);
        }
        if(!StringUtils.isEmpty(aoData.get("sSearch_1").trim())) yhlx.setUserType(aoData.get("sSearch_1"));
        if(!StringUtils.isEmpty(aoData.get("sSearch_2").trim())) yhlx.setTate(Double.valueOf(aoData.get("sSearch_2")));

        String sSearch = aoData.get("sSearch");
        Object iDisplayStartObj = aoData.get("iDisplayStart");
        Integer iDisplayStart = (Integer) iDisplayStartObj;
        Object iDisplayLengthsObj = aoData.get("iDisplayLength");
        Integer iDisplayLength = (Integer) iDisplayLengthsObj;
        Object sEchoStr = aoData.get("sEcho");
        Integer sEcho = (Integer) sEchoStr;
        Integer count = tbYhlxMapper.findCount(yhlx,sSearch);
        List<TbYhlx> TbYhlxList = tbYhlxMapper.findObject(yhlx,iDisplayStart,iDisplayLength,sSearch);
        Map<String,Object> map = new HashMap<>();
        map.put("iTotalDisplayRecords",count);
        map.put("iTotalRecords",count);
        map.put("sEcho",sEcho);
        map.put("aaData",TbYhlxList);
        return map;
    }
}

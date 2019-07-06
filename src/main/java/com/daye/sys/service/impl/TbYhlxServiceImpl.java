package com.daye.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daye.common.annotation.RequiredLog;
import com.daye.common.vo.JsonResult;
import com.daye.sys.entity.TbYhlx;
import com.daye.sys.mapper.TbYhlxMapper;
import com.daye.sys.service.TbYhlxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
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
        if(!StringUtils.isEmpty(aoData.get("sSearch_2").trim())) yhlx.setTate(aoData.get("sSearch_2"));

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

    @Override
    @RequiredLog(operation = "添加用户类型")
    public JsonResult addYhlx(TbYhlx yhlx) {
        if(StringUtils.isEmpty(yhlx.getUserType().trim())) return new JsonResult(new Throwable("用户类型不能为空"));
        if(yhlx.getTate()==null||Double.doubleToLongBits(Double.valueOf(yhlx.getTate()))==Double.doubleToLongBits(0)) return new JsonResult(new Throwable("电费费率不能为空或0"));
        TbYhlx oldYhlx = tbYhlxMapper.findYhlxByUserType(yhlx.getUserType().trim());
        if(oldYhlx != null) return new JsonResult(new Throwable("用户类型已存在，请更改用户类型名称"));
        yhlx.setUserType(yhlx.getUserType().trim());
        tbYhlxMapper.insert(yhlx);
        return new JsonResult("添加成功");
    }

    @Override
    @RequiredLog(operation = "根据用户类型ID获取系统用户类型")
    public JsonResult getYhlx(Long id) {
        if(id == null || id== 0L) return new JsonResult(new Throwable("用户不存在"));
        TbYhlx yhlx = tbYhlxMapper.selectById(id);
        return new JsonResult(yhlx);
    }

    @Override
    @RequiredLog(operation = "修改用户类型信息")
    public JsonResult updateYhlx(TbYhlx yhlx) {
        if(StringUtils.isEmpty(yhlx.getUserType().trim())) return new JsonResult(new Throwable("用户类型不能为空"));
        if(yhlx.getTate()==null||Double.doubleToLongBits(Double.valueOf(yhlx.getTate()))==Double.doubleToLongBits(0)) return new JsonResult(new Throwable("电费费率不能为空或0"));
        TbYhlx oldYhlx = tbYhlxMapper.findYhlxByUserType(yhlx.getUserType().trim());
        if(oldYhlx != null&& oldYhlx.getId()!=yhlx.getId()) return new JsonResult(new Throwable("用户类型已存在，请更改用户类型名称"));
        yhlx.setUserType(yhlx.getUserType().trim());
        yhlx.setModifiedTime(new Date());
        if (tbYhlxMapper.updateById(yhlx) == 1) {
            return new JsonResult("修改成功");
        }
        return  new JsonResult(new Throwable("修改失败"));
    }

    @Override
    @RequiredLog(operation = "删除用户类型")
    public JsonResult deleteYhlx(Long id) {
        if (tbYhlxMapper.deleteById(id) == 1) {
            return new JsonResult("删除成功");
        }
        return  new JsonResult(new Throwable("删除失败"));
    }
}

package com.daye.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daye.common.annotation.RequiredLog;
import com.daye.common.vo.JsonResult;
import com.daye.sys.entity.TbYdyh;
import com.daye.sys.entity.TbYhlx;
import com.daye.sys.mapper.TbYdyhMapper;
import com.daye.sys.service.TbYdyhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用电用户 服务实现类
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
@Service
public class TbYdyhServiceImpl extends ServiceImpl<TbYdyhMapper, TbYdyh> implements TbYdyhService {

    @Autowired
    TbYdyhMapper tbYdyhMapper;

    @Override
    @RequiredLog(operation = "获取所有用户类型")
    public JsonResult getYhlxlist() {
        List<TbYhlx> yhlxList = tbYdyhMapper.getYhlxlist();
        return new JsonResult(yhlxList);
    }

    @Override
    @RequiredLog(operation = "获得用户列表")
    public Map<String, Object> getYdyhList(Map<String, String> aoData) {
        TbYdyh ydyh = new TbYdyh();
        if(!StringUtils.isEmpty(aoData.get("sSearch_0").trim())){
            Long id = Long.valueOf(aoData.get("sSearch_0"));
            ydyh.setId(id);
        }
        if(!StringUtils.isEmpty(aoData.get("sSearch_1").trim())) ydyh.setName(aoData.get("sSearch_1"));
        if(!StringUtils.isEmpty(aoData.get("sSearch_2").trim())) ydyh.setIdCode(aoData.get("sSearch_2"));
        if(!StringUtils.isEmpty(aoData.get("sSearch_3").trim())) ydyh.setAddress(aoData.get("sSearch_3"));
        if(!StringUtils.isEmpty(aoData.get("sSearch_4").trim())) ydyh.setPhoneNum(aoData.get("sSearch_4"));
        if(!StringUtils.isEmpty(aoData.get("sSearch_5").trim())) ydyh.setNote(aoData.get("sSearch_5"));

        String sSearch = aoData.get("sSearch");
        Object iDisplayStartObj = aoData.get("iDisplayStart");
        Integer iDisplayStart = (Integer) iDisplayStartObj;
        Object iDisplayLengthsObj = aoData.get("iDisplayLength");
        Integer iDisplayLength = (Integer) iDisplayLengthsObj;
        Object sEchoStr = aoData.get("sEcho");
        Integer sEcho = (Integer) sEchoStr;
        Integer count = tbYdyhMapper.findCount(ydyh,sSearch);
        List<TbYdyh> ydyhList = tbYdyhMapper.getYdyhList(ydyh,iDisplayStart,iDisplayLength,sSearch);
        Map<String,Object> map = new HashMap<>();
        map.put("iTotalDisplayRecords",count);
        map.put("iTotalRecords",count);
        map.put("sEcho",sEcho);
        map.put("aaData",ydyhList);
        return map;
    }

    @Override
    @RequiredLog(operation = "根据id获得用电用户")
    public JsonResult getYdyh(Long id) {
        TbYdyh ydyh = tbYdyhMapper.selectById(id);
        return new JsonResult(ydyh);
    }

    @Override
    @RequiredLog(operation = "根据id修改用电用户")
    public JsonResult updateYdyh(TbYdyh ydyh) {
        if(StringUtils.isEmpty(ydyh.getName().trim())) return new JsonResult(new Throwable("请填写用户姓名"));
        if(StringUtils.isEmpty(ydyh.getIdCode().trim())) return new JsonResult(new Throwable("请填写用户身份证号"));
        if(StringUtils.isEmpty(ydyh.getAddress().trim())) return new JsonResult(new Throwable("请填写用户住址"));
        if(ydyh.getUserTypeId()==null || ydyh.getUserTypeId() == 0) return  new JsonResult(new Throwable("请选择用户类型"));
        TbYdyh oldYdyh = tbYdyhMapper.getYdyhByIdCode(ydyh.getIdCode().trim());
        if(oldYdyh != null && oldYdyh.getId() != ydyh.getId()) return new JsonResult(new Throwable("修改后的身份证号已存在"));
        ydyh.setName(ydyh.getName().trim());
        ydyh.setIdCode(ydyh.getIdCode().trim());
        ydyh.setPhoneNum(ydyh.getPhoneNum().trim());
        ydyh.setAddress(ydyh.getAddress().trim());
        ydyh.setModifiedTime(new Date());
        if(tbYdyhMapper.updateById(ydyh)==1) return new JsonResult("修改成功");
        return new JsonResult(new Throwable("修改失败"));
    }

    @Override
    @RequiredLog(operation = "添加用电用户")
    public JsonResult addYdyh(TbYdyh ydyh) {
        if(StringUtils.isEmpty(ydyh.getName().trim())) return new JsonResult(new Throwable("请填写用户姓名"));
        if(StringUtils.isEmpty(ydyh.getIdCode().trim())) return new JsonResult(new Throwable("请填写用户身份证号"));
        if(StringUtils.isEmpty(ydyh.getAddress().trim())) return new JsonResult(new Throwable("请填写用户住址"));
        if(ydyh.getUserTypeId()==null || ydyh.getUserTypeId() == 0) return  new JsonResult(new Throwable("请选择用户类型"));
        TbYdyh oldYdyh = tbYdyhMapper.getYdyhByIdCode(ydyh.getIdCode().trim());
        if(oldYdyh != null && oldYdyh.getValid() == 0){
            ydyh.setId(oldYdyh.getId());
            ydyh.setValid(1);
            if(tbYdyhMapper.updateById(ydyh)==1) return new JsonResult("添加成功");
        }
        if(oldYdyh != null && oldYdyh.getValid() == 1) return new JsonResult(new Throwable("该用户已存在"));
        ydyh.setName(ydyh.getName().trim());
        ydyh.setIdCode(ydyh.getIdCode().trim());
        ydyh.setPhoneNum(ydyh.getPhoneNum().trim());
        ydyh.setAddress(ydyh.getAddress().trim());
        if(tbYdyhMapper.insert(ydyh)==1) return new JsonResult("添加成功");
        return new JsonResult(new Throwable("添加失败"));
    }

    @Override
    @RequiredLog(operation = "根据id删除用户")
    public JsonResult deleteYdyh(Integer id) {
        Integer qfCount = tbYdyhMapper.getQfjl(id);
        if(qfCount>0) return new JsonResult(new Throwable("该用户有未缴清的电费，请缴清后再次执行删除！！！"));
        TbYdyh ydyh = tbYdyhMapper.selectById(id);
        ydyh.setValid(0);
        if(tbYdyhMapper.updateById(ydyh) == 1) return new JsonResult("删除成功");
        return new JsonResult(new Throwable("删除失败"));
    }
}

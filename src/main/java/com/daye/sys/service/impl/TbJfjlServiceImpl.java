package com.daye.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daye.common.annotation.RequiredLog;
import com.daye.common.vo.JsonResult;
import com.daye.sys.entity.TbJfjl;
import com.daye.sys.entity.vt.VT_Jfjl;
import com.daye.sys.mapper.TbJfjlMapper;
import com.daye.sys.service.TbJfjlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;

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
    @Autowired
    HttpServletRequest request;

    @Override
    @RequiredLog(operation = "获取最近一期缴费记录")
    public Map<String, Object> findObject(Map<String, String> aoData) {
        String language = request.getHeader("Accept-Language").substring(0,2);
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
            String payStatus = aoData.get("sSearch_5").trim();
            int i = 0;
            if("zh".equals(language)){
                if("未缴费".contains(payStatus)){
                    vt_jfjl.setPayStatu(0);
                    i++;
                }else if("欠费".contains(payStatus)){
                    vt_jfjl.setPayStatu(1);
                    i++;
                }else if("已缴费".contains(payStatus)){
                    vt_jfjl.setPayStatu(2);
                    i++;
                }else{
                    vt_jfjl.setPayStatu(3);
                    i++;
                }
            }else{
                if("Impago".contains(payStatus)){
                    vt_jfjl.setPayStatu(0);
                    i++;
                }else if("Pago pendiente".contains(payStatus)){
                    vt_jfjl.setPayStatu(1);
                    i++;
                }else if("Pagado".contains(payStatus)){
                    vt_jfjl.setPayStatu(2);
                    i++;
                }else{
                    vt_jfjl.setPayStatu(3);
                    i++;
                }
            }
            if(i==3||i==2){
                vt_jfjl.setPayStatu(null);
            }
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
        if (jfjlList != null && jfjlList.size()>0) {
            if("zh".equals(language)){
                for (VT_Jfjl jfjl : jfjlList) {
                    if(jfjl.getPayStatu()==0){
                        jfjl.setPayStatus("未缴费");
                    }
                    if(jfjl.getPayStatu()==1){
                        jfjl.setPayStatus("欠费");
                    }
                    if(jfjl.getPayStatu()==2){
                        jfjl.setPayStatus("已缴费");
                    }
                }
            }else{
                for (VT_Jfjl jfjl : jfjlList) {
                    if(jfjl.getPayStatu()==0){
                        jfjl.setPayStatus("Impago");
                    }
                    if(jfjl.getPayStatu()==1){
                        jfjl.setPayStatus("Pago pendiente");
                    }
                    if(jfjl.getPayStatu()==2){
                        jfjl.setPayStatus("Pagado");
                    }
                }
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("iTotalDisplayRecords",count);
        map.put("iTotalRecords",count);
        map.put("sEcho",sEcho);
        map.put("aaData",jfjlList);
        return map;

    }

/*
    @Override
    @RequiredLog(operation = "根据主键id删除数据")
    public JsonResult deleteById(Integer id) {
        //删除的时候，欠费的，未缴费的不可以删
        TbJfjl jfjl=tbJfjlMapper.selectById(id);
        if(jfjl.getPayStatus()==0||jfjl.getPayStatus()==1){
         return  new JsonResult(new Throwable("欠费的，未缴费的记录不可以删除！！！"));
        }
        if (tbJfjlMapper.updateById(jfjl)==1) return new JsonResult("删除成功！！！");
        return  new JsonResult(new Throwable("删除失败！！！"));
    }
*/

    @Override
    @RequiredLog(operation = "根据电表号获取该电表的历史数据")
    public Map<String, Object> getHistoryJfjlList(String startTime, String endTime, String meterId, Map<String, String> aoData) {

        Object iDisplayStartObj = aoData.get("iDisplayStart");
        Integer iDisplayStart = (Integer) iDisplayStartObj;
        Object iDisplayLengthsObj = aoData.get("iDisplayLength");
        Integer iDisplayLength = (Integer) iDisplayLengthsObj;
        Object sEchoStr = aoData.get("sEcho");
        Integer sEcho = (Integer) sEchoStr;
        String language = request.getHeader("Accept-Language").substring(0,2);

        Integer count =tbJfjlMapper.findCountByid(meterId,startTime,endTime);
        List<VT_Jfjl> cbjlList = tbJfjlMapper.findObjectById(meterId,startTime,endTime,iDisplayStart,iDisplayLength);
        if (cbjlList != null && cbjlList.size()>0) {
            if("zh".equals(language)){
                for (VT_Jfjl jfjl : cbjlList) {
                    if(jfjl.getPayStatu()==0){
                        jfjl.setPayStatus("未缴费");
                    }
                    if(jfjl.getPayStatu()==1){
                        jfjl.setPayStatus("欠费");
                    }
                    if(jfjl.getPayStatu()==2){
                        jfjl.setPayStatus("已缴费");
                    }
                }
            }else{
                for (VT_Jfjl jfjl : cbjlList) {
                    if(jfjl.getPayStatu()==0){
                        jfjl.setPayStatus("Impago");
                    }
                    if(jfjl.getPayStatu()==1){
                        jfjl.setPayStatus("Pago pendiente");
                    }
                    if(jfjl.getPayStatu()==2){
                        jfjl.setPayStatus("Pagado");
                    }
                }
            }
        }
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
        if(vt_jfjl != null){
            if(StringUtils.isEmpty(vt_jfjl.getName())){
                vt_jfjl.setName("");
            }
            if(StringUtils.isEmpty(vt_jfjl.getIdCode())){
                vt_jfjl.setIdCode("");
            }
            if(StringUtils.isEmpty(vt_jfjl.getMeterId())){
                vt_jfjl.setMeterId("");
            }
            if(StringUtils.isEmpty(vt_jfjl.getPayTime())){
                vt_jfjl.setPayTime("");
            }
            if(vt_jfjl.getPeriodElecNum()==null){
                vt_jfjl.setPeriodElecNum("");
            }
            if(StringUtils.isEmpty(vt_jfjl.getAmountDue())){
                vt_jfjl.setAmountDue("");
            }
            if(StringUtils.isEmpty(vt_jfjl.getActualAmount())){
                vt_jfjl.setActualAmount("");
            }
            if(StringUtils.isEmpty(vt_jfjl.getCreatedTime())){
                vt_jfjl.setCreatedTime("");
            }
            if(StringUtils.isEmpty(vt_jfjl.getPrintTime())){
                vt_jfjl.setPrintTime("");
            }
/*            if(StringUtils.isEmpty(vt_jfjl.getNote())){
                vt_jfjl.setNote("");
            }*/
            if(StringUtils.isEmpty(vt_jfjl.getAddress())){
                vt_jfjl.setAddress("");
            }
            if(StringUtils.isEmpty(vt_jfjl.getPhoneNum())){
                vt_jfjl.setPhoneNum("");
            }
            if(StringUtils.isEmpty(vt_jfjl.getTate())){
                vt_jfjl.setTate("");
            }
            if(StringUtils.isEmpty(vt_jfjl.getCreatedUserTime())){
                vt_jfjl.setCreatedUserTime("");
            }
/*            if(StringUtils.isEmpty(vt_jfjl.getModifiedUserTime())){
                vt_jfjl.setModifiedUserTime("");
            }*/
/*            if(StringUtils.isEmpty(vt_jfjl.getUserNote())){
                vt_jfjl.setUserNote("");
            }*/
        }
        return new JsonResult(vt_jfjl);
    }

    @Override
    @RequiredLog(operation = "导出缴费记录",value=0)
    public List<VT_Jfjl> exportJfjl(String payStatus, String meterId, String idCode, String startTime, String endTime) {
        List<VT_Jfjl> jfjlList = tbJfjlMapper.selectByParameters(payStatus, meterId, idCode, startTime, endTime);

        return jfjlList;
    }

    @Override
    @RequiredLog(operation = "获取收费统计信息")
    public Map<String, Object> findSftj(Map<String, String> aoData) {

        VT_Jfjl vt_jfjl = new VT_Jfjl();

        //姓名
        if(!StringUtils.isEmpty(aoData.get("sSearch_1").trim())) {
            vt_jfjl.setName(aoData.get("sSearch_1"));
        }

        //收费时间
        if(!StringUtils.isEmpty(aoData.get("sSearch_2").trim())) {
            vt_jfjl.setPayTime(aoData.get("sSearch_2"));
        } else {
            Date day = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            vt_jfjl.setPayTime(df.format(day));
        }

        String sSearch = aoData.get("sSearch");
        Object iDisplayStartObj = aoData.get("iDisplayStart");
        Integer iDisplayStart = (Integer) iDisplayStartObj;
        Object iDisplayLengthsObj = aoData.get("iDisplayLength");
        Integer iDisplayLength = (Integer) iDisplayLengthsObj;
        Object sEchoStr = aoData.get("sEcho");
        Integer sEcho = (Integer) sEchoStr;

        Integer count =tbJfjlMapper.findSfCount(vt_jfjl,sSearch);
        List<VT_Jfjl> jfjlList = tbJfjlMapper.findSfObject(vt_jfjl,iDisplayStart,iDisplayLength,sSearch);

        Map<String,Object> map = new HashMap<>();
        map.put("iTotalDisplayRecords",count);
        map.put("iTotalRecords",count);
        map.put("sEcho",sEcho);
        map.put("aaData",jfjlList);

        return map;

    }

}


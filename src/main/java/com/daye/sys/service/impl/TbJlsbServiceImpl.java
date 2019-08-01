package com.daye.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daye.common.annotation.RequiredLog;
import com.daye.common.vo.JsonResult;
import com.daye.sys.entity.TbJlsb;
import com.daye.sys.entity.TbYdyh;
import com.daye.sys.entity.vt.VT_Jlsb;
import com.daye.sys.mapper.TbJlsbMapper;
import com.daye.sys.service.TbJlsbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 计量设备 服务实现类
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
@Service
public class TbJlsbServiceImpl extends ServiceImpl<TbJlsbMapper, TbJlsb> implements TbJlsbService {

    @Autowired
    TbJlsbMapper tbjlsbmapper;
    @Autowired
    HttpServletRequest request;
    
    @Override
    @RequiredLog(operation = "根据计量设备ID获取计量设备")
    public JsonResult getJlsb(Long id) {
        if(id == null || id== 0L) {
            String language = request.getHeader("Accept-Language").substring(0,2);
            if("zh".equals(language)){
                return new JsonResult(new Throwable("用户不存在"));
            }else{
                return new JsonResult(new Throwable("El usuario no existe"));
            }
        }
        VT_Jlsb jlsb = tbjlsbmapper.selectJlsbWithIdCodeById(id);
        return new JsonResult(jlsb);
    }

    @Override
    @RequiredLog(operation = "添加计量设备")
    public JsonResult addJlsb(VT_Jlsb jlsb) {
        TbJlsb jlsbInsert = new TbJlsb();
        String language = request.getHeader("Accept-Language").substring(0,2);
        if("zh".equals(language)){
            if(StringUtils.isEmpty(jlsb.getMeterId().trim())) return new JsonResult(new Throwable("表号不能为空"));
            if(StringUtils.isEmpty(jlsb.getMeterBoxId().trim())) return new JsonResult(new Throwable("表箱号不能为空"));
            if(StringUtils.isEmpty(jlsb.getIdCode().trim())) return new JsonResult(new Throwable("用户身份证号不能为空"));
        }else{
            if(StringUtils.isEmpty(jlsb.getMeterId().trim())) return new JsonResult(new Throwable("El número de contador no pueda estar vacío"));
            if(StringUtils.isEmpty(jlsb.getMeterBoxId().trim())) return new JsonResult(new Throwable("El número de cuadro de contador no pueda estar vacío"));
            if(StringUtils.isEmpty(jlsb.getIdCode().trim())) return new JsonResult(new Throwable("El id de usuario no pueda estar vacío "));
        }
        TbYdyh ydyh = tbjlsbmapper.findYdyhByIdCode(jlsb.getIdCode().trim());
        TbJlsb oldJlsb= tbjlsbmapper.findJlsbByMeterId(jlsb.getMeterId().trim());
        if("zh".equals(language)){
            if(oldJlsb!=null) return new JsonResult(new Throwable("计量设备已存在，请更改表号"));
            if(ydyh == null) return new JsonResult(new Throwable("用户不存在，请更改用户身份证号"));
        }else{
            if(oldJlsb!=null) return new JsonResult(new Throwable("El medidor ya existe, cambie el número de tabla"));
            if(ydyh == null) return new JsonResult(new Throwable("El usuario no existe,  cambie la de usuario id "));
        }
        jlsbInsert.setMeterId(jlsb.getMeterId().trim());
        jlsbInsert.setMeterBoxId(jlsb.getMeterBoxId().trim());
        jlsbInsert.setYdyhId(ydyh.getId());
        tbjlsbmapper.insert(jlsbInsert);
        if("zh".equals(language)){
            return new JsonResult("添加成功");
        }else{
            return new JsonResult("Agregado exitosamente");
        }
    }

    @Override
    @RequiredLog(operation = "修改计量设备信息")
    public JsonResult updateJlsb(VT_Jlsb jlsb) {
        TbJlsb jlsbInsert = new TbJlsb();
        String language = request.getHeader("Accept-Language").substring(0,2);
        if("zh".equals(language)){
            if(StringUtils.isEmpty(jlsb.getMeterId().trim())) return new JsonResult(new Throwable("表号不能为空"));
            if(StringUtils.isEmpty(jlsb.getMeterBoxId().trim())) return new JsonResult(new Throwable("表箱号不能为空"));
            if(StringUtils.isEmpty(jlsb.getIdCode().trim())) return new JsonResult(new Throwable("用户身份证号不能为空"));
        }else{
            if(StringUtils.isEmpty(jlsb.getMeterId().trim())) return new JsonResult(new Throwable("El número de contador no pueda estar vacío"));
            if(StringUtils.isEmpty(jlsb.getMeterBoxId().trim())) return new JsonResult(new Throwable("El número de cuadro de contador no pueda estar vacío"));
            if(StringUtils.isEmpty(jlsb.getIdCode().trim())) return new JsonResult(new Throwable("El id de usuario no pueda estar vacío "));
        }
        TbYdyh ydyh = tbjlsbmapper.findYdyhByIdCode(jlsb.getIdCode().trim());
        TbJlsb oldJlsb= tbjlsbmapper.findJlsbByMeterId(jlsb.getMeterId().trim());
        if("zh".equals(language)){
            if(ydyh == null) return new JsonResult(new Throwable("用户不存在，请更改用户身份证号"));
            if(oldJlsb!=null&&oldJlsb.getId()!=jlsb.getId()) return new JsonResult(new Throwable("计量设备已存在，请更改表号"));
        }else{
            if(ydyh == null) return new JsonResult(new Throwable("El usuario no existe,  cambie la de usuario id "));
            if(oldJlsb!=null&&oldJlsb.getId()!=jlsb.getId()) return new JsonResult(new Throwable("El medidor ya existe, cambie el número de tabla"));
        }
        jlsbInsert.setMeterId(jlsb.getMeterId().trim());
        jlsbInsert.setMeterBoxId(jlsb.getMeterBoxId().trim());
        jlsbInsert.setYdyhId(ydyh.getId());
        jlsbInsert.setId(jlsb.getId());
        jlsbInsert.setModifiedTime(new Date());
        if("zh".equals(language)){
            if (tbjlsbmapper.updateById(jlsbInsert) == 1) {
                return new JsonResult("修改成功");
            }
            return  new JsonResult(new Throwable("修改失败"));
        }else{
            if (tbjlsbmapper.updateById(jlsbInsert) == 1) {
                return new JsonResult("Modificado exitosamente");
            }
            return  new JsonResult(new Throwable("Fallado al modificar"));
        }
    }

/*    @Override
    @RequiredLog(operation = "删除计量设备")
    public JsonResult deleteJlsb(Long id) {
        //判断设备上是否关联了数据
        Integer num = tbjlsbmapper.findAssociatedData(id);
        if(num>0){
            return new JsonResult(new Throwable("存在相关数据，禁止删除！"));
        }
        if (tbjlsbmapper.deleteById(id) == 1) {
            return new JsonResult("删除成功");
        }
        return  new JsonResult(new Throwable("删除失败"));
    }*/

    @Override
    @RequiredLog(value = 0, operation = "获得所有计量设备信息")
    public Map<String, Object> findObject(Map<String, String> aoData) {

        VT_Jlsb jlsb = new VT_Jlsb();
        if(!StringUtils.isEmpty(aoData.get("sSearch_0").trim())){
            Long id = Long.valueOf(aoData.get("sSearch_0"));
            jlsb.setId(id);
        }
        if(!StringUtils.isEmpty(aoData.get("sSearch_1").trim())) jlsb.setMeterId(aoData.get("sSearch_1"));
        if(!StringUtils.isEmpty(aoData.get("sSearch_2").trim())) jlsb.setMeterBoxId(aoData.get("sSearch_2"));
        if(!StringUtils.isEmpty(aoData.get("sSearch_3").trim())) jlsb.setName(aoData.get("sSearch_3"));
        if(!StringUtils.isEmpty(aoData.get("sSearch_4").trim())) jlsb.setIdCode(aoData.get("sSearch_4"));

        String sSearch = aoData.get("sSearch");
        Object iDisplayStartObj = aoData.get("iDisplayStart");
        Integer iDisplayStart = (Integer) iDisplayStartObj;
        Object iDisplayLengthsObj = aoData.get("iDisplayLength");
        Integer iDisplayLength = (Integer) iDisplayLengthsObj;
        Object sEchoStr = aoData.get("sEcho");
        Integer sEcho = (Integer) sEchoStr;
        Integer count = tbjlsbmapper.findCount(jlsb,sSearch);
        List<VT_Jlsb> VT_JlsbList = tbjlsbmapper.findObject(jlsb,iDisplayStart,iDisplayLength,sSearch);
        Map<String,Object> map = new HashMap<>();
        map.put("iTotalDisplayRecords",count);
        map.put("iTotalRecords",count);
        map.put("sEcho",sEcho);
        map.put("aaData",VT_JlsbList);
        return map;
    }
}

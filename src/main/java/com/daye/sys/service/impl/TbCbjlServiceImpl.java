package com.daye.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daye.common.annotation.RequiredLog;
import com.daye.common.util.FileUtils;
import com.daye.common.vo.JsonResult;
import com.daye.sys.entity.TbCbjl;
import com.daye.sys.entity.vt.VT_Cbjl;
import com.daye.sys.mapper.TbCbjlMapper;
import com.daye.sys.service.TbCbjlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    @Autowired
    HttpServletRequest request;

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
            Double meterNum = Double.valueOf(meterNumStr);
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

    @Override
    @RequiredLog(operation = "根据meterId获取历史数据")
    public Map<String, Object> getHistoryCbjlList(String startTime,String endTime,String meterId, Map<String,String> aoData) {
        Object iDisplayStartObj = aoData.get("iDisplayStart");
        Integer iDisplayStart = (Integer) iDisplayStartObj;
        Object iDisplayLengthsObj = aoData.get("iDisplayLength");
        Integer iDisplayLength = (Integer) iDisplayLengthsObj;
        Object sEchoStr = aoData.get("sEcho");
        Integer sEcho = (Integer) sEchoStr;

        Integer count =tbCbjlMapper.findCountByid(meterId,startTime,endTime);
        List<VT_Cbjl> cbjlList = tbCbjlMapper.findObjectById(meterId,startTime,endTime,iDisplayStart,iDisplayLength);
        Map<String,Object> map = new HashMap<>();
        map.put("iTotalDisplayRecords",count);
        map.put("iTotalRecords",count);
        map.put("sEcho",sEcho);
        map.put("aaData",cbjlList);
        return map;
    }

/*    @Override
    @RequiredLog(operation = "根据id删除抄表记录")
    public JsonResult deleteCbjl(Integer id) {
        TbCbjl cbjl = tbCbjlMapper.selectById(id);
        if(tbCbjlMapper.updateById(cbjl) == 1) return new JsonResult("删除成功");
        return new JsonResult(new Throwable("删除失败"));
    }*/

    @Override
    @Transactional
    @RequiredLog(value = 0,operation = "批量导入抄表记录")
    public JsonResult uploadCbjl(MultipartFile file) {
        ApplicationHome home = new ApplicationHome(getClass());
        File jarF = home.getSource().getParentFile();
        String path = jarF.getParentFile().toString();
        String language=request.getHeader("Accept-Language").substring(0,2);
        if (path == null || path.equals("")) {
            if("zh".equals(language)){
                return new JsonResult(new Throwable("获取项目根目录错误"));
            } else {
                return new JsonResult(new Throwable("Obtener error de directorio raíz del proyecto"));
            }
        }
        path = path+"/cbjl/";
        String uuid = UUID.randomUUID().toString();
        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdir();
        }
        File cbjlFile = new File(path+uuid+file.getOriginalFilename());
        try {
            file.transferTo(cbjlFile);
        } catch (IOException e) {
            if("zh".equals(language)){
                return new JsonResult(new Throwable("文件上传错误"));
            } else {
                return new JsonResult(new Throwable("Error al subir el archivo"));
            }
        }
        List<TbCbjl> cbjlList = FileUtils.readCbjlCsv(cbjlFile,language);
        for(int i = cbjlList.size()- 1;i>0;i--){//筛掉表内重复记录
            for(int j = cbjlList.size() -2;j>=0;j--){
                if(cbjlList.get(i).getMeterId().equals(cbjlList.get(j).getMeterId())){
                    if(cbjlList.get(i).getMeterNum()> cbjlList.get(j).getMeterNum()){
                        cbjlList.set(j,cbjlList.get(i));
                    }
                    cbjlList.remove(i);
                    break;
                }
            }
        }
        int num = cbjlList.size();
        if(cbjlList != null && cbjlList.size()>0){
            for(int i = num - 1;i>=0;i--){
               if(tbCbjlMapper.selectByEntity(cbjlList.get(i))){//抄表记录已存在或者表单号不存在
                   cbjlList.remove(i);
                }
            }
        }else{
            if("zh".equals(language)){
                return new JsonResult(new Throwable("文件为空"));
            } else {
                return new JsonResult(new Throwable("El archivo está vacío"));
            }
        }
        num = num-cbjlList.size();
        Integer insertCount = null;
        if(cbjlList.size()>0){
            insertCount = tbCbjlMapper.insertCbjls(cbjlList);
        }else{
            if("zh".equals(language)){
                return new JsonResult(new Throwable("导入失败，数据重复或者电表不存在！"));
            } else {
                return new JsonResult(new Throwable("Fallo al importar , los datos están repetidos o el contador no existe!"));
            }
        }
        if(insertCount != null && insertCount>0){
            Map<String,Long> map = new HashMap<>();
            map.put("insertCount",Long.valueOf(insertCount));
            map.put("insId",cbjlList.get(0).getId());
            tbCbjlMapper.insertJfjls(map);
            Long insertJfjlCount = map.get("result");
            if (insertJfjlCount == null || insertJfjlCount <= 0){
                if("zh".equals(language)){
                    return new JsonResult(new Throwable("电费计算失败"));
                } else {
                    return new JsonResult(new Throwable("Falló cálculo de la tarifa de luz"));
                }
            }
        }
        if("zh".equals(language)){
            if(num>0){
                return new JsonResult("成功导入"+cbjlList.size()+"条数据，无效数据"+num+"条");
            }else{
                return new JsonResult("导入成功");
            }
        } else {
            if(num>0){
                return new JsonResult("Importado exitosamente "+cbjlList.size()+"datos,"+num+" datos inválidos");
            }else{
                return new JsonResult("Importado con éxito");
            }
        }
    }
}


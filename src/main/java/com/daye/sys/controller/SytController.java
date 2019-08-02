package com.daye.sys.controller;

import com.daye.common.util.JsonToMap;
import com.daye.common.util.ShiroUtils;
import com.daye.common.vo.JsonResult;
import com.daye.sys.service.SytService;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/syt")
public class SytController {

    @Autowired
    SytService sytService;
    @Autowired
    HttpServletRequest request;

    @RequestMapping("/getJfyhList")
    @RequiresPermissions("sys:tbsyt")
    public Map<String,Object> getJfyhList(@RequestParam Map<String,String> aoData){
        aoData = JsonToMap.jsonToMap(aoData.get("aoData"));
        Map<String,Object> map = sytService.findObject(aoData);
        return map;
    }

    @RequestMapping("/getJfyh")
    @RequiresPermissions("sys:tbsyt")
    public JsonResult getJfyh(Integer id){
        return sytService.getJfyh(id);
    }

    @RequestMapping("/saveJfyh")
    @RequiresPermissions("sys:tbsyt")
    public JsonResult saveJfyh(Integer id,Double actualAmount,String note){
        Integer cashier = ShiroUtils.getPrincipal().getId();
        return sytService.saveJfyh(id,actualAmount,note,cashier);
    }

    @RequestMapping("/printFactura")
    @RequiresPermissions("sys:tbsyt")
    public Object printFactura(Integer id, HttpServletResponse response){
        String language = request.getHeader("Accept-Language").substring(0,2);
        Map<String,Object> datas = sytService.printFactura(id);
        ApplicationHome home = new ApplicationHome(getClass());
        File jarF = home.getSource();
        String path = jarF.getParentFile().getParentFile().toString();
        if(path == null || "".equals(path)){
            if("zh".equals(language)){
                new Throwable("获取项目根目录错误");
            }else{
                new Throwable("error deoObtener directorio de raíz del proyecto");
            }
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        // 生成收据路径
        String filename = path+File.separator+"factura"+File.separator+format.format(new Date())+datas.get("userId")+datas.get("meterId")+".pdf";
        if(!new File(path+File.separator+"factura").exists()){
            new File(path+File.separator+"factura").mkdir();
        }
        //读取模板
        String tempPath = path+File.separator+"temp"+File.separator+"dfsjpdf.pdf";
        PdfReader reader;
        OutputStream os = null;
        ByteArrayOutputStream bos = null;
        PdfStamper stamper;
        try {
            os = response.getOutputStream();
            // 读入pdf表单
            reader = new PdfReader(new FileInputStream(tempPath));
            bos = new ByteArrayOutputStream();
            // 根据表单生成一个新的pdf
            stamper = new PdfStamper(reader, bos);
            // 获取pdf表单
            AcroFields formTexts = stamper.getAcroFields();

            // 设置字体(这里设置为系统字体，你也可以引入其他的字体)，不设置很可能，中文无法显示。
            BaseFont bf = BaseFont.createFont(path+File.separator+"temp"+File.separator+"arial.ttf",
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            formTexts.addSubstitutionFont(bf);

            // 数据查询后，值的植入 强烈推荐键值方式，其他方式很容易混
            Set<String> keySet =  datas.keySet();
            for (String key : keySet) {
                formTexts.setField(key, datas.get(key).toString());
            }

            stamper.setFormFlattening(true); // 这个必须设
            stamper.close();

            // 创建一个pdf实例
            Document document = new Document();
            PdfCopy copy = new PdfCopy(document, os);
            document.open();
            PdfImportedPage importedPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
            copy.addPage(importedPage);
            document.close(); // open和close一套

            // 强制下载
            //response.reset();
            File file = new File(filename);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;fileName="
                    + URLEncoder.encode(filename, "UTF-8"));
        }catch (Exception e){
            if("zh".equals(language)){
                new Throwable("生成收据出错");
            }else{
                new Throwable("Error al generar el recibo");
            }
        }finally {
            try {
                bos.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

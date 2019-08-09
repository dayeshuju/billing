package com.daye.common.util;

import com.csvreader.CsvReader;
import com.daye.common.exception.ServiceException;
import com.daye.sys.entity.TbCbjl;

import java.io.File;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static List<TbCbjl> readCbjlCsv(File cbjlFile,String language){
        List<TbCbjl> cbjlList = new ArrayList<>();
        try {
            CsvReader reader = new CsvReader(cbjlFile.getPath(),',', Charset.forName("UTF-8"));
            reader.readHeaders();
            while (reader.readRecord()){
                String[] values = reader.getValues();
                TbCbjl cbjl = new TbCbjl();
                cbjl.setMeterId(values[1]);
                cbjl.setRegisTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(values[4]));
                cbjl.setMeterNum(Double.valueOf(values[3]));
                cbjlList.add(cbjl);
            }
        } catch (Exception e) {
            if(cbjlFile.exists()) cbjlFile.delete();
            if("zh".equals(language)){
                throw new ServiceException("抄表记录读取错误");
            }else{
                throw new ServiceException("Error de leer el registro de lectura del contador");
            }
        }
        return cbjlList;
    }
}
